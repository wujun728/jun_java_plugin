package com.jun.plugin.json.jackson.d02;

import java.io.IOException;
import java.net.MalformedURLException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * Jackson provides a low-level API to parse JSON to Java. The API provides
 * token for each JSON object. For example, the start of JSON ‘{‘ is the first
 * object that the parser provides. The key value pair is another single object.
 * The client code can use the tokens and get the JSON properties or build a
 * java object out of it if required. This low-level API is extremely powerful
 * but needs a lot of coding. For most cases, Jackson’s tree traversing and data
 * binding capability should be explored instead.
 */
class Eg1StreamParser {
	public static void main(String[] args) throws MalformedURLException, IOException {
		// get an instance of the json parser from the json factory
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createParser(
				"{\"title\":\"Free Music Archive - Albums\",\"dataset\":[{\"album_title\":\"A.B.A.Y.A.M\"},{\"album_title\":\"Support\"}]}");

		// continue parsing the token till the end of input is reached
		while (!parser.isClosed()) {
			// get the token
			JsonToken token = parser.nextToken();
			// if its the last token then we are done
			if (token == null)
				break;
			// we want to look for a field that says dataset

			if (JsonToken.FIELD_NAME.equals(token) && "dataset".equals(parser.getCurrentName())) {
				// we are entering the datasets now. The first token should be
				// start of array
				token = parser.nextToken();
				if (!JsonToken.START_ARRAY.equals(token)) {
					// bail out
					break;
				}
				// each element of the array is an album so the next token
				// should be {
				token = parser.nextToken();
				if (!JsonToken.START_OBJECT.equals(token)) {
					break;
				}
				// we are now looking for a field that says "album_title". We
				// continue looking till we find all such fields. This is
				// probably not a best way to parse this json, but this will
				// suffice for this example.
				while (true) {
					token = parser.nextToken();
					if (token == null)
						break;
					if (JsonToken.FIELD_NAME.equals(token) && "album_title".equals(parser.getCurrentName())) {
						token = parser.nextToken();
						System.out.println(parser.getText());
					}

				}

			}

		}

	}
}