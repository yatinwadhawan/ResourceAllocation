package classes;

public class Vulnerability {

	private String name;
	private String details;
	private double cvssScore;
	private double probability;

	public Vulnerability() {
		System.out.println("Vulnerability created");
		this.name = "";
		this.details = "";
		this.cvssScore = 0;
		this.probability = 0.0;
	}

	public Vulnerability(String name, String details, double cvss, double prob) {
		this.name = name;
		this.details = details;
		this.cvssScore = cvss;
		this.probability = prob;
	}

	public void print() {
		System.out.println("Name: " + this.name);
		System.out.println("Details: " + this.details);
		System.out.println("CVSS Score: " + this.cvssScore);
		System.out.println("Compromise Probability: " + this.probability);
	}

	public static void print(Vulnerability v) {
		System.out.println("Name: " + v.name);
		System.out.println("Details: " + v.details);
		System.out.println("CVSS Score: " + v.cvssScore);
		System.out.println("Compromise Probability: " + v.probability);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public double getCvssScore() {
		return cvssScore;
	}

	public void setCvssScore(double cvssScore) {
		this.cvssScore = cvssScore;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}
}
