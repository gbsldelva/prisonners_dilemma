import React, { createContext, useContext, useState, useEffect } from 'react';
import { useSession } from './SessionContext'; 
import { useWebSocket } from './WebSocketContext'; 
import { Snackbar } from '@mui/material';

const ErrorContext = createContext();

export const useError = () => {
  return useContext(ErrorContext);
};

export const ErrorProvider = ({ children }) => {
  const [errorMessage, setErrorMessage] = useState(null);
  const { sessionId } = useSession(); 
  const { subscribe } = useWebSocket(); 

  useEffect(() => {
    if (sessionId) {
      subscribe(`/user/${sessionId}/queue/errors`, (message) => {
        const error = JSON.parse(message.body);
        setErrorMessage(error.message); 
      });
    }
  }, [sessionId, subscribe]);

  const closeError = () => {
    setErrorMessage(null);
  };

  return (
    <ErrorContext.Provider value={{ errorMessage, closeError }}>
      {children}
      {errorMessage && (
        <Snackbar
          open={Boolean(errorMessage)}
          autoHideDuration={6000}
          message={errorMessage}
          onClose={closeError}
        />
      )}
    </ErrorContext.Provider>
  );
};
