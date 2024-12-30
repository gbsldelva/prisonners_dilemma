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
		
		String result = "Résumé du match entre " + me.getId() + " VS " + opponent.getId() + "\n\n";
		
		for (int i = 0; i < iterations; i++) {
			result += "Iteration #" + (i + 1) + "\n";
			Decision myMove = myStrategy.playNextMove(gameSession.getPlayer1Choices(), gameSession.getPlayer2Choices());
			gameSession.getPlayer1Choices().add(myMove);
			Decision opponentMove = TypeActionToDecision.convert(opponentStrategy.getAction(listMoveStringToTypeAction(gameSession.getPlayer1Choices()), i));
			gameSession.getPlayer2Choices().add(opponentMove);
			Decision ourLastMove = gameSession.getPlayer1Choices().get(gameSession.getPlayer1Choices().size() - 1);
			Decision opponentLastMove = gameSession.getPlayer2Choices().get(gameSession.getPlayer2Choices().size() - 1);
			me.setScore(me.getScore() + UtilFunctions.getScore(ourLastMove, opponentLastMove));
			opponent.setScore(opponent.getScore() + UtilFunctions.getScore(opponentLastMove, ourLastMove));
			result += me.getId() + "[" + textForChoice(gameSession.getPlayer1Choices().get(gameSession.getPlayer1Choices().size() - 1)) + "] - " + opponent.getId() + "[" + textForChoice(gameSession.getPlayer2Choices().get(gameSession.getPlayer2Choices().size() - 1)) + "]";
			result += "(" + me.getScore() + " - " + opponent.getScore() + ")\n";
		}
		result += "\nScore final :\n\n";
		result += me.getId() + "(" + me.getScore() + ") - " + opponent.getId() + "(" + opponent.getScore() + ")";
		
		notificationService.sendCompetitionResult(sessionId, result);
	}
	
	public String textForChoice(Decision decision) {
		if (decision == Decision.COOPERATE)
			return "Cooperer";
		return "Trahir";
	}
	
	static List<TypeAction> listMoveStringToTypeAction(List<Decision> moveStrings) {
		List<TypeAction> actions = new ArrayList<>();
		for (int i = 0; i < moveStrings.size(); i++) {
			actions.add(DecisionToTypeAction.convert(moveStrings.get(i)));
		}
		return actions;
	}
	
}
