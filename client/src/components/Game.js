import React, { useState, useEffect } from 'react';
import { Button, Typography, Container, Box, Paper } from '@mui/material';

const Game = () => {
  const [roundNumber, setRoundNumber] = useState(0);
  const [currentScore, setCurrentScore] = useState('0 - 0');
  const [gameStatus, setGameStatus] = useState('Match débuté!');
  const [yourChoice, setYourChoice] = useState(null);
  const [opponentChoice, setOpponentChoice] = useState(null);
 

  const makeChoice = (choice) => {
    setYourChoice(choice);
  };

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
              onClick={() => makeChoice('c')}
              sx={{ width: 120 }}
            >
              Coopérer
            </Button>
            <Button
              variant="contained"
              color="secondary"
              onClick={() => makeChoice('t')}
              sx={{ width: 120 }}
            >
              Trahir
            </Button>
          </Box>
        </Paper>
      </Box>
    </Container>
  );
};

export default Game;
