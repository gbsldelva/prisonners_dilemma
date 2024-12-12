package fr.uga.m1miage.pc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fr.uga.m1miage.pc.adapter.DecisionToTypeAction;
import fr.uga.m1miage.pc.adapter.Groupe2StrategyFactory;
import fr.uga.m1miage.pc.adapter.TypeActionToDecision;
import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.strategy.*;
import fr.uga.m1miage.pc.utils.UtilFunctions;
import fr.uga.strats.g8.enums.TypeAction;
import fr.uga.strats.g8.strategie.*;

@Service
public class StrategyCompetitionService {
	private final NotificationService notificationService;
	
	public StrategyCompetitionService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	public void startCompetition(String strategyGroupe210, String strategyGroupe18, int iterations, String sessionId) {
		Player me = new Player("Groupe Gabriel & Nadine", UUID.randomUUID().toString());
		Player opponent = new Player("Groupe Amine & Manal", UUID.randomUUID().toString());
		GameSession gameSession = new GameSession(me, opponent);
		Strategy myStrategy = StrategyFactory.createStrategy(StrategyType.fromString(strategyGroupe210));
		Strategie opponentStrategy = Groupe2StrategyFactory.createStrategy(StrategyType.fromString(strategyGroupe18));
		
		String result = "R�sum� du match entre " + me.getUsername() + " VS " + opponent.getUsername() + "\n\n";
		
		for (int i = 0; i < iterations; i++) {
			result += "Iteration #" + (i + 1) + "\n";
			String myMove = myStrategy.playNextMove(gameSession.getPlayer1Choices(), gameSession.getPlayer2Choices());
			gameSession.getPlayer1Choices().add(myMove);
			String opponentMove = TypeActionToDecision.convert(opponentStrategy.getAction(listMoveStringToTypeAction(gameSession.getPlayer1Choices()), i)).getValue();
			gameSession.getPlayer2Choices().add(opponentMove);
			Decision ourLastMove = Decision.fromString(gameSession.getPlayer1Choices().get(gameSession.getPlayer1Choices().size() - 1));
			Decision opponentLastMove = Decision.fromString(gameSession.getPlayer2Choices().get(gameSession.getPlayer2Choices().size() - 1));
			me.setScore(me.getScore() + UtilFunctions.getScore(ourLastMove, opponentLastMove));
			opponent.setScore(opponent.getScore() + UtilFunctions.getScore(opponentLastMove, ourLastMove));
			result += me.getUsername() + "[" + textForChoice(gameSession.getPlayer1Choices().get(gameSession.getPlayer1Choices().size() - 1)) + "] - " + opponent.getUsername() + "[" + textForChoice(gameSession.getPlayer2Choices().get(gameSession.getPlayer2Choices().size() - 1)) + "]";
			result += "(" + me.getScore() + " - " + opponent.getScore() + ")\n";
		}
		result += "\nScore final :\n\n";
		result += me.getUsername() + "(" + me.getScore() + ") - " + opponent.getUsername() + "(" + opponent.getScore() + ")";
		
		notificationService.sendCompetitionResult(sessionId, result);
	}
	
	public String textForChoice(String choice) {
		if (choice.equals("c"))
			return "Coop�rer";
		return "Trahir";
	}
	
	static List<TypeAction> listMoveStringToTypeAction(List<String> moveStrings) {
		List<TypeAction> actions = new ArrayList<>();
		for (int i = 0; i < moveStrings.size(); i++) {
			actions.add(DecisionToTypeAction.convert(Decision.fromString(moveStrings.get(i))));
		}
		return actions;
	}
	
}