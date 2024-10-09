// game.js
export function startGame(player1, player2) {
    alert(`Game started between ${player1} and ${player2}`);
    // Additional logic to start the game
}

export function makeChoice(choice) {
    if (stompClient && stompClient.connected) {
        stompClient.send("/app/makeChoice", {}, JSON.stringify({ username: username, choice: choice }));
        document.getElementById('choices').style.display = 'none'; // Disable choices
    } else {
        console.error("Unable to send choice. StompClient is not connected.");
    }
}
