import React, { useState, useEffect } from 'react';
import { Button, Grid, MenuItem, Select, InputLabel, FormControl, TextField, Typography, Box } from '@mui/material';
import { useSession } from '../context/SessionContext';
import { useWebSocket } from '../context/WebSocketContext';
import { strategies } from '../constants/strategies';
import { useNavigate } from 'react-router-dom';

const Tournoi = () => {
  const [groupe210Strat, setGroupe210Strat] = useState('DONNANT_DONNANT');
  const [groupe18Strat, setGroupe18Strat] = useState('DONNANT_DONNANT');
  const [iterations, setIterations] = useState(1);
  const [result, setResult] = useState('');
  const {sessionId} = useSession()
  const { sendMessage, subscribe} = useWebSocket()
  const navigate = useNavigate()

  useEffect(() => {
        subscribe(`/user/${sessionId}/queue/competitionResult`, (message) => {
        setResult(message.body);
      });

  }, []);

  const startCompetition = () => {
    if (sessionId) {
      sendMessage(
        '/app/startCompetition',
        JSON.stringify({
          groupe210Strategy: groupe210Strat,
          groupe18Strategy: groupe18Strat,
          iterations,
          sessionId,
        })
      );
    }
    // navigate('/game')
    
  };

  return (
    <Box sx={{ maxWidth: 600, margin: 'auto', padding: 2 }}>
      <Typography variant="h4" align="center" gutterBottom>
        Choisir la stratégie de chaque groupe
      </Typography>

      <Grid container spacing={3}>
        <Grid item xs={12} sm={4}>
          <FormControl fullWidth>
            <InputLabel id="strategie-groupe-2-10-label">Stratégie groupe 2_10</InputLabel>
            <Select
              labelId="strategie-groupe-2-10-label"
              id="strategie-groupe-2-10"
              value={groupe210Strat}
              onChange={(e) => setGroupe210Strat(e.target.value)}
              label="Stratégie groupe 2_10"
            >
           {
            strategies.map((strat) => <MenuItem value={strat.key}>{strat.value}</MenuItem>)
           }
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12} sm={4}>
          <FormControl fullWidth>
            <InputLabel id="strategie-groupe-1-8-label">Stratégie groupe 1_8</InputLabel>
            <Select
              labelId="strategie-groupe-1-8-label"
              id="strategie-groupe-1-8"
              value={groupe18Strat}
              onChange={(e) => setGroupe18Strat(e.target.value)}
              label="Stratégie groupe 1_8"
            >
             {
            strategies.map((strat) => <MenuItem value={strat.key}>{strat.value}</MenuItem>)
           }
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12} sm={4}>
          <TextField
            label="Nombre d'itérations"
            type="number"
            fullWidth
            value={iterations}
            onChange={(e) => setIterations(e.target.value)}
            inputProps={{ min: 1 }}
          />
        </Grid>
      </Grid>

      <Box sx={{ textAlign: 'center', marginTop: 2 }}>
        <Button variant="contained" color="primary" onClick={startCompetition}>
          Lancer compétition
        </Button>
      </Box>

      {result && (
        <Box sx={{ marginTop: 2, padding: 2, backgroundColor: '#f4f4f4', borderRadius: 2 }}>
          <Typography variant="h6">Résultat de la compétition:</Typography>
          <pre>{result}</pre>
        </Box>
      )}
    </Box>
  );
};

export default Tournoi;
