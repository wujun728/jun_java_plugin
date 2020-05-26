package com.jun.plugin.json.jackson.d06;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = As.PROPERTY, property = "@class")
class Zoo {
	public String name;
	public String city;
	public List<Animal> animals;

	@JsonCreator
	public Zoo(@JsonProperty("name") String name, @JsonProperty("city") String city) {
		this.name = name;
		this.city = city;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	@Override
	public String toString() {
		return "Zoo [name=" + name + ", city=" + city + ", animals=" + animals + "]";
	}
}

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "@class")
abstract class Animal {
	@JsonProperty("name")
	String name;
	@JsonProperty("sound")
	String sound;
	@JsonProperty("type")
	String type;
	@JsonProperty("endangered")
	boolean endangered;
}

class Elephant extends Animal {
	private String name;

	@JsonCreator
	public Elephant(@JsonProperty("name") String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getSound() {
		return "trumpet";
	}

	public String getType() {
		return "herbivorous";
	}

	public boolean isEndangered() {
		return false;
	}

	@Override
	public String toString() {
		return "Elephant [name=" + name + ", getName()=" + getName() + ", getSound()=" + getSound() + ", getType()="
				+ getType() + ", isEndangered()=" + isEndangered() + "]";
	}
}

class Lion extends Animal {
	private String name;

	@JsonCreator
	public Lion(@JsonProperty("name") String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getSound() {
		return "Roar";
	}

	public String getType() {
		return "carnivorous";
	}

	public boolean isEndangered() {
		return true;
	}

	@Override
	public String toString() {
		return "Lion [name=" + name + ", getName()=" + getName() + ", getSound()=" + getSound() + ", getType()="
				+ getType() + ", isEndangered()=" + isEndangered() + "]";
	}
}