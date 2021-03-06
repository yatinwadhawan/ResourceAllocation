import ilog.concert.*;
import ilog.cplex.*;

public class ERASER {

	public static void solve() throws IloException {

		IloCplex cplex = new IloCplex();
		// Defender coverage vector for targets and Constraints 0 to 1
		IloNumVar[] c = cplex.numVarArray(Input.targets, 0.0, 1.0);

		// Attacker attack vector for targets and Constraints 0 or 1
		IloNumVar[] a = cplex.boolVarArray(Input.targets);

		// Equation gives the defender utility for an attack on target
		// t, given his coverage vector
		IloNumExpr[] defUtil = new IloNumExpr[Input.targets];
		for (int t = 0; t < Input.targets; t++) {
			defUtil[t] = cplex.sum(cplex.prod(Input.defCov[t], c[t]),
					cplex.prod(Input.defUCov[t], cplex.diff(1.0, c[t])));
		}

		// Equation gives the attacker utility for an attack on target
		// t, given his coverage vector
		IloNumExpr[] attUtil = new IloNumExpr[Input.targets];
		for (int t = 0; t < Input.targets; t++) {
			attUtil[t] = cplex.sum(cplex.prod(Input.attCov[t], c[t]),
					cplex.prod(Input.attUCov[t], cplex.diff(1.0, c[t])));
		}

		// Objective Function
		IloNumVar D = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);
		IloNumVar K = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);
		cplex.addMaximize(D);

		// Constraint 1
		cplex.addEq(cplex.sum(a), 1.0);

		// Constraint 2
		cplex.addLe(cplex.scalProd(c, Input.hours), Input.B);

		// Constraint 3
		for (int t = 0; t < Input.targets; t++) {
			cplex.addLe(cplex.diff(D, defUtil[t]),
					cplex.prod(cplex.diff(1.0, a[t]), Input.Z));
		}
		// Constraint 4
		for (int t = 0; t < Input.targets; t++) {
			cplex.addGe(K, attUtil[t]);
			cplex.addLe(cplex.diff(K, attUtil[t]),
					cplex.prod(cplex.diff(1.0, a[t]), Input.Z));
		}

		boolean flag = cplex.solve();
		System.out.println();
		System.out.println();
		System.out.print("Defender Covered: ");
		for (int t = 0; t < Input.targets; t++) {
			System.out.print(Input.defCov[t] + ", ");
		}
		System.out.println();
		System.out.print("Defender Uncovered: ");
		for (int t = 0; t < Input.targets; t++) {
			System.out.print(Input.defUCov[t] + ", ");
		}
		System.out.println();
		System.out.print("Attacker Covered: ");
		for (int t = 0; t < Input.targets; t++) {
			System.out.print(Input.attCov[t] + ", ");
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
		System.out.println("********************************************");
		System.out.println("********************************************");
		System.out.print("Results");

		if (flag) {
			System.out.println();
			System.out.println("Solution Status = " + cplex.getStatus());
			System.out.print("Coverage Vector: ");
			double[] val = cplex.getValues(c);
			double sum = 0.0;
			for (int j = 0; j < val.length; j++) {
				System.out.print(val[j] + " , ");
				sum = sum + val[j] * Input.defCov[j] + (1 - val[j])
						* Input.defUCov[j];
			}
			System.out.println();
			System.out.println("Optimal Value (SUM): " + sum);
			System.out.println("Optimal Value (D): " + cplex.getObjValue());
			System.out.println("Value (K): " + cplex.getValue(K));
			System.out.print("Attack Vector:   ");
			double[] aval = cplex.getValues(a);
			for (int j = 0; j < aval.length; j++) {
				System.out.print(aval[j] + " , ");
			}
			System.out.println();
			System.out.println();
			//			SamplePolicy.samplePolicy(val, Input.hours, Input.B);
			System.out.println("********************************************");
			System.out.println("********************************************");
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
