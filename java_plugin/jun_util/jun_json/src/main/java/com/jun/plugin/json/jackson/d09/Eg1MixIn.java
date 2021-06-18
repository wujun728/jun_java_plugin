package com.jun.plugin.json.jackson.d09;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Annotations are a great way to manage serialization and deserialization in
 * Jackson. However, what do you do if you want to annotate a third party class,
 * or if you dont want to tightly couple your POJOs to jackson annotations. This
 * is where Mix-in comes into play. You define a mix-in abstract class that is
 * kind of a proxy to the actual class. Annotations are then definied over this
 * proxy class
 */
class Eg1MixIn {
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(Bird.class, BirdMixIn.class);

		Bird bird = new Bird("scarlet Ibis");
		bird.setSound("eee");
		bird.setHabitat("water");

		String jsonString1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bird);
		System.out.println(jsonString1);

		String jsonString2 = "{\"b-name\":\"b-bird\",\"b-sound\":\"yaya\",\"b-habitat\":\"food\"}";

		Bird bird2 = mapper.readValue(jsonString2, Bird.class);
		System.out.println(bird2);
	}
}