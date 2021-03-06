import java.lang.annotation.Target;

public class UniformRandomPolicy {

	public static void uniformPolicy() {
		int targets = 7;
		double B = 20; // Budget: number of hours defender has.

		double[] defCov = { 11.0, 7.0, 14.0, 15.0, 19.0, 16.0, 17.0 };
		double[] defUCov = { -12.0, -14.0, -35.0, -19.0, -38.0, -14.0, -21.0 };
		double[] attCov = { -21.0, -31.0, -13.0, -16.0, -25.0, -33.0, -41.0 };
		double[] attUCov = { 14.0, 23.0, 30.0, 17.0, 52.0, 16.0, 19.0 };
		double[] hours = { 10.0, 2.0, 3.0, 10.0, 7.0, 6.0, 5.0 };

		double sum = 0.0;
		double c = 1.0 / 7.0;
		double mc = 1 - 1 / 7.0;
		for (int i = 0; i < targets; i++) {
			sum = sum + c * defCov[i] + mc * defUCov[i];
		}
		System.out.println("********************************************");
		System.out.println("Uniform Random Policy");
		System.out.println("Probability of covering each target: " + c);
		System.out.println("Optimal Value: " + sum);

		double[] prob = { 0.1, 0.1, 0.1, 0.1, 0.4, 0.1, 0.1 };
		sum = 0.0;
		for (int i = 0; i < targets; i++) {
			sum = sum + prob[i] * defCov[i] + (1 - prob[i]) * defUCov[i];
		}
		System.out.println("********************************************");
		System.out.println("Weigthing Policy");
		System.out.println("Optimal Value: " + sum);

	}

}
