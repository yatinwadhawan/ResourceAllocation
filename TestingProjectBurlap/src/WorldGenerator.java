import java.util.List;

import Graph.Graph;
import Graph.Node;
import Graph.NodeStatus;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.Domain;
import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;

public class WorldGenerator implements DomainGenerator {

	@Override
	public Domain generateDomain() {

		SADomain domain = new SADomain();
		for (int i = 0; i < MainClass.nlist.size(); i++) {
			for (int j = 0; j < MainClass.ACTIONS.size(); j++) {
				domain.addActionType(new MAction(MainClass.nlist.get(i),
						MainClass.ACTIONS.get(j)));
			}
		}
		System.out.println(domain.getActionTypes());

		StateWorld smodel = new StateWorld();
		Reward rf = new Reward();
		Terminal tf = new Terminal();

		domain.setModel(new FactoredModel(smodel, rf, tf));

		return domain;
	}

	protected class StateWorld implements FullStateModel {

		@Override
		public State sample(State s, Action a) {

			s = s.copy();
			WState state = (WState) s;
			MAction m = (MAction) a;
			Node n = m.getNode();
			String action = m.getAction();
			Graph g = state.getGraph();
			int size = g.getNodelist().size();

			if (action.equals(MainClass.ACTION_SCAN)) {
				for (int i = 0; i < size; i++) {
					if (n.getName().equals(g.getNodelist().get(i).getName())) {

						// Change status of the Node stochastically according to
						// the action
						if (n.getStatus().equals(NodeStatus.UNKNOWN)) {
							// From here it can go either to patched state or
							// vulnerable state.

						} else if (n.getStatus().equals(NodeStatus.PATCHED)) {
							// From here, it will go to patched state with high
							// probability and go to vulnerable state with low
							// probability

						} else if (n.getStatus().equals(NodeStatus.VULNERABLE)) {
							// Do nothing since it is already vulnerable.
						}

						break;
					}
				}
			} else if (action.equals(MainClass.ACTION_PATCH)) {
				for (int i = 0; i < size; i++) {
					if (n.getName().equals(g.getNodelist().get(i).getName())) {
						// Change status of the Node stochastically according to
						// the action

						if (n.getStatus().equals(NodeStatus.UNKNOWN)) {
							// Do nothing since you don't know whether it is
							// vulnerable or patched.

						} else if (n.getStatus().equals(NodeStatus.PATCHED)) {
							// Do nothing since it is already patched.

						} else if (n.getStatus().equals(NodeStatus.VULNERABLE)) {
							// The state of node will change to patched from
							// vulnerable.
							
							

						}

						break;
					}
				}

			}

			return null;
		}

		@Override
		public List<StateTransitionProb> stateTransitions(State arg0,
				Action arg1) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	protected class Reward implements RewardFunction {

		@Override
		public double reward(State arg0, Action arg1, State arg2) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	protected class Terminal implements TerminalFunction {

		@Override
		public boolean isTerminal(State arg0) {
			// TODO Auto-generated method stub
			return false;
		}
	}

}
