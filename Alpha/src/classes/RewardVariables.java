package classes;

public class RewardVariables {

	private String name;
	private String symbol;
	private String description;
	private Double value;

	@Override
	public RewardVariables clone() {
		RewardVariables n = new RewardVariables();
		n.name = this.name;
		n.symbol = this.symbol;
		n.description = this.description;
		n.value = 0.0;
		return n;
	}

	public void print() {
		System.out.println(this.name);
		System.out.println(this.symbol);
		System.out.println(this.description);
		System.out.println(this.value);
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
