package org.drools.range;

public class Item {

	private String name;
	private int weight;
	private int price;
	private ItemCode code;
	
	private String output;

	public Item(String name, int weight, int price, ItemCode code) {
		this.name = name;
		this.weight = weight;
		this.price = price;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ItemCode getCode() {
		return code;
	}

	public void setCode(ItemCode code) {
		this.code = code;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
}
