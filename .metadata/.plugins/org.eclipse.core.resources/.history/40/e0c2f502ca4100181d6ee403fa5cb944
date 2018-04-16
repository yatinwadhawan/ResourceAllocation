import ilog.concert.*;
import ilog.cplex.*;

public class ORIGAMI {

	public static void solve() throws IloException {

		IloCplex cplex = new IloCplex();
		IloNumVar[] c = cplex.numVarArray(Input.targets, 0.0, 1.0);
		IloNumVar[] a = cplex.boolVarArray(Input.targets);

		IloNumExpr[] attUtil = new IloNumExpr[Input.targets];
		for (int t = 0; t < Input.targets; t++) {
			attUtil[t] = cplex.sum(cplex.prod(Input.attCov[t], c[t]),
					cplex.prod(Input.attUCov[t], cplex.diff(1.0, c[t])));
		}

		IloNumExpr K = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);

		// Constraint 1
		cplex.addLe(cplex.scalProd(c, Input.hours), Input.B);

		// Constraint 2
		for (int t = 0; t < Input.targets; t++) {
			cplex.addLe(attUtil[t], K);
		}

		// Constraint 3
		for (int t = 0; t < Input.targets; t++) {
			cplex.addLe(cplex.diff(K, attUtil[t]),
					cplex.prod(cplex.diff(1.0, a[t]), Input.Z));
		}

		// Constraint 4
		for (int t = 0; t < Input.targets; t++) {
			cplex.addLe(c[t], a[t]);
		}

		// Objective
		cplex.addMinimize(K);

		boolean flag = cplex.solve();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("ORIGAMI");
		// System.out.print(cplex);
		System.out.println();
		System.out.print("Defender Covered: ");
		for (int t = 0; t < Input.targets; t++) {
			System.out.print(Input.defCov[t] + ", ");
		}
		System.out.println();
		System.out.print("Attacker Covered: ");
		for (int t = 0; t < Input.targets; t++) {
			System.out.print(Input.attCov[t] + ", ");
		}
		System.out.println();
		System.out.print("Defender Uncovered: ");
		for (int t = 0; t < Input.targets; t++) {
			System.out.print(Input.defUCov[t] + ", ");
		}
		System.out.println();
		System.out.print("Attack Uncovered: ");
		for (int t = 0; t < Input.targets; t++) {
			System.out.print(Input.attUCov[t] + ", ");
		}
		System.out.println();
		System.out.print("hours per target: ");
		for (int t = 0; t < Input.targets; t++) {
			System.out.print(Input.hours[t] + ", ");
		}
		System.out.println();
		System.out.println();
		System.out.print("Results of ORIGAMI Algorithm");

		if (flag) {
			System.out.println();
			System.out.println("Solution Status = " + cplex.getStatus());
			System.out.println("Optimal Value (K): " + cplex.getObjValue());
			System.out.println("K: " + cplex.getValue(K));
			System.out.print("Coverage Vector: ");
			double[] val = cplex.getValues(c);
			for (int j = 0; j < val.length; j++) {
				System.out.print(val[j] + " , ");
			}
			System.out.println();
			System.out.print("Attack Vector:   ");
			double[] aval = cplex.getValues(a);
			for (int j = 0; j < aval.length; j++) {
				System.out.print(aval[j] + " , ");
			}
			System.out.println();
			System.out.println();
			SamplePolicy.samplePolicy(val, Input.hours, Input.B);
		} else {
			System.out.println();
			System.out.println("Not solved");
			System.out.println("Primal Feasible: " + cplex.isPrimalFeasible());
			System.out.println("Dual Deasible: " + cplex.isDualFeasible());
			System.out.println("Is MIP: " + cplex.isMIP());
			System.gc();
		}
		cplex.end();

	}
}
