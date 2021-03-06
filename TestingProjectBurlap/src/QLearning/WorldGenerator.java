package QLearning;

import java.util.ArrayList;
import java.util.List;

import Graph.Node;
import Graph.NodeStatus;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.Domain;
import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;

public class WorldGenerator implements DomainGenerator {

	@Override
	public Domain generateDomain() {

		SADomain domain = new SADomain();
		for (int i = 0; i < MainClass.ls.size(); i++) {
			domain.addActionType(new UniversalActionType(MainClass.ls.get(i)));
		}

		StateWorld smodel = new StateWorld();
		Reward rf = new Reward();
		Terminal tf = new Terminal();

		domain.setModel(new FactoredModel(smodel, rf, tf));

		return domain;
	}

	protected class StateWorld implements FullStateModel {

		@Override
		public State sample(State s, Action a) {

			MAction ac = (MAction) a;
			String name = ac.getNodeName();
			String action = ac.getAction();

			State w = s.copy();
			WState state = (WState) w;
			int size = state.getNodeList().size();
			int index = 0;

			// Find the index of the node in the graph on which action is
			// performed.
			for (int i = 0; i < size; i++) {
				if (name.equals(state.getNodeList().get(i).getName())) {
					index = i;
					break;
				}
			}

			Node n = state.getNodeList().get(index);

			// Perform Specific action on the node.
			if (action.equals(MainClass.ACTION_SCAN)) {

				// If status is UNKNOWN
				if (n.getStatus().equals(NodeStatus.UNKNOWN)) {
					// state.getNodeList().get(index)
					// .setPstatus(NodeStatus.UNKNOWN);
					double rand = Math.random();
					if (rand > 0.5) {
						state.getNodeList().get(index)
								.setStatus(NodeStatus.PATCHED);
					} else {
						state.getNodeList().get(index)
								.setStatus(NodeStatus.VULNERABLE);
					}

					// If status is PATCHED
				} else if (n.getStatus().equals(NodeStatus.PATCHED)) {
					// state.getNodeList().get(index)
					// .setPstatus(NodeStatus.PATCHED);
					double rand = Math.random();
					if (rand > 0.6) {
						state.getNodeList().get(index)
								.setStatus(NodeStatus.PATCHED);
					} else {
						state.getNodeList().get(index)
								.setStatus(NodeStatus.VULNERABLE);
					}

					// If status is VULNERABLE
				} else if (n.getStatus().equals(NodeStatus.VULNERABLE)) {
					// state.getNodeList().get(index)
					// .setPstatus(NodeStatus.VULNERABLE);
					state.getNodeList().get(index)
							.setStatus(NodeStatus.VULNERABLE);

				}

			} else if (action.equals(MainClass.ACTION_PATCH)) {

				// If status is UNKNOWN
				if (n.getStatus().equals(NodeStatus.UNKNOWN)) {
					// Only scan is possible. No patching allowed since defender
					// does not know what to patch.
					state.getNodeList().get(index)
							.setStatus(NodeStatus.UNKNOWN);

					// If status is PATCHED
				} else if (n.getStatus().equals(NodeStatus.PATCHED)) {
					// The node is already patched. Defender can only scan to
					// know whether it is vulnerable or not. Later we
					// will introduce Unknown state over a period of time.
					state.getNodeList().get(index)
							.setStatus(NodeStatus.PATCHED);

					// If status is VULNERABLE
				} else if (n.getStatus().equals(NodeStatus.VULNERABLE)) {
					// Defender will patch the node and change the state of the
					// node.
					// state.getNodeList().get(index)
					// .setPstatus(NodeStatus.VULNERABLE);
					state.getNodeList().get(index)
							.setStatus(NodeStatus.PATCHED);
				}

			}
			return state;
		}

		@Override
		public List<StateTransitionProb> stateTransitions(State s, Action a) {

			return null;
		}
	}

	protected class Reward implements RewardFunction {

		@Override
		public double reward(State s, Action a, State sp) {

			// Fill this function
			MAction ac = (MAction) a;
			String namea = ac.getNodeName();
			String action = ac.getAction();

			State w = s.copy();
			WState state = (WState) w;
			int size = state.getNodeList().size();
			Node n = null;

			State wp = sp.copy();
			WState statep = (WState) wp;
			int sizep = statep.getNodeList().size();
			Node np = null;

			// Find the index of the node in the graph on which action is
			// performed.
			for (int i = 0; i < size; i++) {
				if (namea.equals(state.getNodeList().get(i).getName())) {
					n = state.getNodeList().get(i);
					break;
				}
			}
			for (int i = 0; i < sizep; i++) {
				if (namea.equals(statep.getNodeList().get(i).getName())) {
					np = statep.getNodeList().get(i);
					break;
				}
			}

			if (action.equals(MainClass.ACTION_SCAN)) {

				if (n.getStatus().equals(NodeStatus.VULNERABLE)) {
					return 0;
				} else if (n.getStatus().equals(NodeStatus.UNKNOWN)) {
					// We distinguish between the state in which
					// node lands.. whether Vulnerable or Patched
					if (n.getStatus().equals(NodeStatus.PATCHED)) {
						return MainClass.reward.get(np.getName()) / 2;
					} else {
						return MainClass.reward.get(np.getName());
					}
				} else if (n.getStatus().equals(NodeStatus.PATCHED)) {
					// We distinguish between the state in which
					// node lands.. whether Vulnerable or Patched
					if (n.getStatus().equals(NodeStatus.PATCHED)) {
						return MainClass.reward.get(np.getName()) / 2;
					} else {
						return MainClass.reward.get(np.getName());
					}
				}

			} else if (action.equals(MainClass.ACTION_PATCH)) {

				if (n.getStatus().equals(NodeStatus.VULNERABLE)) {
					return MainClass.reward.get(np.getName());
				} else if (n.getStatus().equals(NodeStatus.PATCHED)) {
					return 0;
				} else if (n.getStatus().equals(NodeStatus.UNKNOWN)) {
					return 0;
				}
			}

			return 0;
		}
	}

	protected class Terminal implements TerminalFunction {

		@Override
		public boolean isTerminal(State s) {

			WState state = (WState) s;
			ArrayList<Node> l = state.getNodeList();
			int size = l.size();
			for (int i = 0; i < size; i++) {
				Node n = l.get(i);
				if (n.getStatus().equals(NodeStatus.VULNERABLE)
						|| n.getStatus().equals(NodeStatus.UNKNOWN)) {
					return false;
				}
			}

			return true;
		}
	}

}
