package fr.uga.m1miage.pc.service;

import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    PlayerRepository repository;

    @Autowired
    PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Player connectNewPlayer(Player player) {
        return repository.save(player);
    }

    public List<Player> getAllPlayers() {
        return repository.findAll();
    }

    public Player getPlayerByUsername(String id) {
        return repository.findPlayerById(id).orElseThrow(() -> new RuntimeException("No player with the following username : " + id));
    }

    public Player updatePlayer(Player player) {
        return repository.save(player);
    }

    public void disconnectPlayer(String username) {
        repository.deleteById(username);
    }

    public List<Player> getAllAvailablePlayers() {
        return repository.getAllAvailablePlayers();
    }

}
