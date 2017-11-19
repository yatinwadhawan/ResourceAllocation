package multiagent;

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
		// for (int i = 0; i < MainClass.ls.size(); i++) {
		// domain.addActionType(new UniversalActionType(MainClass.ls.get(i)));
		// }

		StateWorld smodel = new StateWorld();
		Reward rf = new Reward();
		Terminal tf = new Terminal();

		domain.setModel(new FactoredModel(smodel, rf, tf));

		return domain;
	}

	protected class StateWorld implements FullStateModel {

		@Override
		public State sample(State s, Action a) {

			return null;
		}

		@Override
		public List<StateTransitionProb> stateTransitions(State s, Action a) {

			return null;
		}
	}

	protected class Reward implements RewardFunction {

		@Override
		public double reward(State s, Action a, State sp) {

			return 0;
		}
	}

	protected class Terminal implements TerminalFunction {

		@Override
		public boolean isTerminal(State s) {

			return true;
		}
	}

}
