import React, { useState, useEffect } from 'react';
import { Container, Typography, Button } from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';

const EndGame = () => {
  const location = useLocation()
  const score = location.state?.score || ''
  const [finalYourScore, setFinalYourScore] = useState(0);
  const [finalOpponentScore, setFinalOpponentScore] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    if (score) {
      const regex = /\((\d+)\) - .+\((\d+)\)/;
      const match = score.match(regex);
      if (match) {
        const [_, yourScore, opponentScore] = match;
        setFinalYourScore(parseInt(yourScore, 10));
        setFinalOpponentScore(parseInt(opponentScore, 10));
      }
    }
  }, [score]);

  return (
    <Container>
      <Typography variant="h4">Match terminé!</Typography>
      <Typography variant="h6">
        Score final: Vous {finalYourScore} - Adversaire {finalOpponentScore}
      </Typography>
      <Button variant="contained" color="primary" fullWidth onClick={() => navigate('/')}>
        Retour à l'accueil
      </Button>
    </Container>
  );
};

export default EndGame;
