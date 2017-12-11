package ufl.cs1.controllers;

import game.controllers.DefenderController;
import game.models.*;
import game.system.*;
import java.util.*;

import java.util.List;

public final class StudentController implements DefenderController {
	public void init(Game game) {
	}

	public void shutdown(Game game) {
	}

	public int[] update(Game game, long timeDue) {
		int[] actions = new int[Game.NUM_DEFENDER];
		List<Defender> enemies = game.getDefenders();
		Attacker attacker = game.getAttacker();

		for (int i = 0; i < actions.length; i++) {

			Defender defender = enemies.get(i);
			List<Integer> possibleDirs = defender.getPossibleDirs();

			if (possibleDirs.size() != 0) {

				if (i % 2 == 0)
					actions[i] = defenderChase(attacker, defender);

				else
					actions[i] = defenderAmbush(attacker, defender, game);

			} else
				actions[i] = -1;
		}
		return actions;
	}

	public int defenderChase(Attacker attacker, Defender defender) {

		Node attackerLocation = attacker.getLocation();

		int nextDir = defender.getNextDir(attackerLocation, true);
		int nextDir2 = defender.getNextDir(attackerLocation, false);

		Node checkPill = defender.getLocation().getNeighbor(nextDir);
		Node checkPill2 = defender.getLocation().getNeighbor(nextDir2);

		if (!defender.isVulnerable() && checkPill.isPill()){

			return nextDir;
		}
		else if (defender.isVulnerable() && checkPill2.isPill()) {

			return nextDir2;
		}

		return -1;

	}


	public int defenderAmbush(Attacker attacker, Defender defender, Game game) {

		int nextDir;
		int nextDir2;
		Node target;
		Node attackerLocation;
		int direction;

		List<Node> powerPillLocations;
		List<Node> checkPillsAround;

		direction = attacker.getDirection();
		attackerLocation = attacker.getLocation();
		target = attackerLocation.getNeighbor(direction);

		powerPillLocations = game.getPowerPillList();
		checkPillsAround = attackerLocation.getNeighbors();

		try {
			nextDir = defender.getNextDir(target, true);
		} catch (Exception e) {
			nextDir = defender.getNextDir(attacker.getLocation(), true);
		}
		nextDir2 = defender.getNextDir(attacker.getLocation(), false);

		if (!defender.isVulnerable() && defender.getLocation().getNeighbor(nextDir).isPill()) {
			for (int i = 0; i < powerPillLocations.size(); i++) {
				for (int j = 0; j < checkPillsAround.size(); j++) {
					if (powerPillLocations.get(i) == checkPillsAround.get(j)) {
						return (nextDir2);
					}
				}
			}
			return (nextDir);
		}

		else if (defender.isVulnerable() && defender.getLocation().getNeighbor(nextDir2).isPill()) {

			return (nextDir2);
		}

		return -1;
	}
}




