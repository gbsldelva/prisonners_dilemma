// connection.js
export function connectWebSocket(socket) {
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/user/queue/gameStart', function (message) {
            // Handle game start
        });

        // Other subscriptions...

        stompClient.send("/app/connectUser", {}, JSON.stringify({ username: username }));
    }, function (error) {
        console.log("Error connecting: ", error);
    });
}

export function connect() {
    // username = document.getElementById('username').value;
    stompClient = new SockJS('http://localhost:8080/ws');

    // Connect to STOMP and subscribe to topics
    connectWebSocket(stompClient);
}