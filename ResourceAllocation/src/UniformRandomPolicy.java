import java.lang.annotation.Target;

public class UniformRandomPolicy {

	public static void uniformPolicy() {
		int targets = 7;
		double Z = 9999;
		double B = 20.0; // Budget: number of hours defender has.

		double[] defCov = { 22.0, 14.0, 28.0, 30.0, 38.0, 32.0, 34.0 };
		double[] defUCov = { -22.0, -14.0, -35.0, -19.0, -38.0, -14.0, -21.0 };
		double[] attCov = { -21.0, -31.0, -13.0, -16.0, -25.0, -33.0, -41.0 };
		double[] attUCov = { 14.0, 23.0, 30.0, 17.0, 76.0, 16.0, 64.0 };
		double[] hours = { 9.0, 3.0, 4.0, 8.0, 7.0, 6.0, 5.0 };

		double sum = 0.0;
		double c = 1.0 / 7.0;
		double mc = (1 - c);
		double prob = 0.0;
		for (int i = 0; i < targets; i++) {
			sum = sum + c * defCov[i] + mc * defUCov[i];
			prob = prob + c * hours[i];
		}
		System.out.println("********************************************");
		System.out.println("Uniform Random Policy");
		System.out.println("Probability of covering each target: " + c);
		System.out.println("Optimal Value: " + sum);
		System.out.println("Probability : " + prob + " <= " + B);

	}

}
