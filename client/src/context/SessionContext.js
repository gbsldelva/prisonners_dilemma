import React, { createContext, useState, useContext } from 'react';

const SessionContext = createContext();

export const useSession = () => {
  return useContext(SessionContext);
};

export const SessionProvider = ({ children }) => {
  const [sessionId, setSessionId] = useState();
  const [username, setUsername] = useState();

  const updateSessionId = (id) => {
    setSessionId(id);
  };

  const updateUsername = (username) => {
    setUsername(username);
  };


  return (
    <SessionContext.Provider value={{ sessionId, username, updateSessionId, updateUsername }}>
      {children}
    </SessionContext.Provider>
  );
};
