import React, { useState, useEffect } from 'react';
import { Container, Typography, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const EndGame = () => {
  const [finalYourScore, setFinalYourScore] = useState(0);
  const [finalOpponentScore, setFinalOpponentScore] = useState(0);
  const navigate = useNavigate();

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
