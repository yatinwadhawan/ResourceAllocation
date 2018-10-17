import java.util.Random;

public class Input {

	static double Z = 9999.0;
	static double B = 40.0; // Budget: number of hours defender has.

	 // Region 1 (SGC Paper)
//	 static int targets = 7;
//	 static double[] defCov = { 11.0, 7.0, 14.0, 15.0, 19.0, 16.0, 17.0 };
//	 static double[] defUCov = { -22.0, -34.0, -65.0, -19.0, -38.0, -14.0, -21.0 };
//	 static double[] attCov = { -81.0, -11.0, -13.0, -96.0, -25.0, -33.0, -91.0 };
//	 static double[] attUCov = { 4.0, 23.0, 30.0, 10.0, 76.0, 16.0, 64.0 };
//	 static double[] hours = { 9.0, 3.0, 4.0, 8.0, 7.0, 6.0, 5.0 };


	 // Region 2
//	 static int targets = 6;
//	 static double[] defCov = { 12.0, 11.0, 15.0, 19.0, 22.0, 21.0 };
//	 static double[] defUCov = { -24.0, -22.0, -30.0, -18.0, -20.0, -24.0 };
//	 static double[] attCov = { -24.0, -10.0, -25.0, -27.0, -30.0, -33.0 };
//	 static double[] attUCov = { 20.0, 17.0, 30.0, 34.0, 38.0, 34.0 };
//	 static double[] hours = { 5.0, 2.0, 7.0, 6.0, 4.0, 9.0 };

  	 // Region 3
//	 static int targets = 7;
//	 static double[] defCov = { 14.0, 15.0, 15.0, 9.0, 15.0, 12.0, 20.0 };
//	 static double[] defUCov = { -20.0, -10.0, -24.0, -15.0, -10.0, -10.0,-40.0 };
//	 static double[] attCov = { -26.0, -10.0, -22.0, -20.0, -36.0, -24.0,-31.0 };
//	 static double[] attUCov = { 25.0, 30.0, 36.0, 16.0, 21.0, 18.0, 23.0 };
//	 static double[] hours = { 9.0, 14.0, 6.0, 9.0, 11.0, 4.0, 12.0 };
	
	 // Region 1 (Sensitivity Analysis)
	 static int targets = 3;
	 static double[] defCov = { 8.0, 16.0, 17.0 };
	 static double[] defUCov = { -14.0, -49.0, -24.0 };
	 static double[] attCov = { -11.0, -48.0, -13.0 };
	 static double[] attUCov = { 33.0, 10.0, 56.0 };
	 static double[] hours = { 6.0, 8.0, 6.0 };


	public static int randomNumberInRange(int min, int max) {
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	// When input called.
	public static void input() {
		for (int t = 0; t < targets; t++) {
			// defCov[t] = randomNumberInRange(0, 100);
			attUCov[t] = randomNumberInRange(0, 100);
			defUCov[t] = randomNumberInRange(-100, 0);
			attCov[t] = randomNumberInRange(-100, 0);
			// hours[t] = randomNumberInRange(5, 15);
		}

	}

}
