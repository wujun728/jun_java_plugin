package com.jun.plugin.json.jackson.d08;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The example converts a Zoo class to JSON. the zoo class contains the name of
 * zoo, its city and a list of animals. The list is of type ‘Animal’, i.e. the
 * list contains elements that are subclass of the Abstract class Animal.s
 */
class Eg1ObjectList {
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		Lion lion = new Lion("Simba");
		Elephant elephant = new Elephant("Manny");
		Zoo zoo = new Zoo("London Zoo", "London");
		zoo.addAnimal(elephant).add(lion);

		ObjectMapper mapper = new ObjectMapper();

		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(zoo);
		System.out.println(jsonString);

		mapper.readValue(jsonString, Zoo.class);
	}
}
