import java.util.List;

import scpsolver.graph.Graph;
import burlap.mdp.core.state.MutableState;

public class State implements MutableState {

	public Graph graph;

	public State(Graph g) {
		this.graph = g;
	}

	@Override
	public burlap.mdp.core.state.State copy() {
		return new State(graph);
	}

	@Override
	public Object get(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> variableKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutableState set(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

}