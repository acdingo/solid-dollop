
package ufl.cs1.controllers;

		import game.controllers.DefenderController;
		import game.models.*;
		import game.system.*;

		import java.util.List;

public final class StudentController implements DefenderController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int[] update(Game game,long timeDue)
	{
		int[] actions = new int[Game.NUM_DEFENDER];
		List<Defender> enemies = game.getDefenders();

		Attacker attacker = game.getAttacker(); //check if i can do this

		//Chooses a random LEGAL action if required. Could be much simpler by simply returning
		//any random number of all of the ghosts
		for(int i = 0; i < actions.length; i++)
		{
			Defender defender = enemies.get(i);
			List<Integer> possibleDirs = defender.getPossibleDirs();

			if (possibleDirs.size() != 0){

				/*********************************ERASE THIS CODE**********************************************
				 /*List<Node> possibleLocations = attacker.getPossibleLocations(true); //possible nodes A could move to
				 Node targetNode = defender.getTargetNode(possibleLocations,true); //find closest possible location of A from D
				 //Check documentation // This^ might send the D to an empty location

				 if (!defender.isVulnerable()) //if not blue
				 actions[i] = defender.getNextDir(targetNode, true);
				 *********************************************************************************************/

				int nextDir = defender.getNextDir(attacker.getLocation(),true);
				int nextDir2 = defender.getNextDir(attacker.getLocation(), false);

				if (!defender.isVulnerable()){
					if(defender.getLocation().getNeighbor(nextDir).isPill()) //check if not a wall or vulnerable
						actions[i] = nextDir;

					//else
					//code to switch directions if there is a wall

				}

				else if (defender.isVulnerable()) {
					if(defender.getLocation().getNeighbor(nextDir2).isPill())
						actions[i] = nextDir2;
					//actions[i] = defender.getNextDir(targetNode, false);

					//else
					//code to switch directions if there is a wall
				}
			}

			//actions[i]=possibleDirs.get(Game.rng.nextInt(possibleDirs.size()));

			else
				actions[i] = -1;
		}
		return actions;
	}
}
