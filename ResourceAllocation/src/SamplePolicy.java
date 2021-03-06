import java.util.ArrayList;
import java.util.Collections;

public class SamplePolicy {

	@SuppressWarnings("unchecked")
	public static void samplePolicy(double[] coverage, double[] hours, double B) {

		ArrayList<Sample> ls = new ArrayList<Sample>();
		for (int j = 0; j < coverage.length; j++) {
			Sample s = new Sample("S" + (j + 1), coverage[j], hours[j]);
			ls.add(s);
		}

		Collections.sort(ls);
		for (int j = 0; j < ls.size(); j++) {
			System.out.println(ls.get(j).coverage);
		}
		double h = 0.0;
		double count = 0.0;
		ArrayList<Sample> sample = new ArrayList<Sample>();
		for (int i = 0; i < ls.size(); i++) {
			count = count + ls.get(i).hours;
			if (count <= B) {
				h = count;
				sample.add(ls.get(i));
			} else {
				count = h;
			}
		}
		System.out.println("Sample Policy");
		for (int j = 0; j < sample.size(); j++) {
			System.out.print(sample.get(j).name + "(H:" + sample.get(j).hours
					+ ") ,");
		}
		System.out.println();
		System.out.println("Total Hours: " + h + "<= " + B);
		System.out.println();
	}

}
