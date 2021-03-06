package mainClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import QLearningDefender.DecisionMaking;
import burlap.mdp.core.state.State;
import classes.MAction;
import classes.NetworkComponent;
import classes.Node;
import classes.NodeStatus;
import classes.RewardVariables;
import classes.Vulnerability;
import database.InstallDatabase;
import database.NodesImportance;

public class MainClass {

	// Path of the database
	public static String FILENAME = "/Users/yatinwadhawan/Documents/Projects/Alpha/src/database/";
	public final static String ADDRESS = "/Users/yatinwadhawan/Documents/Results/";

	// Data Structure
	public static ArrayList<Vulnerability> vulnerabilityList = new ArrayList<Vulnerability>();
	public static HashMap<String, Vulnerability> vulnerabilityMap = new HashMap<String, Vulnerability>();
	public static ArrayList<NetworkComponent> networkList = new ArrayList<NetworkComponent>();
	public static HashMap<String, NetworkComponent> networkMap = new HashMap<String, NetworkComponent>();
	public static ArrayList<Node> nodeList = new ArrayList<Node>();
	public static HashMap<String, Node> nodeMap = new HashMap<String, Node>();
	public static ArrayList<RewardVariables> rewardList = new ArrayList<RewardVariables>();
	public static HashMap<String, RewardVariables> rewardVariable = new HashMap<String, RewardVariables>();
	public static HashMap<String, ArrayList<RewardVariables>> rewardValueMap = new HashMap<String, ArrayList<RewardVariables>>();

	// Data Structure for Storing QLearning variables
	public static ArrayList<String> ACTIONS = new ArrayList<String>();
	public static HashMap<String, Double> reward = new HashMap<String, Double>();
	public static List<MAction> actionList = new ArrayList<MAction>();
	public static ArrayList<State> wstateList = new ArrayList<State>();
	public static List<State> statelist = new ArrayList<State>();

	public static void main(String[] str) throws IOException {

		// clearing all the files before starting the program
		clear(MainClass.ADDRESS + "config.text");
		clear(MainClass.ADDRESS + "action.text");
		clear(MainClass.ADDRESS + "reward.text");
		clear(MainClass.ADDRESS + "states.text");
		clear(MainClass.ADDRESS + "output.text");
		clear(MainClass.ADDRESS + "rewards.text");
		clear(MainClass.ADDRESS + "statetoaction.text");
		purgeDirectory(MainClass.ADDRESS + "/states/");

		// Installing database into data structures from files
		InstallDatabase.install();
		InstallDatabase.print();
		InstallDatabase.printReward();
		NodesImportance.rewardFunction();

		// Defender Data
		// Adding actions and rewards
		NodeStatus.addDefenderActions();
		NodeStatus.createDefenderActionList();
		// NodesImportance.alternateRewardFunction();

		// Calling QLearning Algorithm to make decision for Defender
		DecisionMaking.makeDecision();

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

}
