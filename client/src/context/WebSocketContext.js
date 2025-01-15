import React, { createContext, useContext, useEffect, useRef, useState } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { useSession } from './SessionContext';

const WebSocketContext = createContext();

export const useWebSocket = () => {
  return useContext(WebSocketContext);
};

export const WebSocketProvider = ({ children }) => {
  const stompClientRef = useRef(null);
  const [isConnected, setIsConnected] = useState(false);
  const { updateSessionId } = useSession();
  const subscriptionsRef = useRef({}); 

  useEffect(() => {
    if (stompClientRef.current) {
      console.warn('WebSocket client is already active. Skipping new connection.');
      return;
    }

    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000, 
      debug: (str) => console.log(str),
      onConnect: (frame) => {
        console.log('Connected to WebSocket:', frame);
        setIsConnected(true);
        const sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
        console.log(sessionId);
        updateSessionId(sessionId);
        Object.entries(subscriptionsRef.current).forEach(([destination, callback]) => {
          stompClient.subscribe(destination, callback);
           });
      },
      onStompError: (frame) => {
        console.error('STOMP Error:', frame.headers['message']);
        console.error('Details:', frame.body);
      },
      onWebSocketClose: (event) => {
        console.warn('WebSocket closed:', event);
        setIsConnected(false);
        stompClientRef.current = null;
      },
    });

    stompClient.activate();
    stompClientRef.current = stompClient;

    return () => {
      if (stompClientRef.current) {
        stompClientRef.current.deactivate();
        stompClientRef.current = null;
      }
    };
  }, []);

  const sendMessage = (destination, body) => {
    if (stompClientRef.current && stompClientRef.current.connected) {
      stompClientRef.current.publish({ destination, body });
    } else {
      console.error('WebSocket is not connected');
    }
  };

    const subscribe = (destination, callback) => {
    const stompClient = stompClientRef.current;
    if (stompClient && stompClient.connected) {
      const subscription = stompClient.subscribe(destination, callback);
      subscriptionsRef.current[destination] = callback;
      return subscription.unsubscribe; 
    } else {
      console.error('Stomp client is not connected');
      return () => {}; 
    }
  };

  return (
    <WebSocketContext.Provider value={{ sendMessage, subscribe, isConnected }}>
      {children}
    </WebSocketContext.Provider>
  );
};
