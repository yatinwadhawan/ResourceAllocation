package multiagent;

import java.util.ArrayList;
import java.util.List;

import QLearning.MainClass;
import QLearning.WState;
import burlap.behavior.stochasticgames.GameEpisode;
import burlap.behavior.stochasticgames.agents.maql.MultiAgentQLearning;
import burlap.behavior.stochasticgames.madynamicprogramming.backupOperators.MinMaxQ;
import burlap.mdp.stochasticgames.SGDomain;
import burlap.mdp.stochasticgames.agent.SGAgentType;
import burlap.mdp.stochasticgames.world.World;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;

public class WorldForMultiAgent {

	final double discount = 0.95;
	final double learningRate = 0.1;
	final double defaultQ = 100;
	int ngames = 1000;

	public void createAndRunGameModel() {
		HashableStateFactory hashingFactory = new SimpleHashableStateFactory();
		JointWorldGenerator joint = new JointWorldGenerator();
		SGDomain domain = (SGDomain) joint.generateDomain();
		Reward rf = new Reward();
		Terminal tf = new Terminal();
		WState initialState = new WState(MainClass.nlist);

		SGAgentType defender = new SGAgentType("Defender", null);
		SGAgentType attacker = new SGAgentType("Attacker", null);

		World w = new World(domain, rf, tf, initialState);

		MultiAgentQLearning a0 = new MultiAgentQLearning(domain, discount,
				learningRate, hashingFactory, defaultQ, new MinMaxQ(), true,
				"defender", defender);
		MultiAgentQLearning a1 = new MultiAgentQLearning(domain, discount,
				learningRate, hashingFactory, defaultQ, new MinMaxQ(), true,
				"attacker", attacker);

		w.join(a0);
		w.join(a1);

		System.out.println("Starting training");

		List<GameEpisode> games = new ArrayList<GameEpisode>();
		for (int i = 0; i < ngames; i++) {
			GameEpisode ga = w.runGame();
			games.add(ga);
			if (i % 10 == 0) {
				System.out.println("Game: " + i + ": " + ga.maxTimeStep());
			}
		}

		System.out.println("Finished training");

	}

}
