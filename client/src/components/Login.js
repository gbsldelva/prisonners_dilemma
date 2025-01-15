import React, { useState } from 'react';
import { Button, TextField, MenuItem, Select, FormControl, InputLabel, Container, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useSession } from '../context/SessionContext';
import { useWebSocket } from '../context/WebSocketContext';
import { options } from '../constants/options';

const Login = () => {
  const [strategy, setStrategy] = useState('');
  const { sessionId , username, updateUsername} = useSession()
  const {sendMessage} = useWebSocket()
  const navigate = useNavigate();

  const isValidUsername = (username) => /^[a-zA-Z0-9]{3,16}$/.test(username);

  const handleConnect = () => {
    if (username === '' || !isValidUsername(username)) {
      alert('Veuillez entrer un nom d\'utilisateur correct.');
      return;
    }
    sendMessage('/app/connectUser', JSON.stringify(username,sessionId,strategy));
    navigate('/waiting');
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Dilemne du Prisonnier
      </Typography>
      <TextField
        label="Nom d'utilisateur"
        fullWidth
        value={username}
        onChange={(e) => updateUsername(e.target.value)}
        margin="normal"
      />
      <FormControl fullWidth margin="normal">
        <InputLabel>Stratégie</InputLabel>
        <Select
          value={strategy}
          onChange={(e) => setStrategy(e.target.value)}
          label="Stratégie"
        >
           {
            options.map((strat) => <MenuItem value={strat.key}>{strat.value}</MenuItem>)
           }
        </Select>
      </FormControl>
      <Button fullWidth variant="contained" onClick={handleConnect}>
        Se connecter
      </Button>
      <Button fullWidth variant="outlined" onClick={() => navigate('/tournoi')} style={{ marginTop: '10px' }}>
        Lancer une compétition de Stratégies
      </Button>
    </Container>
  );
};

export default Login;
