package com.jun.plugin.json.jackson.d05;

import java.io.IOException;
import java.net.MalformedURLException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson provides an efficient way to bind JSON to POJOs. However, at times,
 * certain properties may need to be ignored while converting a JSON to Java
 * Object or Java Object to JSON.
 * 
 * Jackson provides three ways to Ignore fields in JSON:
 * 1. @JsonIgnoreProperties– This annotation can be used at the type level to
 * ignore json properties. 2. @JsonIgnore – This annotation can be set at the
 * property level to ignore certain properties. 3. Using Custom filters
 * 
 * The example below shows method 1 and 2.
 * 
 * Also, note the use of the @JsonAutoDetect annotation.
 */
class Eg1DataBindingFilter {
	public static void main(String[] args)
			throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		String genreJson = "{\"album_title\":\"Support\",\"tag\":\"Fashion\",\"album_comments\":\"__comments__\",\"album_listens\":\"--album_listens--\",\"album_images\":\"--album_images--\"}";
		DynamicBean dynamicBean = mapper.readValue(genreJson, DynamicBean.class);
		System.out.println(dynamicBean.getAlbum_title());
		System.out.println(dynamicBean.getAlbum_comments());
		System.out.println(dynamicBean.any());

		mapper.writerWithDefaultPrettyPrinter().writeValue(System.out, dynamicBean);
	}
}