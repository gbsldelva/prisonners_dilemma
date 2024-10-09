// main.js
import { connect } from './connection.js';
import { updateAvailablePlayers } from './player.js';
import { startGame } from './game.js';
import { showMessage } from './ui.js';

var stompClient = null;
var gameId = null;
var username = '';
var totalIterations = 0;
var currentRound = 0;

function init() {
    document.getElementById('connectButton').addEventListener('click', connect);
}

function connectWebSocket(socket) {
    stompClient = Stomp.over(socket);
    
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/availablePlayers', function (message) {
            updateAvailablePlayers(JSON.parse(message.body), username);
        });

        // Additional subscriptions...

        stompClient.send("/app/connectUser", {}, JSON.stringify({ username: username }));
    }, function (error) {
        console.log("Error connecting: ", error);
    });
}

window.onload = init;
