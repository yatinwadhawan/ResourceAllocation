package multiagent;

import java.util.ArrayList;

import Graph.Node;
import Graph.NodeStatus;
import QLearning.WState;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.state.State;

public class Terminal implements TerminalFunction {

	@Override
	public boolean isTerminal(State s) {

		WState state = (WState) s;
		ArrayList<Node> l = state.getNodeList();

		// When defender is trying to access a node on which attacker is
		// present.
		if (WorldForMultiAgent.defenderNode != null
				&& WorldForMultiAgent.defenderNode
						.equals(WorldForMultiAgent.attackerNode)) {
			WorldForMultiAgent.isDefWin = true;
			System.out.println("Defender and Attacker are on the same Node.");
			return true;
		}

		// When last node in the system gets HACKED by the attacker. Now
		// attacked can control the system functionality.
		for (int i = 0; i < l.size(); i++) {
			Node n = l.get(i);
			if (n.getName().equals("N11")) {
				if (n.getStatus().equals(NodeStatus.HACKED)) {
					WorldForMultiAgent.isDefWin = false;
					System.out.println("Attacker Hacked the Main Node N11.");
					return true;
				}
			}
		}
		return false;
	}
}

// When all the functions are patched by the defender.
// Defender gets high reward.
// for (int i = 0; i < size; i++) {
// Node n = l.get(i);
// if (n.getDstatus().equals(NodeStatus.VULNERABLE)
// || n.getDstatus().equals(NodeStatus.UNKNOWN)
// || n.getDstatus().equals(NodeStatus.HACKED)) {
// return false;
// }
// }
// return true;

