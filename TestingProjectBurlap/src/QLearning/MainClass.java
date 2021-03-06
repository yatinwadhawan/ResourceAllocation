package QLearning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import multiagent.WorldForMultiAgent;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.auxiliary.performance.LearningAlgorithmExperimenter;
import burlap.behavior.singleagent.auxiliary.performance.PerformanceMetric;
import burlap.behavior.singleagent.auxiliary.performance.TrialMode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.LearningAgentFactory;
import burlap.behavior.valuefunction.ConstantValueFunction;
import burlap.behavior.valuefunction.QValue;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import Graph.Node;
import Graph.NodeStatus;

public class MainClass {

	public final static String ADDRESS = "/Users/yatinwadhawan/Documents/ResultsMulti/";
	public final static String ACTION_PATCH = "PATCH";
	public final static String ACTION_HACK = "HACK";
	public final static String ACTION_SCAN = "SCAN";
	public static ArrayList<String> ACTIONS = new ArrayList<String>();
	public static HashMap<String, Integer> reward = new HashMap<String, Integer>();
	public static List<MAction> ls = new ArrayList<MAction>();
	public static List<State> statelist = new ArrayList<State>();
	public static int count_state = 0;
	public static ArrayList<Node> nlist = new ArrayList<Node>();
	static ArrayList<State> wl = new ArrayList<State>();

	static int trials = 500;
	static double learningrate = 0.2;
	static double gamma = 0.2;
	static double epsilon = 0.2;

	public static void main(String[] args) throws IOException {

		// Writing Configueration to the file
		clear(ADDRESS + "config.text");
		writeConfig(ADDRESS + "config.text");

		// We have to write a separate function for parsing input to the model.
		// This is dummy input for testing purpose.
		ACTIONS.add(ACTION_PATCH);
		ACTIONS.add(ACTION_SCAN);

		// Create a nodelist that will create a state.

		for (int i = 0; i < 12; i++) {
			Node n = new Node("N" + i, NodeStatus.VULNERABLE);
			nlist.add(n);
		}
		ArrayList<Node> l0 = new ArrayList<Node>();
		l0.add(nlist.get(1));
		l0.add(nlist.get(2));
		nlist.get(0).setAdj(l0);

		ArrayList<Node> l1 = new ArrayList<Node>();
		l1.add(nlist.get(3));
		l1.add(nlist.get(7));
		nlist.get(1).setAdj(l1);

		ArrayList<Node> l2 = new ArrayList<Node>();
		l2.add(nlist.get(3));
		l2.add(nlist.get(4));
		nlist.get(2).setAdj(l2);

		ArrayList<Node> l3 = new ArrayList<Node>();
		nlist.get(3).setAdj(l3);

		ArrayList<Node> l4 = new ArrayList<Node>();
		l4.add(nlist.get(5));
		nlist.get(4).setAdj(l4);

		ArrayList<Node> l5 = new ArrayList<Node>();
		l5.add(nlist.get(6));
		l5.add(nlist.get(8));
		nlist.get(5).setAdj(l5);

		ArrayList<Node> l6 = new ArrayList<Node>();
		l6.add(nlist.get(10));
		nlist.get(6).setAdj(l6);

		ArrayList<Node> l7 = new ArrayList<Node>();
		l7.add(nlist.get(6));
		l7.add(nlist.get(9));
		nlist.get(7).setAdj(l7);

		ArrayList<Node> l8 = new ArrayList<Node>();
		l8.add(nlist.get(11));
		nlist.get(8).setAdj(l8);

		ArrayList<Node> l9 = new ArrayList<Node>();
		l9.add(nlist.get(10));
		nlist.get(9).setAdj(l9);

		ArrayList<Node> l10 = new ArrayList<Node>();
		l10.add(nlist.get(8));
		nlist.get(10).setAdj(l10);

		ArrayList<Node> l11 = new ArrayList<Node>();
		nlist.get(11).setAdj(l11);

		// Creating action set for the domain. We will assign this in the
		// WorldGenerator class.
		for (int i = 0; i < nlist.size(); i++) {
			// nlist.get(i).print();

			MAction ms = new MAction(nlist.get(i).getName(),
					MainClass.ACTION_SCAN);
			MAction mp = new MAction(nlist.get(i).getName(),
					MainClass.ACTION_PATCH);
			ls.add(ms);
			ls.add(mp);
		}

		// Assigning rewards to each node for patching.
		reward.put(nlist.get(0).getName(), 20);
		reward.put(nlist.get(1).getName(), 80);
		reward.put(nlist.get(2).getName(), 100);
		reward.put(nlist.get(3).getName(), 140);
		reward.put(nlist.get(4).getName(), 60);
		reward.put(nlist.get(5).getName(), 40);
		reward.put(nlist.get(6).getName(), 20);
		reward.put(nlist.get(7).getName(), 20);
		reward.put(nlist.get(8).getName(), 40);
		reward.put(nlist.get(9).getName(), 90);
		reward.put(nlist.get(10).getName(), 10);
		reward.put(nlist.get(11).getName(), 170);

		WorldForMultiAgent world = new WorldForMultiAgent();
		world.createAndRunGameModel();

		// Initializing the world generator.
		// WorldGenerator gen = new WorldGenerator();
		// SADomain domain = (SADomain) gen.generateDomain();
		// WState initialstate = new WState(nlist);
		// SimulatedEnvironment env = new SimulatedEnvironment(domain,
		// initialstate);
		//
		// QLearning agent = new QLearning(domain, gamma,
		// new SimpleHashableStateFactory(), new ConstantValueFunction(),
		// learningrate, epsilon);
		//
		// ArrayList<State> wl = new ArrayList<State>();
		// ArrayList<State> allStates = new ArrayList<State>();
		// ArrayList<Double> plot = new ArrayList<Double>();
		// HashMap<State, List<List<QValue>>> map = new HashMap<State,
		// List<List<QValue>>>();
		//
		// // run Q-learning and store results in a list
		// List<Episode> episodes = new ArrayList<Episode>(trials);
		// for (int x = 0; x < trials; x++) {
		// episodes.add(agent.runLearningEpisode(env));
		// env.resetEnvironment();
		// Episode e = episodes.get(x);
		// List<State> sl = e.stateSequence;
		//
		// for (int j = 0; j < sl.size(); j++) {
		// WState w = (WState) sl.get(j);
		// if (!wl.contains(w)) {
		// List<List<QValue>> qv = new ArrayList<List<QValue>>();
		// wl.add(sl.get(j));
		// qv.add(agent.qValues(sl.get(j)));
		// map.put(sl.get(j), qv);
		// } else {
		// List<List<QValue>> qv = map.get(sl.get(j));
		// qv.add(agent.qValues(sl.get(j)));
		// map.put(sl.get(j), qv);
		// }
		// }
		// }
		//
		// clear(ADDRESS + "action.text");
		// clear(ADDRESS + "reward.text");
		// clear(ADDRESS + "states.text");
		//
		// wl.clear();
		// allStates.clear();
		// for (int i = 0; i < trials; i++) {
		// Episode e = episodes.get(i);
		// List<Action> al = e.actionSequence;
		// List<Double> rl = e.rewardSequence;
		// List<State> sl = e.stateSequence;
		//
		// for (int j = 0; j < sl.size(); j++) {
		// WState w = (WState) sl.get(j);
		// if (!wl.contains(w)) {
		// wl.add(sl.get(j));
		// allStates.add(sl.get(j));
		// }
		// }
		// double reward = 0.0;
		// for (int j = 0; j < rl.size(); j++) {
		// reward += rl.get(j);
		// }
		// plot.add(reward / rl.size());
		//
		// writeFile(al, ADDRESS + "action.text", i);
		// writeFile(rl, ADDRESS + "reward.text", i);
		// }
		//
		// writeAllStates(allStates, ADDRESS + "states.text");
		//
		// System.out.println("Count States - " + count_state);
		// System.out.println("Total states - " + allStates.size());
		//

		//
		// purgeDirectory(ADDRESS + "/states/");
		// createFilesForEachStateQValues(map);

	}

	public static int getNodeIndex(String name) {
		int size = nlist.size();
		for (int i = 0; i < size; i++) {
			if (nlist.get(i).getName().equals(name))
				return i;
		}
		return -1;
	}

	public static void clear(String name) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(name);
		writer.print("");
		writer.close();
	}

	static void purgeDirectory(String str) {
		File dir = new File(str);
		for (File file : dir.listFiles()) {
			file.delete();
		}
	}

	public static void writeAllStates(ArrayList<State> allStates, String name)
			throws IOException {

		File fout = new File(name);
		FileOutputStream fos = new FileOutputStream(fout, true);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		for (int i = 0; i < allStates.size(); i++) {
			State s = allStates.get(i);
			WState w = (WState) s;

			bw.write("State " + i + "\n");

			ArrayList<Node> nl = w.getNodeList();
			for (int j = 0; j < nl.size(); j++) {
				Node n = nl.get(j);
				bw.write(n.getName());
				bw.newLine();
				bw.write(n.getStatus());
				bw.newLine();
				bw.write(n.getAdj().toString());
				bw.newLine();
			}
			bw.newLine();
		}
		bw.close();
	}

	public static void createFilesForEachStateQValues(
			HashMap<State, List<List<QValue>>> map) throws IOException {

		int i = 1;
		Set set = map.keySet();
		Iterator itr = set.iterator();
		while (itr.hasNext()) {
			State s = (State) itr.next();
			WState w = (WState) s;

			String name = ADDRESS + "/states/" + i++ + ".text";
			File fout = new File(name);
			FileOutputStream fos = new FileOutputStream(fout, true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			ArrayList<Node> nl = w.getNodeList();
			for (int j = 0; j < nl.size(); j++) {
				Node n = nl.get(j);
				bw.write(n.getName());
				bw.newLine();
				bw.write(n.getStatus());
				bw.newLine();
				bw.write(n.getAdj().toString());
				bw.newLine();
			}
			bw.newLine();
			List<List<QValue>> q = map.get(s);
			for (int x = 0; x < q.size(); x++) {
				List<QValue> l = q.get(x);
				for (int y = 0; y < l.size(); y++) {
					String text = l.get(y).a.actionName() + ":" + l.get(y).q
							+ ",";
					bw.write(text);
				}
				bw.newLine();
				bw.newLine();
			}
			bw.close();
		}

	}

	public static void writeConfig(String name) throws IOException {
		File fout = new File(name);
		FileOutputStream fos = new FileOutputStream(fout, true);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		bw.write("Trials-" + trials);
		bw.newLine();
		bw.write("Learning Rate-" + learningrate);
		bw.newLine();
		bw.write("Epsilon-" + epsilon);
		bw.newLine();
		bw.write("Discount Factor-" + gamma);
		bw.newLine();
		bw.close();
	}

	public static void writeFile(List ls, String name, int number)
			throws IOException {
		File fout = new File(name);
		FileOutputStream fos = new FileOutputStream(fout, true);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		bw.write("Episode");
		bw.write(Integer.toString(number));
		bw.newLine();
		if (name.equals(ADDRESS + "state.text")) {
			List<State> l = (List<State>) ls;
			for (int j = 0; j < l.size(); j++) {
				WState s = (WState) l.get(j);
				bw.write(s.getNodeList().toString());
				bw.newLine();
			}
		} else if (name.equals(ADDRESS + "reward.text")) {
			List<Double> l = (List<Double>) ls;
			for (int j = 0; j < l.size(); j++) {
				bw.write(l.get(j) + ",");
			}
			bw.newLine();
		} else {
			List<Action> l = (List<Action>) ls;
			for (int j = 0; j < l.size(); j++) {
				bw.write(l.get(j).actionName() + ",");
			}
			bw.newLine();
		}
		bw.newLine();

		bw.close();
	}
}

// Q-Learning Algorithm: Create Q-Learning Class and implement methods.
// LearningAgentFactory qLearningFactory = new LearningAgentFactory() {
//
// public String getAgentName() {
// return "Q-learning";
// }
//
// public LearningAgent generateAgent() {
// return new QLearning(domain, 0.5,
// new SimpleHashableStateFactory(),
// new ConstantValueFunction(), 0.5, 0.1);
// }
// };
// LearningAlgorithmExperimenter exp = new
// LearningAlgorithmExperimenter(
// env, 1000, 100, qLearningFactory);
//
// exp.setUpPlottingConfiguration(500, 250, 2, 1000,
// TrialMode.MOST_RECENT_AND_AVERAGE,
// PerformanceMetric.CUMULATIVE_STEPS_PER_EPISODE,
// PerformanceMetric.AVERAGE_EPISODE_REWARD);
//
// // start experiment
// exp.startExperiment();

// LinkedHashMap<State, ArrayList<Double>> map = new LinkedHashMap<State,
// ArrayList<Double>>();
// for (int i = 0; i < trials; i++) {
// Episode e = episodes.get(i);
// List<Action> ls = e.actionSequence;
// List<Double> rs = e.rewardSequence;
// List<State> sl = e.stateSequence;
//
// for (int j = 0; j < rs.size(); j++) {
// if (!map.containsKey(sl.get(j))) {
// ArrayList<Double> re = new ArrayList<Double>();
// re.add(rs.get(j));
// map.put(sl.get(j), re);
// } else {
// ArrayList<Double> re = map.get(sl.get(j));
// re.add(rs.get(j));
// map.put(sl.get(j), re);
// }
// }
// }
// Set set = map.entrySet();
// java.util.Iterator itr = set.iterator();
// while (itr.hasNext()) {
// Map.Entry me = (Map.Entry) ((java.util.Iterator<Entry<State,
// ArrayList<Double>>>) itr)
// .next();
// ArrayList<Double> ls = (ArrayList<Double>) me.getValue();
// PlotGraph demo = new PlotGraph("Average Reward per Episode", ls);
// demo.pack();
// RefineryUtilities.centerFrameOnScreen(demo);
// demo.setVisible(true);
// break;
// }
