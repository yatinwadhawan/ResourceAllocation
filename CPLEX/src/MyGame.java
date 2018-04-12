import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloNumVarType;
import ilog.cplex.IloCplex;

public class MyGame {

	public static void solve() {

		// targets
		int cyber = 3;
		int physical = 3;
		int resourceC = 2;
		int resourcesP = 2;

		// Defender cyber and physical strategies
		int mixedCyberD = 3;
		int mixedPhysicalD = 3;

		// Cyber and Physical attacker strategies
		int mixedCyberA = 2;
		int mixedPhysicalA = 2;

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
		for (int i = 0; i < mixedCyberD; i++) {
			for (int j = 0; j < mixedCyberA; j++) {
				ability[i][j] = Math.random();
			}
		}

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
			IloNumVar[][] z_c = new IloNumVar[mixedCyberD][];
			IloNumVar[][] z_p = new IloNumVar[mixedPhysicalD][];

			for (int i = 0; i < mixedCyberD; i++) {
				z_c[i] = cplex.numVarArray(mixedCyberA, 0.0, 1.0,
						IloNumVarType.Float);
			}

			for (int i = 0; i < mixedPhysicalD; i++) {
				z_p[i] = cplex.numVarArray(mixedPhysicalA, 0.0, 1.0,
						IloNumVarType.Float);
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

			// Constraint 28
			for (int j = 0; j < mixedPhysicalA; j++) {
				IloLinearNumExpr exp28 = cplex.linearNumExpr();
				for (int y = 0; y < mixedPhysicalD; y++) {
					for (int h = 0; h < mixedPhysicalA; h++) {
						exp28.addTerm(physicalA[y][j], z_p[y][h]);
					}
				}
				cplex.addLe(cplex.diff(bNC, exp28),
						cplex.prod(cplex.diff(1.0, p[j]), Double.MAX_VALUE));
				cplex.addGe(cplex.diff(bNC, exp28), 0.0);
			}

			// Constraint 27
			for (int i = 0; i < mixedCyberA; i++) {
				IloLinearNumExpr exp27 = cplex.linearNumExpr();
				for (int x = 0; x < mixedCyberD; x++) {
					for (int h = 0; h < mixedCyberA; h++) {
						exp27.addTerm(cyberA[x][i], z_c[x][h]);
					}
				}
				cplex.addLe(cplex.diff(aNC, exp27),
						cplex.prod(cplex.diff(1.0, c[i]), Double.MAX_VALUE));
				cplex.addGe(cplex.diff(aNC, exp27), 0.0);
			}

			// Constraint 26
			for (int j = 0; j < mixedPhysicalA; j++) {
				IloLinearNumExpr exp28 = cplex.linearNumExpr();
				for (int y = 0; y < mixedPhysicalD; y++) {
					for (int h = 0; h < mixedPhysicalA; h++) {
						exp28.addTerm(physicalA[y][j] + wvP * ability[y][j],
								z_p[y][h]);
					}
				}
				cplex.addLe(cplex.diff(bNC, exp28),
						cplex.prod(cplex.diff(1.0, p[j]), Double.MAX_VALUE));
				cplex.addGe(cplex.diff(bNC, exp28), 0.0);
			}

			// Constraint 25
			for (int i = 0; i < mixedCyberA; i++) {
				IloLinearNumExpr exp27 = cplex.linearNumExpr();
				for (int x = 0; x < mixedCyberD; x++) {
					for (int h = 0; h < mixedCyberA; h++) {
						exp27.addTerm(cyberA[x][i] + wvC * ability[x][i],
								z_c[x][h]);
					}
				}
				cplex.addLe(cplex.diff(aNC, exp27),
						cplex.prod(cplex.diff(1.0, c[i]), Double.MAX_VALUE));
				cplex.addGe(cplex.diff(aNC, exp27), 0.0);
			}

			// Constraint 1
			IloLinearNumExpr const1 = cplex.linearNumExpr();
			for (int x = 0; x < mixedCyberD; x++) {
				for (int i = 0; i < mixedCyberA; i++) {
					const1.addTerm(1.0, z_c[x][i]);
				}
			}
			cplex.addEq(const1, 1.0);

			// Constraint 2
			IloLinearNumExpr const2 = cplex.linearNumExpr();
			for (int y = 0; y < mixedPhysicalD; y++) {
				for (int j = 0; j < mixedPhysicalA; j++) {
					const2.addTerm(1.0, z_p[y][j]);
				}
			}
			cplex.addEq(const2, 1.0);

			// Constraint 3
			for (int x = 0; x < mixedCyberD; x++) {
				IloLinearNumExpr const3 = cplex.linearNumExpr();
				for (int i = 0; i < mixedCyberA; i++) {
					const3.addTerm(1.0, z_c[x][i]);
				}
				cplex.addLe(const3, 1.0);
			}

			// Constraint 4
			for (int y = 0; y < mixedPhysicalD; y++) {
				IloLinearNumExpr const4 = cplex.linearNumExpr();
				for (int j = 0; j < mixedPhysicalA; j++) {
					const4.addTerm(1.0, z_p[y][j]);
				}
				cplex.addLe(const4, 1.0);
			}

			// Constraint 5
			IloLinearNumExpr const5 = cplex.linearNumExpr();
			for (int i = 0; i < mixedCyberA; i++) {
				const5.addTerm(1.0, c[i]);
			}
			cplex.addEq(const5, 1.0);

			// Constraint 6
			IloLinearNumExpr const6 = cplex.linearNumExpr();
			for (int j = 0; j < mixedPhysicalA; j++) {
				const6.addTerm(1.0, p[j]);
			}
			cplex.addEq(const6, 1.0);

			// Constraint 7
			for (int i = 0; i < mixedCyberA; i++) {
				IloLinearNumExpr const7 = cplex.linearNumExpr();
				for (int x = 0; x < mixedCyberD; x++) {
					const7.addTerm(1.0, z_c[x][i]);
				}
				cplex.addGe(const7, c[i]);
				cplex.addLe(const7, 1.0);
			}

			// Constraint 8
			for (int j = 0; j < mixedCyberA; j++) {
				IloLinearNumExpr const8 = cplex.linearNumExpr();
				for (int y = 0; y < mixedCyberD; y++) {
					const8.addTerm(1.0, z_p[y][j]);
				}
				cplex.addGe(const8, p[j]);
				cplex.addLe(const8, 1.0);
			}

			IloLinearNumExpr obj = cplex.linearNumExpr();
			for (int x = 0; x < mixedCyberD; x++) {
				for (int y = 0; y < mixedPhysicalD; y++) {
					for (int i = 0; i < mixedCyberA; i++) {
						for (int j = 0; j < mixedPhysicalA; j++) {
							IloLinearNumExpr e = cplex.linearNumExpr();
							e.addTerm(cyberD[x][i], z_c[x][i]);
							e.addTerm(physicalD[y][j], z_p[x][i]);
							obj.add(e);
						}
					}
				}
			}

			cplex.addMaximize(obj);

			boolean flag = cplex.solve();
			System.out.print(cplex);

			if (flag) {
				System.out.println("Solution status = " + cplex.getStatus());
				System.out.println("Solution value = " + cplex.getObjValue());
				for (int i = 0; i < mixedCyberD; i++) {
					double[] val = cplex.getValues(z_c[i]);
					for (int j = 0; j < val.length; j++) {
						System.out.print(val[j] + " , ");
					}
					System.out.println();
				}
				System.out.println();

				for (int i = 0; i < mixedPhysicalD; i++) {
					double[] val = cplex.getValues(z_p[i]);
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

		}

	}
}
