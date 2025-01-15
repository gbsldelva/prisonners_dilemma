import React, { useState, useEffect, useMemo } from 'react';
import { List, ListItem, Button, Typography, Container, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useSession } from '../context/SessionContext';
import { useWebSocket } from '../context/WebSocketContext';

const WaitingRoom = () => {
  const [players, setPlayers] = useState([]);
  const {sessionId, username} = useSession()
  const navigate = useNavigate();
  const {sendMessage, subscribe} = useWebSocket()

  console.log(players)

  const sendInvitation = (opponent) => {
    if (window.confirm(`Voulez-vous jouer contre ${opponent}?`)) {
        sendMessage('/app/invitationAnswer', JSON.stringify({message: "confirmed", opponentUsername: opponent, playerUsername: username}))
    } else  {
        sendMessage('/app/invitationAnswer', JSON.stringify({message: "declined", opponentUsername: opponent, playerUsername: username}))
    }
  };

  const availablePlayers = useMemo(() => {
    return players.length > 0 ? (
      <List>
      {players.map((player) => (
        <ListItem key={player.sessionId} style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
          <Box
            style={{
              backgroundColor: '#f0f0f0',
              padding: '10px',
              borderRadius: '5px',
              flexGrow: 1, 
              textAlign: 'center',
            }}
          >
            <Typography variant="body1">{player.username}</Typography>
          </Box>
          <Box
            style={{
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'center',
              marginLeft: '10px',
              minWidth: '150px', 
            }}
          >
            <Button
              onClick={() => sendInvitation(player.username)}
              variant="contained"
              fullWidth
              style={{ marginBottom: '5px' }}
            >
              Jouer contre
            </Button>
          </Box>
        </ListItem>
      ))}
    </List>
    ) : (
      <Typography textAlign="center" padding="10px">En attente...</Typography>
    );
    
    

  },[players, sendInvitation])

  useEffect(() => {
    const fetchAvailablePlayers = () => {
      subscribe('/topic/availablePlayers', (message) => {
        setPlayers(JSON.parse(message.body));
      });
    };

    fetchAvailablePlayers();
    const intervalId = setInterval(fetchAvailablePlayers, 1000);
    return () => clearInterval(intervalId);
  }, []);

  return (
    <Container>
      <Typography variant="h5" paddingBottom="20px">Aucun utilisateur connecté, veuillez attendre</Typography>
            {availablePlayers}
      <Button variant="contained" fullWidth onClick={() => navigate('/game')} style={{ marginTop: '10px' }}>
        Jouer avec le serveur
      </Button>
    </Container>
  );
};

export default WaitingRoom;
