import ilog.concert.*;
import ilog.cplex.*;

public class Cargo {
	public static void solve() {

		int cargo = 4, compartment = 3;
		double[] weigthComp = { 10.0, 16.0, 8.0 };
		double[] profit = { 310.0, 380.0, 350.0, 285.0 };
		double[] spaceComp = { 6800.0, 8700.0, 5300.0 };
		double[] weigthCargo = { 18.0, 15.0, 23.0, 12.0 };
		double[] volCargo = { 480.0, 650.0, 580.0, 390.0 };
		try {
			IloCplex cplex = new IloCplex();
			IloNumVar[][] x = new IloNumVar[cargo][];
			for (int i = 0; i < cargo; i++) {
				x[i] = cplex.numVarArray(4, 0, Double.MAX_VALUE);
			}
			IloNumVar y = cplex.numVar(0, Double.MAX_VALUE);
			IloLinearNumExpr[] usedCompCap = new IloLinearNumExpr[compartment];
			IloLinearNumExpr[] usedVolComp = new IloLinearNumExpr[compartment];
			for (int j = 0; j < compartment; j++) {
				usedCompCap[j] = cplex.linearNumExpr();
				usedVolComp[j] = cplex.linearNumExpr();
				for (int i = 0; i < cargo; i++) {
					usedCompCap[j].addTerm(1.0, x[i][j]);
					usedVolComp[j].addTerm(volCargo[i], x[i][j]);
				}
			}

			IloLinearNumExpr obj = cplex.linearNumExpr();
			for (int i = 0; i < cargo; i++) {
				for (int j = 0; j < compartment; j++) {
					obj.addTerm(profit[i], x[i][j]);
				}
			}

			cplex.addMaximize(obj);

			for (int i = 0; i < cargo; i++) {
				cplex.addLe(cplex.sum(x[i]), weigthCargo[i]);
			}

			for (int j = 0; j < compartment; j++) {
				cplex.addLe(usedCompCap[j], weigthComp[j]);
				cplex.addLe(usedVolComp[j], spaceComp[j]);
				cplex.addEq(cplex.prod(1 / weigthComp[j], usedCompCap[j]), y);
			}

			if (cplex.solve()) {
				System.out.println("Solution status = " + cplex.getStatus());
				System.out.println("Solution value = " + cplex.getObjValue());
				for (int i = 0; i < cargo; i++) {
					double[] val = cplex.getValues(x[i]);
					for (int j = 0; j < val.length; j++)
						System.out.println("Column: " + j + " Value = "
								+ val[j]);
				}
			}
		} catch (IloException e) {

		}

	}
}
