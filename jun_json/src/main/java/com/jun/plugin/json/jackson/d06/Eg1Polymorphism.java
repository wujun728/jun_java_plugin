package com.jun.plugin.json.jackson.d06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson provides a way to maintain sub type information while serializing
 * java objects. It is possible to recreate the exact sub type. The type
 * information can be embedded into the json as a property.
 * 
 * In the example below we create a zoo, that has a list of animals. The animal
 * may be an elephant or a lion, and they both extend the Animal abstract class.
 * 
 * While deserializing we want to create the exact animal type.
 * 
 * We also demonstrate the use of @JsonTypeInfo and @JsonSubTypes annotations.
 */
class Eg1Polymorphism {
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		Lion lion = new Lion("Simba");
		Elephant elephant = new Elephant("Manny");
		List<Animal> animals = new ArrayList<>();
		animals.add(lion);
		animals.add(elephant);
		Zoo zoo = new Zoo("Samba Wild Park", "Paz");
		zoo.setAnimals(animals);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(zoo);
		System.out.println(jsonString);

		Zoo zoo2 = mapper.readValue(jsonString, Zoo.class);
		System.out.println(zoo2);
	}
}