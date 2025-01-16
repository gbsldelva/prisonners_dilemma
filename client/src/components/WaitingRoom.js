import React, { useState, useEffect, useMemo } from 'react';
import { List, ListItem, Button, Typography, Container, Box } from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';
import { useSession } from '../context/SessionContext';
import { useWebSocket } from '../context/WebSocketContext';
import NombreDesPartiesDialog from './NombreDesPartiesDialog';

const WaitingRoom = ({ route }) => {
  const location = useLocation()
  const connectedPlayers = location.state?.connectedPlayers || []
  const [players, setPlayers] = useState(connectedPlayers);
  const [inviteSent, setInviteSent] = useState(false);
  const [nombreParties, setNombreParties] = useState(0);
  const [displayDialog, setDisplayDialog] = useState(false);
  const [opponent, setOpponent] = useState('')
  const { username } = useSession();
  const navigate = useNavigate();
  const { sendMessage, subscribe } = useWebSocket();


  const sendInvitation = (user) => {
    if (window.confirm(`Voulez-vous jouer contre ${user}?`)) {
      setOpponent(user)
      setDisplayDialog(true)
      }
  };
  const handleInvite = () => {
    if(nombreParties > 0) {
    setDisplayDialog(false)
    sendMessage('/app/invite', JSON.stringify({ fromPlayer: username, toUsername: opponent, iteration: nombreParties }));
    setInviteSent(true);
    }
  }

  const handleInvitation = (opponent) => {
    if (window.confirm(`${opponent} vous invite à jouer, voulez-vous accepter l'invitation ?`)) {
      sendMessage('/app/invitationAnswer', JSON.stringify({ message: "confirmed", oponentUsername: opponent, playerUsername: username }));
      navigate('/game');
    } else {
      sendMessage('/app/invitationAnswer', JSON.stringify({ message: "declined", oponentUsername: opponent, playerUsername: username }));
    }
  };

  const availablePlayers = useMemo(() => {
    return players?.length > 0 ? (
      <>
        <Typography variant="h5" paddingBottom="20px">Liste des joueurs disponibles</Typography>
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
      </>
    ) : (
      <>
        <Typography variant="h5" paddingBottom="20px">Aucun utilisateur connecté, veuillez attendre</Typography>
        <Typography textAlign="center" padding="10px">En attente...</Typography>
      </>
    );
  }, [players, sendInvitation, username]);

  useEffect(() => {
    subscribe('/topic/availablePlayers', (message) => {
      console.log(message.body)
      const parsedPlayers = JSON.parse(message.body)
        .map((player) => JSON.parse(player))
        .filter((player) => player.username !== username);

      setPlayers((prevPlayers) => {
        const updatedPlayers = [...prevPlayers];
        parsedPlayers?.forEach((newPlayer) => {
          if (!updatedPlayers.some((player) => player.sessionId === newPlayer.sessionId)) {
            updatedPlayers.push(newPlayer);
          }
        });
        return updatedPlayers;
      });
    }, [username, players]);

    if (players?.length > 0) {
      subscribe(`/user/${username}/queue/invitation`, (message) => {
        handleInvitation(message.body);
      });
    }
    if(inviteSent) {
      subscribe(`/user/${username}/queue/gameStartHandler`, (message) => {
        console.log(message.body)
        if (message.body === 'confirmed') {
          navigate('/game');
        } else {
          alert("Invitation refusée");
        }
      });
    }
  }, [players, inviteSent, username, navigate, subscribe]);

  return (
    <Container>
      <NombreDesPartiesDialog open={displayDialog} onClose={() => setDisplayDialog(false)} onSubmit={handleInvite} nombreDesParties={nombreParties} setNombreDesParties={setNombreParties} />
      {availablePlayers}
      <Button variant="contained" fullWidth onClick={() => navigate('/game')} style={{ marginTop: '10px' }}>
        Jouer avec le serveur
      </Button>
    </Container>
  );
};

export default WaitingRoom;
