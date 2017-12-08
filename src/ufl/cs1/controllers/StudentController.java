package ufl.cs1.controllers;

import game.controllers.DefenderController;
import game.models.*;
import game.system.*;

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
					actions[i] = defenderAmbush(attacker, defender);

			} else
				actions[i] = -1;
		}
		return actions;
	}

	public int defenderChase(Attacker attacker, Defender defender) {

		int nextDir = defender.getNextDir(attacker.getLocation(), true);
		int nextDir2 = defender.getNextDir(attacker.getLocation(), false);

		if (!defender.isVulnerable() && defender.getLocation().getNeighbor(nextDir).isPill()) {

			return nextDir;
		} else if (defender.isVulnerable() && defender.getLocation().getNeighbor(nextDir2).isPill()) {

			return nextDir2;
		}

		return -1;
	}

	public int defenderAmbush(Attacker attacker, Defender defender) {
		List<Node> maybe = attacker.getPossibleLocations(true);
		//List<Node> thisthing = attacker.getPossibleLocations(true);
        int nextDir;
		try {
			nextDir = defender.getNextDir(maybe.get(0), true);
		}
		catch(Exception e) {
			nextDir = defender.getNextDir(attacker.getLocation(), true);
		}
		int nextDir2 = defender.getNextDir(attacker.getLocation(), false);


			if (!defender.isVulnerable() && defender.getLocation().getNeighbor(nextDir).isPill()) {

				return nextDir;
			} else if (defender.isVulnerable() && defender.getLocation().getNeighbor(nextDir2).isPill()) {

				return nextDir2;
			}

			return -1;
	}
	}

