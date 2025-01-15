import React from 'react';
import { Container, Typography, Button } from '@mui/material';

const Tournoi = () => {
  return (
    <Container>
      <Typography variant="h4">Compétition de Stratégies</Typography>
      <Button variant="contained" color="primary" fullWidth>
        Lancer une compétition
      </Button>
    </Container>
  );
};

export default Tournoi;
