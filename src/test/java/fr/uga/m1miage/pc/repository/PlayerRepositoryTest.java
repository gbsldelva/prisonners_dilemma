package fr.uga.m1miage.pc.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.strategy.StrategyType;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void testFindPlayerById() {
        String playerId = "test_player_id";
        Player player = new Player(playerId, "session123", StrategyType.DONNANT_DONNANT, false);
        playerRepository.save(player);

        Optional<Player> foundPlayer = playerRepository.findPlayerById(playerId);

        assertThat(foundPlayer).isPresent();
        assertEquals(foundPlayer.get(), player);
    }

    @Test
    void testGetAllAvailablePlayers() {
        Player firstPlayer = new Player("playing_player", "session456", StrategyType.DONNANT_DONNANT, true);
        playerRepository.save(firstPlayer);

        Player availablePlayer = new Player("available_player", "session789", StrategyType.DONNANT_DONNANT, false);
        playerRepository.save(availablePlayer);

        List<Player> availablePlayers = playerRepository.getAllAvailablePlayers();

        assertThat(availablePlayers)
                .hasSize(2)
                .contains(firstPlayer)
                .contains(availablePlayer);
    }
}