package com.jun.plugin.json.jackson.d08;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * In this example we try and serialize the List directly.
 */
class Eg2ListDirectly {
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		Lion lion = new Lion("Samba");
		Elephant elephant = new Elephant("Manny");
		List<Animal> animals = new ArrayList<Animal>();
		animals.add(lion);
		animals.add(elephant);

		ObjectMapper mapper = new ObjectMapper();

		String jsonString1 = mapper.writeValueAsString(animals);
		System.out.println(jsonString1);

		String jsonString2 = mapper.writerFor(new TypeReference<List<Animal>>() {
		}).writeValueAsString(animals);
		System.out.println(jsonString2);
	}
}
