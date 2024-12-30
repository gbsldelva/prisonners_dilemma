package fr.uga.m1miage.pc.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.repository.PlayerRepository;
import fr.uga.m1miage.pc.strategy.StrategyType;

class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConnectNewPlayer() {
        Player player = new Player("test_player_id", "session123", StrategyType.DONNANT_DONNANT, false);
        when(playerRepository.save(player)).thenReturn(player);

        Player result = playerService.connectNewPlayer(player);

        assertEquals(player, result);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void testGetAllPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1", "session1", StrategyType.DONNANT_DONNANT, false));
        players.add(new Player("player2", "session2", StrategyType.DONNANT_DONNANT, true));
        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getAllPlayers();

        assertEquals(players, result);
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    void testGetPlayerByUsername() {
        String playerId = "test_player_id";
        Player player = new Player(playerId, "session123", StrategyType.DONNANT_DONNANT, false);
        when(playerRepository.findPlayerById(playerId)).thenReturn(Optional.of(player));

        Player result = playerService.getPlayerByUsername(playerId);

        assertEquals(player, result);
        verify(playerRepository, times(1)).findPlayerById(playerId);
    }

    @Test
    void testGetPlayerByUsernameNotFound() {
        String playerId = "non_existent_id";
        when(playerRepository.findPlayerById(playerId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> playerService.getPlayerByUsername(playerId));
        verify(playerRepository, times(1)).findPlayerById(playerId);
    }

    @Test
    void testUpdatePlayer() {
        Player player = new Player("test_player_id", "session123", StrategyType.DONNANT_DONNANT, false);
        when(playerRepository.save(player)).thenReturn(player);

        Player result = playerService.updatePlayer(player);

        assertEquals(player, result);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void testDisconnectPlayer() {
        String playerId = "test_player_id";
        playerService.disconnectPlayer(playerId);

        verify(playerRepository, times(1)).deleteById(playerId);
    }

    @Test
    void testGetAllAvailablePlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1", "session1", StrategyType.DONNANT_DONNANT, false)); // Available
        players.add(new Player("player2", "session2", StrategyType.DONNANT_DONNANT, true)); // Playing
        players.add(new Player("player3", "session3", StrategyType.DONNANT_DONNANT, false)); // Available
        when(playerRepository.getAllAvailablePlayers()).thenReturn(players);

        List<Player> result = playerService.getAllAvailablePlayers();

        assertEquals(players, result);
        verify(playerRepository, times(1)).getAllAvailablePlayers();
    }
}