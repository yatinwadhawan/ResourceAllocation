package classes;

import burlap.mdp.core.action.UniversalActionType;
import mainClass.MainClass;

public class NodeStatus {

	public final static String UNKNOWN = "UNKNOWN";
	public final static String PATCHED = "PATCHED";
	public final static String VULNERABLE = "VULNERABLE";
	public final static String HACKED = "HACKED";

	public final static String VAR_LIST = "LIST";

	public final static String ACTION_PATCH = "PATCH";
	public final static String ACTION_SCAN = "SCAN";
	public final static String ACTION_HACK = "HACK";

	// Adding actions to the list for defender.
	public static void addDefenderActions() {
		MainClass.ACTIONS.add(ACTION_PATCH);
		MainClass.ACTIONS.add(ACTION_SCAN);
	}

	// Creating action set for the domain for defender.
	// We have assigned this in the WorldGenerator class.
	public static void createDefenderActionList() {
		for (int i = 0; i < MainClass.nodeList.size(); i++) {
			MAction ms = new MAction(MainClass.nodeList.get(i).getSymbol(),
					NodeStatus.ACTION_SCAN);
			MAction mp = new MAction(MainClass.nodeList.get(i).getSymbol(),
					NodeStatus.ACTION_PATCH);
			MainClass.actionList.add(ms);
			MainClass.actionList.add(mp);
		}
	}

	// ****************************************************************************************************************
	// Attacker Actions
	public static void addAttackerBasicActions() {
		MainClass.ACTIONS.add(ACTION_SCAN);
		MainClass.ACTIONS.add(ACTION_HACK);
	}

	// Creating action set for the domain for attacker.
	public static void createAttackerActions() {
		for (int i = 0; i < MainClass.nodeList.size(); i++) {
			MAction ms = new MAction(MainClass.nodeList.get(i).getSymbol(),
					NodeStatus.ACTION_SCAN);
			MAction mp = new MAction(MainClass.nodeList.get(i).getSymbol(),
					NodeStatus.ACTION_HACK);
			MainClass.actionList.add(ms);
			MainClass.actionList.add(mp);
		}
	}

	public static void addActionAttacker(int index) {
		MAction ms = new MAction(MainClass.nodeList.get(index).getSymbol(),
				NodeStatus.ACTION_SCAN);
		MAction mp = new MAction(MainClass.nodeList.get(index).getSymbol(),
				NodeStatus.ACTION_HACK);
		MainClass.actionList.add(ms);
		MainClass.actionList.add(mp);
	}

	public static void updateActionList(int index) {
		for (int i = 0; i < MainClass.nodeList.get(index).getAdjList().size(); i++) {
			MAction ms = new MAction(MainClass.nodeList.get(index).getAdjList()
					.get(i).getSymbol(), NodeStatus.ACTION_SCAN);
			MAction mp = new MAction(MainClass.nodeList.get(index).getAdjList()
					.get(i).getSymbol(), NodeStatus.ACTION_HACK);
			MainClass.actionList.add(ms);
			MainClass.actionList.add(mp);
		}
	}

}
