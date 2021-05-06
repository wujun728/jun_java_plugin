package com.jun.plugin.json.jackson.d01;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * It is also possible to build a json using a simple tree model. This could be
 * useful if you dont want to write classes for your JSON structure.
 */
class Eg2SerializationTree {
	public static void main(String[] args) throws IOException {
		/*
		 * Create a JsonNodeFactory to create the nodes.
		 */
		JsonNodeFactory jsonNodeFactory = new JsonNodeFactory(false);

		ObjectNode album = jsonNodeFactory.objectNode();
		album.put("Album-Title", "Kind Of Blue");
		ArrayNode links = jsonNodeFactory.arrayNode();
		links.add("link1").add("link2");
		album.set("links", links);
		ObjectNode artist = jsonNodeFactory.objectNode();
		artist.put("Artist-Name", "Miles Davis");
		artist.put("birthDate", "26 May 1926");
		album.set("artist", artist);
		ObjectNode musicians = jsonNodeFactory.objectNode();
		musicians.put("Julian Adderley", "Alto Saxophone");
		musicians.put("Miles Davis", "Trumpet, Band leader");
		album.set("musicians", musicians);

		/*
		 * Create a JsonGenerator from a JsonFactory and specify the output method.
		 */
		JsonFactory jsonFactory = new JsonFactory();
		JsonGenerator jsonGenerator = jsonFactory.createGenerator(System.out);

		/*
		 * Create an ObjectMapper that will use the jsonGenerator and the root node to
		 * create the JSON.
		 */
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeTree(jsonGenerator, album);
	}
}