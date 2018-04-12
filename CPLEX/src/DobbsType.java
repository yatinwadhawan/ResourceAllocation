import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloNumVarType;
import ilog.cplex.IloCplex;

public class DobbsType {
	public static void solve() {
		System.gc();
		int mixedDef = 2;
		int mixedAttacker = 2;
		double[][] dr = new double[mixedDef][mixedAttacker];
		double[][] ar = new double[mixedDef][mixedAttacker];
		for (int i = 0; i < mixedDef; i++) {
			for (int j = 0; j < mixedAttacker; j++) {
				double x = Math.random();
				double y = Math.random();
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
			IloNumVar[][] z = new IloNumVar[mixedDef][];
			IloNumVar[] q = cplex.boolVarArray(mixedAttacker);
			IloNumVar a = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);

			for (int i = 0; i < mixedDef; i++) {
				z[i] = cplex.numVarArray(mixedAttacker, 0.0, 1.0,
						IloNumVarType.Float);
			}

			// Constraint 1
			IloLinearNumExpr const1 = cplex.linearNumExpr();
			for (int i = 0; i < mixedDef; i++) {
				for (int j = 0; j < mixedAttacker; j++) {
					const1.addTerm(1.0, z[i][j]);
				}
			}
			cplex.addEq(const1, 1.0);

			// Constraint 2
			for (int i = 0; i < mixedDef; i++) {
				IloLinearNumExpr const2 = cplex.linearNumExpr();
				for (int j = 0; j < mixedAttacker; j++) {
					const2.addTerm(1.0, z[i][j]);
				}
				cplex.addLe(const2, 1.0);
			}

			// Constraint 3
			for (int j = 0; j < mixedAttacker; j++) {
				IloLinearNumExpr const3 = cplex.linearNumExpr();
				for (int i = 0; i < mixedDef; i++) {
					const3.addTerm(1.0, z[i][j]);
				}
				cplex.addGe(const3, q[j]);
				cplex.addLe(const3, 1.0);
			}

			// Constraint 4
			IloLinearNumExpr const4 = cplex.linearNumExpr();
			for (int j = 0; j < mixedAttacker; j++) {
				const4.addTerm(1.0, q[j]);
			}
			cplex.addEq(const4, 1.0);

			// Constraint 5
			for (int j = 0; j < mixedAttacker; j++) {
				IloLinearNumExpr internal = cplex.linearNumExpr();
				for (int i = 0; i < mixedDef; i++) {
					for (int h = 0; h < mixedAttacker; h++) {
						internal.addTerm(ar[i][j], z[i][h]);
					}
				}

				IloNumExpr ep1 = cplex.prod(cplex.diff(1.0, q[j]),
						Double.MAX_VALUE);

				cplex.addGe(cplex.diff(a, internal), 0.0);
				cplex.addLe(cplex.diff(a, internal), ep1);
			}

			// Objective Function
			IloLinearNumExpr obj = cplex.linearNumExpr();
			for (int i = 0; i < mixedDef; i++) {
				for (int j = 0; j < mixedAttacker; j++) {
					obj.addTerm(dr[i][j], z[i][j]);
				}
			}

			cplex.addMaximize(obj);

			boolean flag = cplex.solve();
			System.out.print(cplex);

			if (flag) {
				System.out.println("Solution status = " + cplex.getStatus());
				System.out.println("Solution value = " + cplex.getObjValue());
				System.out.println("Attacker Value 'a': " + cplex.getValue(a));
				for (int i = 0; i < mixedDef; i++) {
					double[] val = cplex.getValues(z[i]);
					for (int j = 0; j < val.length; j++) {
						System.out.print(val[j] + " , ");
					}
					System.out.println();
				}

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