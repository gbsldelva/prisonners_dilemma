import React from 'react';
import { SessionProvider } from './context/SessionContext';
import { WebSocketProvider } from './context/WebSocketContext';
import Login from './components/Login';
import WaitingRoom from './components/WaitingRoom';
import Game from './components/Game';
import EndGame from './components/EndGame';
import Tournoi from './components/Tournoi';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { Container , Box} from '@mui/material';
import { ErrorProvider } from './context/ErrorContext';

function App() {
  const routes = [
    { path: '/', component: Login },
    { path: '/waiting', component: WaitingRoom },
    { path: '/game', component: Game },
    { path: '/end', component: EndGame },
    { path: '/tournoi', component: Tournoi },
  ];
  return (
      <SessionProvider>
        <WebSocketProvider>
          <ErrorProvider>
          <Router>
            <Container
              maxWidth="sm"
              style={{
                display: 'flex',
                justifyContent: 'flex-start', 
                paddingTop: '100px', 
              }}
            >
              <Box
                style={{
                  width: '100%', 
                  padding: '10px',
                }}
              >
                <Routes>
                  {routes.map(({ path, component: Component }) => (
                    <Route key={path} path={path} element={<Component />} />
                  ))}
                </Routes>
              </Box>
            </Container>
          </Router>
          </ErrorProvider>
        </WebSocketProvider>
      </SessionProvider>
    );

}

export default App;
