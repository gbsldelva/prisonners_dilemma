// player.js
export function updateAvailablePlayers(players, currentUsername) {
    const availablePlayersList = document.getElementById('availablePlayers');
    availablePlayersList.innerHTML = ''; // Clear previous players

    if (players.length === 0) {
        availablePlayersList.innerHTML = '<li>No other user connected yet, please wait...</li>';
    } else {
        players.forEach(player => {
            if (player !== currentUsername) { // Exclude current player
                const li = document.createElement('li');
                li.textContent = player;
                
                // Add invite button
                const inviteButton = document.createElement('button');
                inviteButton.textContent = 'Invite';
                inviteButton.onclick = () => sendInvitation(player);
                li.appendChild(inviteButton);
                availablePlayersList.appendChild(li);
            }
        });
    }
}

export function sendInvitation(opponent) {
    if (confirm(`Do you want to send an invitation to ${opponent}?`)) {
        // Send invitation logic to the server
        stompClient.send("/app/invite", {}, JSON.stringify({ inviter: username, invitee: opponent }));
    }
}
