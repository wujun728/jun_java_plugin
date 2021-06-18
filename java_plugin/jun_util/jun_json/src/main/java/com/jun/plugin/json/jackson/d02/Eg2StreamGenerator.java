package com.jun.plugin.json.jackson.d02;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

/**
 * In This example we look at generating a json using the jsongenerator.
 */
class Eg2StreamGenerator {
	public static void main(String[] args) throws IOException {
		JsonFactory factory = new JsonFactory();
		JsonGenerator generator = factory.createGenerator(System.out);

		generator.writeStartObject();// start writing with {
		generator.writeFieldName("title");
		generator.writeString("Free Music Archive - Albums");
		generator.writeFieldName("dataset");
		generator.writeStartArray();// start an array
		generator.writeStartObject();
		generator.writeStringField("album_title", "A.B.A.Y.A.M");
		generator.writeEndObject();
		generator.writeStartObject();
		generator.writeStringField("album_title", "Support");
		generator.writeEndObject();
		generator.writeEndArray();
		generator.writeEndObject();

		generator.close();
	}
}