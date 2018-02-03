package multiagent;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.jfree.ui.RefineryUtilities;

import Graph.Node;
import Graph.NodeStatus;
import Graph.PlotGraph;
import QLearning.MAction;
import QLearning.MainClass;
import QLearning.WState;
import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.stochasticgames.GameEpisode;
import burlap.behavior.stochasticgames.PolicyFromJointPolicy;
import burlap.behavior.stochasticgames.agents.maql.MultiAgentQLearning;
import burlap.behavior.stochasticgames.agents.twoplayer.singlestage.equilibriumplayer.equilibriumsolvers.CorrelatedEquilibrium;
import burlap.behavior.stochasticgames.agents.twoplayer.singlestage.equilibriumplayer.equilibriumsolvers.MinMax;
import burlap.behavior.stochasticgames.auxiliary.GameSequenceVisualizer;
import burlap.behavior.stochasticgames.madynamicprogramming.AgentQSourceMap;
import burlap.behavior.stochasticgames.madynamicprogramming.QSourceForSingleAgent;
import burlap.behavior.stochasticgames.madynamicprogramming.backupOperators.CorrelatedQ;
import burlap.behavior.stochasticgames.madynamicprogramming.backupOperators.MinMaxQ;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.ECorrelatedQJointPolicy;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.EGreedyJointPolicy;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.EGreedyMaxWellfare;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.EMinMaxPolicy;
import burlap.behavior.stochasticgames.solvers.CorrelatedEquilibriumSolver;
import burlap.behavior.stochasticgames.solvers.CorrelatedEquilibriumSolver.CorrelatedEquilibriumObjective;
import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.state.State;
import burlap.mdp.stochasticgames.JointAction;
import burlap.mdp.stochasticgames.SGDomain;
import burlap.mdp.stochasticgames.agent.SGAgentType;
import burlap.mdp.stochasticgames.world.World;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;

public class WorldForMultiAgent {

	final int movingaverage = 10;
	final double discount = 0.2;
	final double learningRate = 0.2;
	final double defaultQ = 0;
	double epsilon = 0.8;
	int ngames = 100;
	public static int windef = 0;
	public static boolean isDefWin = false;
	public static String initialStatus = NodeStatus.UNKNOWN;

	public static boolean isDefenderAttackerAccessingSameNode = false;
	public static SAgentType defender, attacker;
	public static Node attackerNode, defenderNode;

	public void createAndRunGameModel() {

		PrintStream originalStream = System.out;

		HashableStateFactory hashingFactory = new SimpleHashableStateFactory();
		JointWorldGenerator joint = new JointWorldGenerator();
		SGDomain domain = (SGDomain) joint.generateDomain();

		Reward rf = new Reward();
		Terminal tf = new Terminal();
		WState initialState = new WState(MainClass.nlist);

		// Creating Defender object with all the actions available to him.
		defender = new SAgentType("Defender", 0,
				JointWorldGenerator.getDefenderActionList());

		// Adding initial actions of the attacker.
		attacker = new SAgentType("Attacker", 1, new ArrayList<ActionType>());

		World w = new World(domain, rf, tf, initialState);

		MultiAgentQLearning dagent = new MultiAgentQLearning(domain, discount,
				learningRate, hashingFactory, defaultQ, new MinMaxQ(), true,
				defender.getTypeName(), defender);
		dagent.agentNum = 0;

		MultiAgentQLearning aagent = new MultiAgentQLearning(domain, discount,
				learningRate, hashingFactory, defaultQ, new MinMaxQ(), true,
				attacker.getTypeName(), attacker);
		aagent.agentNum = 1;

		w.join(dagent);
		w.join(aagent);

		ECorrelatedQJointPolicy policy = new ECorrelatedQJointPolicy(
				CorrelatedEquilibriumObjective.LIBERTARIAN, epsilon);
		PolicyFromJointPolicy dp = new PolicyFromJointPolicy(policy, true);
		PolicyFromJointPolicy ap = new PolicyFromJointPolicy(policy, true);
		dp.setSynchronizeJointActionSelectionAmongAgents(true);
		ap.setSynchronizeJointActionSelectionAmongAgents(true);
		dp.setActingAgent(0);
		ap.setActingAgent(1);
		aagent.setLearningPolicy(ap);
		dagent.setLearningPolicy(dp);

		System.out.println("Starting training");

		ArrayList<Integer> randomNum = new ArrayList<Integer>();

		List<GameEpisode> games = new ArrayList<GameEpisode>();
		for (int i = 0; i < ngames; i++) {

			isDefWin = false;
			int random = ThreadLocalRandom.current().nextInt(0, 4 + 1);
			randomNum.add(random);
			defenderNode = null;
			attackerNode = null;
			attacker.clearActions();

			attacker.updateActionList(random);

			for (int j = 0; j < MainClass.nlist.size(); j++) {
				initialState.getNodeList().get(j).setDstatus(initialStatus);
				initialState.getNodeList().get(j).setAstatus(initialStatus);
				initialState.getNodeList().get(j).setStatus(initialStatus);
			}
			// Initially attacker has compromised the first node which is the
			// starting point for the attacker.
			initialState.getNodeList().get(random)
					.setDstatus(NodeStatus.HACKED);
			initialState.getNodeList().get(random)
					.setAstatus(NodeStatus.HACKED);
			initialState.getNodeList().get(random).setStatus(NodeStatus.HACKED);

			w.setCurrentState(initialState);

			System.out.println("GAME NUMBER " + i);

			GameEpisode ga = w.runGame();
			if (isDefWin)
				windef++;
			games.add(ga);
		}

		System.setOut(originalStream);
		System.out.println("Finished training");
		System.out.println("");

		System.out.println("Analysis Starts");
		ArrayList<WState> ls = new ArrayList<WState>();
		ArrayList<Double> display = new ArrayList<Double>();
		ArrayList<Double> adisplay = new ArrayList<Double>();
		int avg = 0;
		double defreward = 0.0, attrewards = 0.0, sum = 0.0, asum = 0.0;
		for (int i = 0; i < ngames; i++) {
			GameEpisode g = games.get(i);
			List<State> temp = g.getStates();

			for (int j = 0; j < temp.size(); j++) {
				if (!ls.contains(temp.get(j))) {
					ls.add((WState) temp.get(j));
				}
			}

			List<double[]> ld = g.getJointRewards();
			for (int j = 0; j < ld.size(); j++) {
				defreward += ld.get(j)[0];
				attrewards += ld.get(j)[1];
			}
			sum = sum + (defreward / ld.size());
			asum = asum + (attrewards / ld.size());
			if (avg == (movingaverage - 1)) {
				avg = 0;
				if (sum != 0)
					display.add(sum / movingaverage);
				if (asum != 0)
					adisplay.add(asum / movingaverage);
				sum = 0.0;
				asum = 0.0;
				defreward = 0.0;
				attrewards = 0.0;
			}
			avg++;
		}

		plot(display, "Defender Rewards", "Moving average of rewards");
		plot(adisplay, "Attacker Rewards", "Moving average of rewards");
		System.out.println("Random Numbers Choosen: " + randomNum);
		System.out.println("Number of states covered: " + ls.size());

		System.out.println("Number of Games: " + ngames);
		System.out.println("Number of Games won by defender: " + windef);
		System.out.println("Number of Games won by Attacker: "
				+ (ngames - windef));
		System.out.println("Defender: Moving Average: " + display);
		System.out.println("Attacker: Moving Average: " + adisplay);

		storeQValues("Defender", policy.qSourceProvider.getQSources()
				.agentQSource(0), games);
		storeQValues("Attacker", policy.qSourceProvider.getQSources()
				.agentQSource(1), games);
		showQValueDiff("Difference", policy.qSourceProvider.getQSources()
				.agentQSource(0), policy.qSourceProvider.getQSources()
				.agentQSource(1), games);

	}

	public static void storeQValues(String agentName,
			QSourceForSingleAgent qsource, List<GameEpisode> games) {
		List<Double> ls = new ArrayList<Double>();
		for (int i = 0; i < games.size(); i++) {
			GameEpisode g = games.get(i);
			List<JointAction> ja = g.jointActions;
			List<State> s = g.states;
			double qvalue = 0.0;
			for (int j = 0; j < ja.size(); j++) {
				qvalue += qsource.getQValueFor(s.get(j), ja.get(j)).q;
			}
			if (qvalue != 0)
				ls.add(qvalue / ja.size());
		}
		System.out.println(agentName + ": Q-Values Average: " + ls);
		plot(ls, agentName, "Q-Values Average:");
	}

	public static void showQValueDiff(String agentName,
			QSourceForSingleAgent defender, QSourceForSingleAgent attacker,
			List<GameEpisode> games) {
		List<Double> ls = new ArrayList<Double>();
		for (int i = 0; i < games.size(); i++) {
			GameEpisode g = games.get(i);
			List<JointAction> ja = g.jointActions;
			List<State> s = g.states;
			double dqvalue = 0.0, aqvalue = 0.0;
			for (int j = 0; j < ja.size(); j++) {
				dqvalue += defender.getQValueFor(s.get(j), ja.get(j)).q;
				aqvalue += attacker.getQValueFor(s.get(j), ja.get(j)).q;
			}
			ls.add((Math.abs(dqvalue) - Math.abs(aqvalue)));
		}
		System.out.println(agentName
				+ ": Difference Between Q-Values and Average: " + ls);
		plot(ls, agentName, "Difference Between Q-Values and Average");
	}

	public static void plot(List<Double> ls, String name, String str) {
		PlotGraph demo = new PlotGraph(name + " " + str, ls);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}
}
