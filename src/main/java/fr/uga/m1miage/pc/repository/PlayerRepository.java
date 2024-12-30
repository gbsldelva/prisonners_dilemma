package fr.uga.m1miage.pc.repository;

import fr.uga.m1miage.pc.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {
    Optional<Player> findPlayerById(String id);

    @Query("SELECT p FROM Player p WHERE p.isPlaying = false")
    List<Player> getAllAvailablePlayers();
}
