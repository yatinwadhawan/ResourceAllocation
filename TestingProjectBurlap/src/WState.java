import java.util.ArrayList;
import java.util.List;

import Graph.Graph;
import Graph.Node;
import Graph.NodeStatus;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.UnknownKeyException;

public class WState implements MutableState {

	public Graph graph;

	public WState(Graph g) {
		this.graph = g;
	}

	@Override
	public State copy() {
		return new WState(graph);
	}

	@Override
	public Object get(Object variableKeys) {
		if (variableKeys.equals(NodeStatus.VAR_NODES))
			return graph.getNodes();
		else if (variableKeys.equals(NodeStatus.VAR_EDGES))
			return graph.getEdges();
		else if (variableKeys.equals(NodeStatus.VAR_LIST))
			return graph.getNodelist();
		else if (variableKeys.equals(NodeStatus.VAR_START))
			return graph.getStartNode();
		else if (variableKeys.equals(NodeStatus.VAR_END))
			return graph.getEndNode();

		throw new UnknownKeyException(variableKeys);
	}

	@Override
	public List<Object> variableKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutableState set(Object variableKeys, Object value) {
		if (variableKeys.equals(NodeStatus.VAR_NODES))
			this.graph.setNodes((int) value);
		else if (variableKeys.equals(NodeStatus.VAR_EDGES))
			this.graph.setEdges((int) value);
		else if (variableKeys.equals(NodeStatus.VAR_LIST))
			this.graph.setNodelist((ArrayList<Node>) value);
		else if (variableKeys.equals(NodeStatus.VAR_START))
			this.graph.setStartNode((Node) value);
		else if (variableKeys.equals(NodeStatus.VAR_END))
			this.graph.setEndNode((Node) value);
		else
			throw new UnknownKeyException(variableKeys);

		return this;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	

}