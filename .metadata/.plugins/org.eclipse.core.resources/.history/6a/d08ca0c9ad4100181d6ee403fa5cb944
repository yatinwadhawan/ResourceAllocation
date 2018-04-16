import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloNumVarType;
import ilog.cplex.IloCplex;

public class MyGameMIQP {

	public static void solve() {

		// Defender cyber and physical strategies
		int mixedCyberD = 5;
		int mixedPhysicalD = 3;

		// Cyber and Physical attacker strategies
		int mixedCyberA = 6;
		int mixedPhysicalA = 4;

		// Defender rewards
		double[][] cyberD = new double[mixedCyberD][mixedCyberA];
		double[][] physicalD = new double[mixedPhysicalD][mixedPhysicalA];

		// Cyber and Physical attacker rewards
		double[][] cyberA = new double[mixedCyberD][mixedCyberA];
		double[][] physicalA = new double[mixedPhysicalD][mixedPhysicalA];

		// Domain values
		double volume = 1000.0;
		double weigth = 0.5;
		double share = 0.5;
		double wvP = share * (1 - weigth) * volume;
		double wvC = (1 - share) * (1 - weigth) * volume;
		double[][] ability = new double[mixedCyberD][mixedPhysicalD];

		// ability of the attacker
		// for (int i = 0; i < mixedCyberD; i++) {
		// for (int j = 0; j < mixedCyberA; j++) {
		// ability[i][j] = Math.random();
		// }
		// }

		for (int i = 0; i < mixedCyberD; i++) {
			for (int j = 0; j < mixedCyberA; j++) {
				double x = Math.random();
				double y = Math.random();
				if (i == j) {
					if (x > y) {
						cyberD[i][j] = x;
						cyberA[i][j] = y;
					} else {
						cyberD[i][j] = y;
						cyberA[i][j] = x;
					}
				} else {
					if (x > y) {
						cyberD[i][j] = y;
						cyberA[i][j] = x;
					} else {
						cyberD[i][j] = x;
						cyberA[i][j] = y;
					}
				}
			}
		}

		for (int i = 0; i < mixedPhysicalD; i++) {
			for (int j = 0; j < mixedPhysicalA; j++) {
				double x = Math.random();
				double y = Math.random();
				if (i == j) {
					if (x > y) {
						physicalD[i][j] = x;
						physicalA[i][j] = y;
					} else {
						physicalD[i][j] = y;
						physicalA[i][j] = x;
					}
				} else {
					if (x > y) {
						physicalD[i][j] = y;
						physicalA[i][j] = x;
					} else {
						physicalD[i][j] = x;
						physicalA[i][j] = y;
					}
				}
			}
		}

		try {

			IloCplex cplex = new IloCplex();

			// Attackers utility
			IloNumVar aNC = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);
			IloNumVar bNC = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);
			IloNumVar aC = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);
			IloNumVar bC = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE);

			// Boolean Variables
			IloNumVar[] c = cplex.boolVarArray(mixedCyberA);
			IloNumVar[] p = cplex.boolVarArray(mixedPhysicalA);
			IloNumVar alpha = cplex.boolVar();
			IloNumVar beta = cplex.boolVar();

			// Probability variables
			IloNumVar[] m = new IloNumVar[mixedCyberD];
			IloNumVar[] n = new IloNumVar[mixedPhysicalD];

			// Constraint 1
			IloLinearNumExpr const1 = cplex.linearNumExpr();
			for (int i = 0; i < mixedCyberA; i++) {
				const1.addTerm(1.0, c[i]);
			}
			cplex.addEq(const1, 1.0);

			// Constraint 2
			IloLinearNumExpr const2 = cplex.linearNumExpr();
			for (int i = 0; i < mixedCyberD; i++) {
				m[i] = cplex.numVar(0, 1, IloNumVarType.Float);
				cplex.addGe(m[i], 0);
				const2.addTerm(1.0, m[i]);
			}
			cplex.addEq(const2, 1.0);

			// Constraint 3
			IloLinearNumExpr const3 = cplex.linearNumExpr();
			for (int i = 0; i < mixedPhysicalA; i++) {
				const3.addTerm(1.0, p[i]);
			}
			cplex.addEq(const3, 1.0);

			// Constraint 4
			IloLinearNumExpr const4 = cplex.linearNumExpr();
			for (int i = 0; i < mixedPhysicalD; i++) {
				n[i] = cplex.numVar(0, 1, IloNumVarType.Float);
				cplex.addGe(n[i], 0);
				const4.addTerm(1.0, n[i]);
			}
			cplex.addEq(const4, 1.0);

			// Constraint 5
			for (int i = 0; i < mixedCyberA; i++) {
				IloLinearNumExpr internal = cplex.linearNumExpr();
				for (int x = 0; x < mixedCyberD; x++) {
					internal.addTerm(cyberA[x][i] + wvC * Math.random(), m[x]);
				}

				cplex.addGe(cplex.diff(aC, internal), 0);
				cplex.addLe(cplex.diff(aC, internal),
						cplex.prod(Double.MAX_VALUE, cplex.diff(1, c[i])));
			}

			// Constraint 6
			for (int j = 0; j < mixedPhysicalA; j++) {
				IloLinearNumExpr internal = cplex.linearNumExpr();
				for (int i = 0; i < mixedPhysicalD; i++) {
					internal.addTerm(physicalA[i][j] + wvP * Math.random(),
							n[i]);
				}

				cplex.addGe(cplex.diff(bC, internal), 0);
				cplex.addLe(cplex.diff(bC, internal),
						cplex.prod(Double.MAX_VALUE, cplex.diff(1, p[j])));
			}

			// Constraint 7
			for (int i = 0; i < mixedCyberA; i++) {
				IloLinearNumExpr internal = cplex.linearNumExpr();
				for (int x = 0; x < mixedCyberD; x++) {
					internal.addTerm(cyberA[x][i], m[x]);
				}

				cplex.addGe(cplex.diff(aNC, internal), 0);
				cplex.addLe(cplex.diff(aNC, internal),
						cplex.prod(Double.MAX_VALUE, cplex.diff(1, c[i])));
			}

			// Constraint 8
			for (int j = 0; j < mixedPhysicalA; j++) {
				IloLinearNumExpr internal = cplex.linearNumExpr();
				for (int i = 0; i < mixedPhysicalD; i++) {
					internal.addTerm(physicalA[i][j], n[i]);
				}

				cplex.addGe(cplex.diff(bNC, internal), 0);
				cplex.addLe(
						cplex.diff(bNC, internal),
						cplex.prod(Double.MAX_VALUE,
								cplex.prod(p[j], Double.MAX_VALUE)));
			}

			// Constraint 29
			cplex.addLe(cplex.diff(aC, aNC),
					cplex.prod(cplex.diff(1.0, alpha), Double.MAX_VALUE));
			cplex.addGe(cplex.diff(aC, aNC),
					cplex.prod(alpha, -Double.MAX_VALUE));

			// Constraint 30
			cplex.addLe(cplex.diff(bC, bNC),
					cplex.prod(cplex.diff(1.0, beta), Double.MAX_VALUE));
			cplex.addGe(cplex.diff(bC, bNC),
					cplex.prod(beta, -Double.MAX_VALUE));

			// Constraint 31
			cplex.addLe(cplex.prod(alpha, beta), 1.0);
			cplex.addGe(cplex.prod(alpha, beta), 0.0);

			int k = 0;
			IloNumExpr[] z = new IloNumExpr[mixedCyberD * mixedPhysicalD
					* mixedCyberA * mixedPhysicalA];
			for (int x = 0; x < mixedCyberD; x++) {
				for (int y = 0; y < mixedPhysicalD; y++) {
					for (int i = 0; i < mixedCyberA; i++) {
						for (int j = 0; j < mixedPhysicalA; j++) {

							z[k++] = cplex.sum(
									cplex.prod(cyberD[x][i],
											cplex.prod(m[x], c[i])),
									cplex.prod(physicalD[y][j],
											cplex.prod(n[y], p[j])));

						}
					}
				}
			}

			IloNumExpr obj = cplex.sum(z);
			cplex.addMaximize(obj);
			System.out.print(cplex);
			boolean flag = cplex.solve();
			System.out.print(cplex);

			if (flag) {
				System.out.println("Solution status = " + cplex.getStatus());
				System.out.println("Solution value = " + cplex.getObjValue());
				double[] val = cplex.getValues(m);
				for (int j = 0; j < val.length; j++) {
					System.out.print(val[j] + " , ");
				}
				System.out.println();
				System.out.println();

				double[] val1 = cplex.getValues(n);
				for (int j = 0; j < val1.length; j++) {
					System.out.print(val1[j] + " , ");
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
