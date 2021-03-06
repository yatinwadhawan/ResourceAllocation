package QLearning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import Graph.Node;
import Graph.NodeStatus;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;

public class WState implements MutableState {

	private ArrayList<Node> nodeList;

	private final static List<Object> keys = Arrays
			.<Object> asList(NodeStatus.VAR_LIST);

	public WState(ArrayList<Node> list) {
		this.nodeList = (ArrayList<Node>) list.clone();
	}

	@Override
	public boolean equals(Object obj) {
		WState that = (WState) obj;
		for (int i = 0; i < that.nodeList.size(); i++) {
			Node n1 = that.nodeList.get(i);
			Node n2 = this.nodeList.get(i);
			if (!n1.equals(n2)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nodeList);
	}

	@Override
	public State copy() {
		ArrayList<Node> l = new ArrayList<Node>();
		for (Node n : nodeList) {
			l.add(n.copy());
		}
		WState w = new WState(l);

		return w;
	}

	@Override
	public Object get(Object variableKeys) {

		if (variableKeys.equals(NodeStatus.VAR_LIST))
			return nodeList;

		throw new UnknownKeyException(variableKeys);
	}

	@Override
	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public MutableState set(Object variableKeys, Object value) {

		if (variableKeys.equals(NodeStatus.VAR_LIST))
			this.nodeList = (ArrayList<Node>) value;
		else
			throw new UnknownKeyException(variableKeys);

		return this;
	}

	@Override
	public String toString() {
		return StateUtilities.stateToString(this);
	}

	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(ArrayList<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public static List<Object> getKeys() {
		return keys;
	}

}