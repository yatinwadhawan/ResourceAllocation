package classes;

import QLearning.MAction;
import mainClass.MainClass;

public class NodeStatus {

	public final static String UNKNOWN = "UNKNOWN";
	public final static String PATCHED = "PATCHED";
	public final static String VULNERABLE = "VULNERABLE";
	public final static String HACKED = "HACKED";
	public final static String VAR_LIST = "LIST";
	public final static String ACTION_PATCH = "PATCH";
	public final static String ACTION_HACK = "HACK";
	public final static String ACTION_SCAN = "SCAN";

	// Adding actions to the list.
	public static void addActions() {
		MainClass.ACTIONS.add(ACTION_PATCH);
		MainClass.ACTIONS.add(ACTION_SCAN);
	}

	// Creating action set for the domain.
	// We have assigned this in the WorldGenerator class.
	public static void createActionList() {
		for (int i = 0; i < MainClass.nodeList.size(); i++) {
			MAction ms = new MAction(MainClass.nodeList.get(i).getSymbol(),
					NodeStatus.ACTION_SCAN);
			MAction mp = new MAction(MainClass.nodeList.get(i).getSymbol(),
					NodeStatus.ACTION_PATCH);
			MainClass.actionList.add(ms);
			MainClass.actionList.add(mp);
		}
	}

	// Create Rewards List
	public static void createRewardList() {
		for (int i = 0; i < MainClass.nodeList.size(); i++) {
			Node n = MainClass.nodeList.get(i);
			MainClass.reward.put(n.getName(), n.getReward());
		}
	}
}
