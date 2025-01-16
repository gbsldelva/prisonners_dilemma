import React, { useState, useEffect } from 'react';
import { Button, Typography, Container, Box, Paper } from '@mui/material';
import { useWebSocket } from '../context/WebSocketContext';
import { useSession } from '../context/SessionContext';
import { useNavigate } from 'react-router-dom';

const Game = () => {
  const [roundNumber, setRoundNumber] = useState(`0/0`);
  const [currentScore, setCurrentScore] = useState('0 - 0');
  const [gameStatus, setGameStatus] = useState('Match débuté!');
  const [choiceMade, setChoiceMade] = useState(false)
  const [yourChoice, setYourChoice] = useState(null);
  const [opponentChoice, setOpponentChoice] = useState(null);
  const {subscribe, sendMessage} = useWebSocket()
  const {username} = useSession()
  const navigate = useNavigate()
 

  const handleMakeChoice = (choice) => {
    setYourChoice(choice);
    setChoiceMade(true)
    sendMessage(`/app/makeChoice`, JSON.stringify({username, choice}))
  };
  
  const handleDisconnect = () => {
    sendMessage('app/disconnect', JSON.stringify(username))
    subscribe('/topic/availablePlayers', (message) => {
      console.log(message.body)
      const parsedPlayers = JSON.parse(message.body)
        .map((player) => JSON.parse(player))
        .filter((player) => player.username !== username);

        navigate('/waiting', { state: { connectedPlayers: parsedPlayers } });  
    })  

    setTimeout(() => {
      navigate('/waiting', { state: { connectedPlayers: [] } });  
    }, 5000)
  }

  const displayResults = (res) => {
    const parsedResult = JSON.parse(res)
    console.log(parsedResult)
    if(parsedResult?.status == 'Fini') {
      navigate('/end', { state: { score: parsedResult?.score } }); 
    }
    setGameStatus(parsedResult?.status)
    setRoundNumber(parsedResult?.parti)
    setCurrentScore(parsedResult?.score)
    setYourChoice(null)
    setChoiceMade(false) 
  }
  
  useEffect(() => {
    subscribe(`/user/${username}/queue/scoreUpdate`, (message) => {
        displayResults(message.body)
    })
    subscribe(`/user/${username}/queue/gameEnd`, (message) => {
      displayResults(message.body)
    })
    subscribe(`/user/${username}/queue/playerReplacement`, (message) => {
      alert(message.body);
  });

  }, [])

  return (
    <Container maxWidth="sm">
      <Box textAlign="center" marginTop={4}>
        <Paper elevation={3} sx={{ padding: 3 }}>
          <Typography variant="h4" gutterBottom>
            {gameStatus}
          </Typography>
          <Typography variant="h6" color="textSecondary">
            Partie: {roundNumber}
          </Typography>
          <Typography variant="h6" color="textSecondary" gutterBottom>
            Score actuel: {currentScore}
          </Typography>

          <Box marginBottom={2}>
            <Typography variant="body1" color="textPrimary">
              Votre choix: {yourChoice || 'Aucun choix fait'}
            </Typography>
            <Typography variant="body1" color="textPrimary">
              Choix de l'adversaire: {opponentChoice || 'En attente'}
            </Typography>
          </Box>

          <Box display="flex" justifyContent="center" gap={2}>
            <Button
              variant="contained"
              color="primary"
              onClick={() => handleMakeChoice('c')}
              sx={{ width: 120 }}
              disabled={choiceMade}
            >
              Coopérer
            </Button>
            <Button
              variant="contained"
              color="secondary"
              onClick={() => handleMakeChoice('t')}
              sx={{ width: 120 }}
              disabled={choiceMade}
            >
              Trahir
            </Button>
          </Box>
        </Paper>
      </Box>
    <Button fullWidth variant="outlined" onClick={handleDisconnect} style={{ marginTop: '10px' }}>
              Deconnexion
        </Button>
    </Container>
  );
};

export default Game;
