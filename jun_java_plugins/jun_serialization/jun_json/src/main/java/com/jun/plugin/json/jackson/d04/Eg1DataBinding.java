package com.jun.plugin.json.jackson.d04;

import java.io.IOException;
import java.net.MalformedURLException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * What is JSON Java Data Binding? A thing that most java developers love to
 * deal with is …. Java POJO. Wouldn’t you love a black box where you can see
 * JSON string entering from one side and POJOs coming out from the other.
 * That’s what Jackson’s JSON java data binding does.
 */
class Eg1DataBinding {
	public static void main(String[] args)
			throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		String jsonString = "{\"title\":\"Free Music Archive - Albums\",\"propertyA\":\"nothing\",\"dataset\":[{\"album_title\":\"A.B.A.Y.A.M\"},{\"album_title\":\"Support\"}]}";

		/*
		 * The ObjectMapper caches serializers and deserializers so it would be a good
		 * idea to reuse an ObjectMapper instance for multiple conversions.
		 * 
		 * If you have an InputStream then pass it to Jackson as such and do not wrap it
		 * in InputStreamReader for performance reasons.
		 */
		ObjectMapper mapper = new ObjectMapper();

		/*
		 * We have disabled the feature that causes the mapper to break if it encounters
		 * an unknown property. Therefore, if the json has 10 properties and you define
		 * only two in your bean, then the other 8 will be ignored.
		 */
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		/*
		 * We use the readValue method of the ObjectMapper to read. There are multiple
		 * versions of this method and we use the method that takes in a String.
		 * However, there are method that read from a file, inputstream, URL or a
		 * ByteArray.
		 */
		Album albums = mapper.readValue(jsonString, Album.class);
		
		Dataset[] datasets = albums.getDataset();
		for (Dataset dataset : datasets) {
			System.out.println(dataset.getAlbum_title());
		}
	}
}