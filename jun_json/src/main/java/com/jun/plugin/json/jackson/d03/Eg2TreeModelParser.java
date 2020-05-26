package com.jun.plugin.json.jackson.d03;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Lets look at another example that shows the use of the path method. The path
 * method is similar to the get() method but if the node does not exist, it does
 * not return null but returns an object of type MissingNode
 */
class Eg2TreeModelParser {
	public static void main(String[] args) throws MalformedURLException, IOException {
		String genreJson = "{\"title\":\"Free Music Archive - Albums\",\"dataset\":[{\"album_title\":\"A.B.A.Y.A.M\"},{\"album_title\":\"Support\"}]}";

		// create an ObjectMapper instance.
		ObjectMapper mapper = new ObjectMapper();
		// use the ObjectMapper to read the json string and create a tree
		JsonNode node = mapper.readTree(genreJson);

		// not the use of path. this does not cause the code to break if the code does
		// not exist
		Iterator<JsonNode> albums = node.path("dataset").iterator();
		while (albums.hasNext()) {
			System.out.println(albums.next().path("album_title"));
		}
	}
}