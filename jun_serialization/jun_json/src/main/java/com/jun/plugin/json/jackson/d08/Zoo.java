package com.jun.plugin.json.jackson.d08;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

class Zoo {
	public String name;
	public String city;
	public List<Animal> animals = new ArrayList<Animal>();

	/*
	 * When we try to get the Zoo Object back from the JSON, Jackson has to know
	 * that it should create the Zoo Object using the constructor that takes in the
	 * name and city properties.
	 */
	@JsonCreator
	public Zoo(@JsonProperty("name") String name, @JsonProperty("city") String city) {
		this.name = name;
		this.city = city;
	}

	public List<Animal> addAnimal(Animal animal) {
		animals.add(animal);
		return animals;
	}
}

//Tell Jackson that we need to include the type info for Animal class.
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "@class")
abstract class Animal {
	@JsonProperty("name")
	String name;
}

class Elephant extends Animal {
	@JsonCreator
	public Elephant(@JsonProperty("name") String name) {
		super.name = name;
	}
}

class Lion extends Animal {
	@JsonCreator
	public Lion(@JsonProperty("name") String name) {
		this.name = name;
	}
}
