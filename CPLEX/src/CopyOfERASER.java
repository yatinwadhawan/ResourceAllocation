import ilog.concert.*;
import ilog.cplex.*;

public class CopyOfERASER {

	public static void solve() throws IloException {
		// Values of the targets when it covered and uncovered by defender for
		// defender and attacker.
		int targets = 7;
		double[] defCov = { 11.0, 7.0, 14.0, 15.0, 19.0, 16.0, 17.0 };
		double[] defUCov = { -22.0, -17.0, -28.0, -30.0, -38.0, -32.0, -35.0 };
		double[] attCov = { -33.0, -21.0, -42.0, -45.0, -57.0, -48.0, -51.0 };
		double[] attUCov = { 22.0, 14.0, 28.0, 30.0, 38.0, 32.0, 34.0 };
		double[] hours = { 4.0, 2.0, 3.0, 10.0, 5.0, 6.0, 5.0 };

		IloCplex cplex = new IloCplex();
		double Z = Double.MAX_VALUE;
		double B = 2; // Budget: number of hours defender has.

		// Defender coverage vector for targets and Constraints 0 to 1
		IloNumVar[] c = cplex.numVarArray(targets, 0.0, 1.0);

		// Attacker attack vector for targets and Constraints 0 or 1
		IloNumVar[] a = cplex.boolVarArray(targets);

		// Equation gives the defender utility for an attack on target
		// t, given his coverage vector
		IloNumExpr[] defUtil = new IloNumExpr[targets];
		for (int t = 0; t < targets; t++) {
			defUtil[t] = cplex.sum(cplex.prod(defCov[t], c[t]),
					cplex.prod(defUCov[t], cplex.diff(1.0, c[t])));
		}

		// Equation gives the attacker utility for an attack on target
		// t, given his coverage vector
		IloNumExpr[] attUtil = new IloNumExpr[targets];
		for (int t = 0; t < targets; t++) {
			attUtil[t] = cplex.sum(cplex.prod(attCov[t], c[t]),
					cplex.prod(attUCov[t], cplex.diff(1.0, c[t])));
		}

		// Objective Function: D
		IloNumExpr[] Dpay = new IloNumExpr[targets];
		for (int t = 0; t < targets; t++) {
			Dpay[t] = cplex.prod(
					cplex.sum(cplex.prod(defCov[t], c[t]),
							cplex.prod(defUCov[t], cplex.diff(1.0, c[t]))),
					a[t]);
		}
		IloNumExpr D = cplex.sum(Dpay);
		cplex.addMaximize(D);

		// Attacker Function: K
		IloNumExpr[] Apay = new IloNumExpr[targets];
		for (int t = 0; t < targets; t++) {
			Apay[t] = cplex.prod(
					cplex.sum(cplex.prod(attCov[t], c[t]),
							cplex.prod(attUCov[t], cplex.diff(1.0, c[t]))),
					a[t]);
		}
		IloNumExpr K = cplex.sum(Apay);

		// Constraint 1
		cplex.addEq(cplex.sum(a), 1.0);

		// Constraint 2
		cplex.addLe(cplex.sum(c), B);
		//cplex.addLe(cplex.scalProd(c, hours), B);

		// Constraint 3
		for (int t = 0; t < targets; t++) {
			cplex.addLe(cplex.diff(D, defUtil[t]),
					cplex.prod(cplex.diff(1.0, a[t]), Z));
		}
		// Constraint 4
		for (int t = 0; t < targets; t++) {
			cplex.addGe(K, attUtil[t]);
			cplex.addLe(cplex.diff(K, attUtil[t]),
					cplex.prod(cplex.diff(1.0, a[t]), Z));
		}

		boolean flag = cplex.solve();
		System.out.print(cplex);
		System.out.println();
		System.out.println();
		System.out.print("Results");

		if (flag) {
			System.out.println();
			System.out.println("Solution Status = " + cplex.getStatus());
			double objval = cplex.getObjValue();
			System.out.println("D Optimal Value: " + cplex.getObjValue());
			System.out.println("K: " + cplex.getValue(K));
			System.out.print("Coverage Vector: ");
			double[] val = cplex.getValues(c);
			for (int j = 0; j < val.length; j++) {
				System.out.print(val[j] + " , ");
			}
			System.out.println();
			System.out.print("Attack Vector:   ");
			val = cplex.getValues(a);
			for (int j = 0; j < val.length; j++) {
				System.out.print(val[j] + " , ");
			}
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