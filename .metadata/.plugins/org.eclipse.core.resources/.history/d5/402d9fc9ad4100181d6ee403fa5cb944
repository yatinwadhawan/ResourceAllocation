import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloModeler;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloNumVarType;
import ilog.cplex.IloCplex;

public class DOBBS {
	public static void solve() {
		System.gc();
		int mixedDef = 45;
		int mixedAttacker = 10;
		double[][] dr = new double[mixedDef][mixedAttacker];
		double[][] ar = new double[mixedDef][mixedAttacker];
		for (int i = 0; i < mixedDef; i++) {
			for (int j = 0; j < mixedAttacker; j++) {
				double x = Math.random() * 10;
				double y = Math.random() * 10;
				if (i == j) {
					if (x > y) {
						dr[i][j] = x;
						ar[i][j] = y;
					} else {
						dr[i][j] = y;
						ar[i][j] = x;
					}
				} else {
					if (x > y) {
						dr[i][j] = y;
						ar[i][j] = x;
					} else {
						dr[i][j] = x;
						ar[i][j] = y;
					}
				}
			}
		}
		try {
			IloCplex cplex = new IloCplex();
			IloNumVar[] q = new IloNumVar[mixedAttacker];
			IloNumVar[] x = new IloNumVar[mixedDef];
			IloNumVar a = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);

			// Constraint 1
			IloLinearNumExpr const1 = cplex.linearNumExpr();
			for (int i = 0; i < mixedDef; i++) {
				x[i] = cplex.numVar(0, 1, IloNumVarType.Float);
				cplex.addGe(x[i], 0);
				const1.addTerm(1.0, x[i]);
			}
			cplex.addEq(const1, 1.0);

			// Constraint 2
			IloLinearNumExpr const2 = cplex.linearNumExpr();
			for (int j = 0; j < mixedAttacker; j++) {
				q[j] = cplex.boolVar();
				const2.addTerm(1.0, q[j]);
			}
			cplex.addEq(const2, 1.0);

			// Constraint 3
			for (int j = 0; j < mixedAttacker; j++) {
				IloLinearNumExpr internal = cplex.linearNumExpr();
				for (int i = 0; i < mixedDef; i++) {
					internal.addTerm(ar[i][j], x[i]);
				}

				cplex.addGe(cplex.diff(a, internal), 0);
				cplex.addLe(cplex.diff(a, internal),
						cplex.prod(cplex.diff(1, q[j]), Double.MAX_VALUE));
			}

			// Objective Function
			int k = 0;
			IloNumExpr[] z = new IloNumExpr[mixedDef * mixedAttacker];
			for (int i = 0; i < mixedDef; i++) {
				for (int j = 0; j < mixedAttacker; j++) {
					z[k++] = cplex.prod(dr[i][j], cplex.prod(x[i], q[j]));
				}
			}
			IloNumExpr obj = cplex.sum(z);
			cplex.addMaximize(obj);

			boolean flag = cplex.solve();
			System.out.print(cplex);

			if (flag) {
				System.out.println("Solution status = " + cplex.getStatus());
				System.out.println("Attacker Value 'a': " + cplex.getValue(a));
				System.out.println("obj Value: " + cplex.getValue(obj));
				double[] val = cplex.getValues(x);
				for (int j = 0; j < val.length; j++) {
					System.out.print(val[j] + " , ");
				}
				System.out.println();

			} else {
				System.out.println("Not solved");
				System.out.println(cplex.isPrimalFeasible());
				System.out.println(cplex.isDualFeasible());
				System.out.println(cplex.isMIP());
				System.gc();
			}
			cplex.end();

		} catch (IloException e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
}