import React, { useState } from 'react';
import { Button, TextField, MenuItem, Select, FormControl, InputLabel, Container, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useSession } from '../context/SessionContext';
import { useWebSocket } from '../context/WebSocketContext';
import { options } from '../constants/options';
import { useError } from '../context/ErrorContext';
import NombreDesPartiesDialog from './NombreDesPartiesDialog';

const Login = () => {
  const [strategy, setStrategy] = useState('');
  const { sessionId , username, updateUsername} = useSession()
  const {sendMessage, subscribe} = useWebSocket()
  const [displayDialog, setDisplayDialog] = useState(false)
  const [nombreParties, setNombreParties] = useState(0)
  const { errorMessage } = useError()
  const navigate = useNavigate();

  const isValidUsername = (username) => /^[a-zA-Z0-9]{3,16}$/.test(username);

  const handleConnect = () => {
    if (username === '' || !isValidUsername(username)) {
      alert('Veuillez entrer un nom d\'utilisateur correct.');
      return;
    }
    sendMessage('/app/connectUser', JSON.stringify({username,sessionId,strategy}));
    if(errorMessage) {
      return;
    }
    subscribe('/topic/availablePlayers', (message) => {
      console.log(message.body)
      const parsedPlayers = JSON.parse(message.body)
        .map((player) => JSON.parse(player))
        .filter((player) => player.username !== username);
        navigate('/waiting', { state: { connectedPlayers: parsedPlayers } });  
    })   

    navigate('/waiting', { state: { connectedPlayers: [] } }); 
  };
  const handlePlayAgainstServer = () => {
    setDisplayDialog(false)
    if (username === '' || !isValidUsername(username)) {
      alert('Veuillez entrer un nom d\'utilisateur correct.');
      return;
    }
    if(strategy === '') {
      alert('Veuillez choisir une stratégie.');
      return
    }
    if(nombreParties > 0) {
      sendMessage("/app/playAgainstServer", JSON.stringify({username, iterations : nombreParties}))
      navigate('/game')
    }
  }

  return (
    <Container>
      <NombreDesPartiesDialog open={displayDialog} onClose={() => setDisplayDialog(false)} onSubmit={handlePlayAgainstServer} nombreDesParties={nombreParties} setNombreDesParties={setNombreParties} />
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
      <Button fullWidth variant="outlined" onClick={() => setDisplayDialog(true)} style={{ marginTop: '10px' }}>
        Jouer Avec le serveur
      </Button>
    </Container>
  );
};

export default Login;
