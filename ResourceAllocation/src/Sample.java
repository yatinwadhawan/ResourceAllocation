public class Sample implements Comparable {
	String name;
	double coverage;
	double hours;

	public Sample(String n, double c, double h) {
		this.name = n;
		this.coverage = c;
		this.hours = h;
	}

	public int compareTo(Object o) {
		Sample s = (Sample) o;
		if (this.coverage == s.coverage)
			return 0;
		else
			return this.coverage > s.coverage ? -1 : 1;
	}
}
