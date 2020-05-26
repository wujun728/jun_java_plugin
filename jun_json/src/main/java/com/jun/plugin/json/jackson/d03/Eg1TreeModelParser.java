package com.jun.plugin.json.jackson.d03;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson provides a tree node called com.fasterxml.jackson.databind.JsonNode.
 * The ObjectMapper provides a method to convert JSON to Java tree model with
 * the root being a JsonNode.
 */
class Eg1TreeModelParser {
	public static void main(String[] args) throws MalformedURLException, IOException {
		String jsonString = "{\"title\":\"Free Music Archive - Albums\",\"dataset\":[{\"album_title\":\"A.B.A.Y.A.M\"},{\"album_title\":\"Support\"}]}";

		// create an ObjectMapper instance.
		ObjectMapper mapper = new ObjectMapper();
		// use the ObjectMapper to read the json string and create a tree
		JsonNode node = mapper.readTree(jsonString);

		// lets see what type the node is
		System.out.println(node.getNodeType()); // prints OBJECT
		// is it a container
		System.out.println(node.isContainerNode()); // prints true
		// lets find out what fields it has
		Iterator<String> fieldNames = node.fieldNames();
		while (fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			System.out.println(fieldName);// prints title, dataset
		}

		// we now know what elemets the container has. lets get the value for one of
		// them
		System.out.println(node.get("title").asText()); // prints "Free Music Archive - Albums".

		// Lets look at the dataset now.
		JsonNode dataset = node.get("dataset");

		// what is its type?
		System.out.println(dataset.getNodeType()); // Prints ARRAY

		// so the dataset is an array. Lets iterate through the array and see
		// what each of the elements are
		Iterator<JsonNode> datasetElements = dataset.iterator();
		while (datasetElements.hasNext()) {
			JsonNode datasetElement = datasetElements.next();
			// what is its type
			System.out.println(datasetElement.getNodeType());// Prints Object
			// it is again a container . what are the elements ?
			Iterator<String> datasetElementFields = datasetElement.fieldNames();
			while (datasetElementFields.hasNext()) {
				String datasetElementField = datasetElementFields.next();
				System.out.println(datasetElementField);
			}
			// break from the loop, since we just want to see the structure
			break;

		}

	}

}