import java.util.Random;

import ilog.concert.*;
import ilog.cplex.*;

public class ERASER {

	public static int randomNumberInRange(int min, int max) {
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	public static void solve() throws IloException {
		// int targets = 3;
		// double Z = 9999;
		// double B = 10; // Budget: number of hours defender has.
		//
		// int[] hours = new int[targets];
		// int[] defCov = new int[targets];
		// int[] attUCov = new int[targets];
		// int[] defUCov = new int[targets];
		// int[] attCov = new int[targets];
		// for (int t = 0; t < targets; t++) {
		// defCov[t] = randomNumberInRange(0, 100);
		// attUCov[t] = randomNumberInRange(0, 100);
		// defUCov[t] = randomNumberInRange(-100, 0);
		// attCov[t] = randomNumberInRange(-100, 0);
		// hours[t] = randomNumberInRange(5, 15);
		// }

		int targets = 7;
		double Z = 9999;
		double B = 20; // Budget: number of hours defender has.

		double[] defCov = { 11.0, 7.0, 14.0, 15.0, 19.0, 16.0, 17.0 };
		double[] defUCov = { -12.0, -14.0, -35.0, -19.0, -38.0, -14.0, -21.0 };
		double[] attCov = { -21.0, -31.0, -13.0, -16.0, -25.0, -33.0, -41.0 };
		double[] attUCov = { 14.0, 23.0, 30.0, 17.0, 52.0, 16.0, 19.0 };
		double[] hours = { 10.0, 2.0, 3.0, 10.0, 7.0, 6.0, 5.0 };

		IloCplex cplex = new IloCplex();
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

		// Objective Function
		IloNumVar D = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);
		IloNumExpr K = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);
		cplex.addMaximize(D);

		// Constraint 1
		cplex.addEq(cplex.sum(a), 1.0);

		// Constraint 2
		cplex.addLe(cplex.scalProd(c, hours), B);

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
		System.out.print("Defender Covered: ");
		for (int t = 0; t < targets; t++) {
			System.out.print(defCov[t] + ", ");
		}
		System.out.println();
		System.out.print("Attacker Covered: ");
		for (int t = 0; t < targets; t++) {
			System.out.print(attCov[t] + ", ");
		}
		System.out.println();
		System.out.print("Defender Uncovered: ");
		for (int t = 0; t < targets; t++) {
			System.out.print(defUCov[t] + ", ");
		}
		System.out.println();
		System.out.print("Attack Uncovered: ");
		for (int t = 0; t < targets; t++) {
			System.out.print(attUCov[t] + ", ");
		}
		System.out.println();
		System.out.print("hours per target: ");
		for (int t = 0; t < targets; t++) {
			System.out.print(hours[t] + ", ");
		}
		System.out.println();
		System.out.println();
		System.out.print("Results");

		if (flag) {
			System.out.println();
			System.out.println("Solution Status = " + cplex.getStatus());
			System.out.println("Optimal Value: " + cplex.getObjValue());
			System.out.println("K: " + cplex.getValue(K));
			System.out.print("Coverage Vector: ");
			double[] val = cplex.getValues(c);
			double sum = 0.0;
			for (int j = 0; j < val.length; j++) {
				System.out.print(val[j] + " , ");
				sum = sum + val[j] * defCov[j] + (1 - val[j]) * defUCov[j];
			}
			System.out.println("Optimal Value SUM: " + sum);
			System.out.println();
			System.out.print("Attack Vector:   ");
			val = cplex.getValues(a);
			for (int j = 0; j < val.length; j++) {
				System.out.print(val[j] + " , ");
			}
			System.out.println();
			System.out.println();
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
