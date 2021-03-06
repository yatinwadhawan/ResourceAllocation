package QLearningDefender;

import java.util.ArrayList;
import java.util.List;

import mainClass.MainClass;
import classes.MAction;
import classes.Node;
import classes.NodeStatus;
import classes.WState;
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
		for (int i = 0; i < MainClass.actionList.size(); i++) {
			domain.addActionType(new UniversalActionType(MainClass.actionList
					.get(i)));
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
				if (name.equals(state.getNodeList().get(i).getSymbol())) {
					index = i;
					break;
				}
			}

			Node n = state.getNodeList().get(index);

			// Perform Specific action on the node.
			if (action.equals(NodeStatus.ACTION_SCAN)) {

				// If status is Hacked
				if (DecisionMaking.isHackedStateInvolved
						&& n.getStatus().equals(NodeStatus.HACKED)) {
					state.getNodeList().get(index).setStatus(NodeStatus.HACKED);

					// If status is UNKNOWN
				} else if (n.getStatus().equals(NodeStatus.UNKNOWN)) {

					// Hacked
					double rand = Math.random();
					if (rand > 0.7 && DecisionMaking.isHackedStateInvolved) {
						state.getNodeList().get(index)
								.setStatus(NodeStatus.HACKED);

					} else if (rand > 0.4) {
						state.getNodeList().get(index)
								.setStatus(NodeStatus.PATCHED);
					} else {
						state.getNodeList().get(index)
								.setStatus(NodeStatus.VULNERABLE);
					}

					// If status is PATCHED
				} else if (n.getStatus().equals(NodeStatus.PATCHED)) {

					// For Future:
					// When Patched its compromised Probability will be zero.
					// If it becomes vulnerable we will update its probability

					double rand = Math.random();
					if (rand > 0.7 && DecisionMaking.isHackedStateInvolved) {
						state.getNodeList().get(index)
								.setStatus(NodeStatus.HACKED);
					} else if (rand > 0.4) {
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

			} else if (action.equals(NodeStatus.ACTION_PATCH)) {

				// If status is Hacked
				if (DecisionMaking.isHackedStateInvolved
						&& n.getStatus().equals(NodeStatus.HACKED)) {

					state.getNodeList().get(index)
							.setStatus(NodeStatus.PATCHED);

					// If status is UNKNOWN
				} else if (n.getStatus().equals(NodeStatus.UNKNOWN)) {
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

		public boolean[] isTerminal(State s) {

			boolean[] b = new boolean[2];
			b[0] = false; // whether terminal state or not
			b[1] = false; // if Hacked State, then true
			WState state = (WState) s;
			ArrayList<Node> l = state.getNodeList();
			int size = l.size();
			for (int i = 0; i < size; i++) {
				Node n = l.get(i);
				if (n.getSymbol().equals("S7")) {
					if (n.getStatus().equals(NodeStatus.HACKED)) {
						b[0] = true;
						b[1] = true;
						return b;
					}
				}
				if (n.getStatus().equals(NodeStatus.VULNERABLE)
						|| n.getStatus().equals(NodeStatus.UNKNOWN)
						|| n.getStatus().equals(NodeStatus.HACKED)) {
					b[0] = false;
					b[1] = false;
					return b;
				}
			}
			b[0] = true;
			b[1] = false;
			return b;
		}

		@Override
		public double reward(State s, Action a, State sp) {

			boolean[] b = isTerminal(sp);
			if (b[0] && !b[1])
				return 500;

			if (b[0] && b[1])
				return -500;

			int negativereward = -200;
			double fraction = 2;

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
				if (namea.equals(state.getNodeList().get(i).getSymbol())) {
					n = state.getNodeList().get(i);
					break;
				}
			}
			for (int i = 0; i < sizep; i++) {
				if (namea.equals(statep.getNodeList().get(i).getSymbol())) {
					np = statep.getNodeList().get(i);
					break;
				}
			}

			if (action.equals(NodeStatus.ACTION_SCAN)) {

				if (DecisionMaking.isHackedStateInvolved
						&& n.getStatus().equals(NodeStatus.HACKED)) {
					return negativereward;

				} else if (n.getStatus().equals(NodeStatus.VULNERABLE)) {
					return negativereward;

				} else if (n.getStatus().equals(NodeStatus.UNKNOWN)) {
					// We distinguish between the state in which
					// node lands.. whether Vulnerable or Patched

					if (DecisionMaking.isHackedStateInvolved
							&& np.getStatus().equals(NodeStatus.HACKED)) {
						return MainClass.reward.get(np.getName()) * fraction;
					} else if (np.getStatus().equals(NodeStatus.PATCHED)) {
						return 0;
					} else {
						return MainClass.reward.get(np.getName()) * fraction;
					}
				} else if (n.getStatus().equals(NodeStatus.PATCHED)) {
					// We distinguish between the state in which
					// node lands.. whether Vulnerable or Patched
					if (DecisionMaking.isHackedStateInvolved
							&& np.getStatus().equals(NodeStatus.HACKED)) {
						return MainClass.reward.get(np.getName()) * fraction;
					} else if (np.getStatus().equals(NodeStatus.PATCHED)) {
						return negativereward;
					} else {
						return MainClass.reward.get(np.getName()) * fraction;
					}
				}

			} else if (action.equals(NodeStatus.ACTION_PATCH)) {

				if (DecisionMaking.isHackedStateInvolved
						&& n.getStatus().equals(NodeStatus.HACKED)) {
					return MainClass.reward.get(np.getName()) * fraction;
				} else if (n.getStatus().equals(NodeStatus.VULNERABLE)) {
					return MainClass.reward.get(np.getName()) * fraction;
				} else if (n.getStatus().equals(NodeStatus.PATCHED)) {
					return negativereward;
				} else if (n.getStatus().equals(NodeStatus.UNKNOWN)) {
					return negativereward;
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
				if (n.getSymbol().equals("S7")) {
					if (n.getStatus().equals(NodeStatus.HACKED))
						return true;
				}
				if (n.getStatus().equals(NodeStatus.VULNERABLE)
						|| n.getStatus().equals(NodeStatus.UNKNOWN)
						|| n.getStatus().equals(NodeStatus.HACKED)) {
					return false;
				}
			}
			return true;
		}
	}
}
