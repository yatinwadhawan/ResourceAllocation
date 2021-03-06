import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class practice {

	public static void practiceEraser() throws IloException {

		// Values of the targets when it covered and uncovered by defender for
		// defender and attacker.
		int targets = 15;
		double[] defCov = { 11.0, 7.0, 14.0, 15.0, 19.0, 16.0, 17.0, 20, 25, 9,
				6, 22, 18, 3, 30 };
		double[] defUCov = { -22.0, -17.0, -28.0, -30.0, -38.0, -32.0, -35.0,
				-40, -50, -18, -12, -44, -36, -6, -60 };
		double[] attCov = { -33.0, -21.0, -42.0, -45.0, -57.0, -48.0, -51.0,
				-60, -75, -27, -18, -66, -54, -9, -90 };
		double[] attUCov = { 22.0, 14.0, 28.0, 30.0, 38.0, 32.0, 34.0, 40, 50,
				18, 14, 43, 35, 6, 60 };
		double[] hours = { 4.0, 2.0, 3.0, 10.0, 5.0, 6.0, 5.0, 15, 10, 5, 6, 2,
				7, 9, 3 };

		IloCplex cplex = new IloCplex();
		double Z = Double.MAX_VALUE;
		double B = 4.0; // Budget: number of hours defender has.

		// Objective value
		IloNumExpr D = cplex.numExpr();
		IloNumExpr K = cplex.numExpr();

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

		// Constraint 1
		cplex.addEq(cplex.sum(a), 1.0);

		// Constraint 2
		cplex.addLe(cplex.sum(c), B);
		// cplex.addLe(cplex.scalProd(c, hours), B);

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

		IloNumExpr[] Dpay = new IloNumExpr[targets];
		for (int t = 0; t < targets; t++) {
			Dpay[t] = cplex.prod(
					cplex.sum(cplex.prod(defCov[t], c[t]),
							cplex.prod(defUCov[t], cplex.diff(1.0, c[t]))),
					a[t]);
		}
		cplex.addMaximize(cplex.sum(Dpay));

		boolean flag = cplex.solve();
		System.out.print(cplex);
		System.out.println();
		System.out.println();
		System.out.println("Results");
		System.out.println("Defender Cost Constraints: " + B + " Hours");

		if (flag) {
			System.out.println("Solution Status = " + cplex.getStatus());
			double objval = cplex.getObjValue();
			System.out.println("Optimal Value: " + cplex.getObjValue());
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

	public static void practice2() throws IloException {
		IloCplex cplex = new IloCplex();

		// Variables
		IloNumVar[] x = cplex.numVarArray(6, 0, Double.MAX_VALUE);

		// Objective Function
		double[] coeff = { 5, 6, 7, 5, 6, 7 };
		cplex.addMaximize(cplex.scalProd(x, coeff));

		// constraints
		cplex.addLe(
				cplex.sum(cplex.prod(2, x[0]), cplex.prod(3, x[1]),
						cplex.prod(2, x[2]), cplex.prod(1, x[3]),
						cplex.prod(1, x[4]), cplex.prod(3, x[5])), 1050);

		cplex.addLe(
				cplex.sum(cplex.prod(2, x[0]), cplex.prod(1, x[1]),
						cplex.prod(3, x[2]), cplex.prod(1, x[3]),
						cplex.prod(3, x[4]), cplex.prod(2, x[5])), 1050);

		cplex.addLe(
				cplex.sum(cplex.prod(1, x[0]), cplex.prod(2, x[1]),
						cplex.prod(1, x[2]), cplex.prod(4, x[3]),
						cplex.prod(1, x[4]), cplex.prod(2, x[5])), 1080);
		if (cplex.solve()) {
			System.out.println("Solution status = " + cplex.getStatus());
			System.out.println("Solution value = " + cplex.getObjValue());
			for (int i = 0; i < x.length; i++)
				System.out.println("x[" + i + "] = " + cplex.getValue(x[i]));
		}
		cplex.end();

	}

	public static void practice1() {
		try {
			IloCplex cplex = new IloCplex();
			IloNumVar x = cplex.numVar(0.0, 5.0);
			IloNumVar y = cplex.numVar(0.0, 10.0);
			IloNumVar z = cplex.numVar(0.0, 20.0);

			IloLinearNumExpr expr = cplex.linearNumExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(1.0, y);
			expr.addTerm(1.0, z);
			cplex.addLe(expr, 20);

			IloLinearNumExpr obj = cplex.linearNumExpr();
			obj.addTerm(1.0, x);
			obj.addTerm(12.0, y);
			obj.addTerm(4.0, z);
			cplex.addMaximize(obj);

			if (cplex.solve()) {
				System.out.println("Solution status = " + cplex.getStatus());
				System.out.println("Solution value = " + cplex.getObjValue());
				System.out.print("x = " + cplex.getValue(x));
				System.out.print("\ny = " + cplex.getValue(y));
				System.out.print("\nz = " + cplex.getValue(z));
			}
			cplex.end();

		} catch (IloException e) {
		}
	}
}
