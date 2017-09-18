import java.util.ArrayList;

import Graph.Graph;
import Graph.Node;
import Graph.NodeStatus;

public class MainClass {

	public static void main(String[] args) {

		// We have to write a separate function for parsing input to the model.
		// This is dummy input for testing purpose.
		ArrayList<Node> nlist = new ArrayList<Node>();
		for (int i = 0; i < 4; i++) {
			Node n = new Node("N" + i, "N" + i, NodeStatus.UNKNOWN, 0.6);
			nlist.add(n);
		}
		ArrayList<Node> l = new ArrayList<Node>();
		l.add(nlist.get(1));
		l.add(nlist.get(2));
		nlist.get(0).setAdj(l);
		ArrayList<Node> l1 = new ArrayList<Node>();
		l1.add(nlist.get(3));
		nlist.get(1).setAdj(l1);
		nlist.get(2).setAdj(l1);
		nlist.get(3).setAdj(new ArrayList<Node>());

		Graph g = new Graph(nlist.size(), 4, nlist, nlist.get(0),
				nlist.get(nlist.size() - 1));

		for (int i = 0; i < 4; i++) {
			Node.print(nlist.get(i));
		}
	}

}
