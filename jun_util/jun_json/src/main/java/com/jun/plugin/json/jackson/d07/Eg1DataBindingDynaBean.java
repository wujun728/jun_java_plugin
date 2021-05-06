package com.jun.plugin.json.jackson.d07;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The json string at times have a lot of properties. It seems a waste creating
 * a POJO with all those properties. Wouldnt it be great if there was a
 * catch’all that could read all properties in a map? Jackson provides
 * annotations to do just that. In the example below we have set two properties
 * in the bean and the other properties are read into a map.
 */
class Eg1DataBindingDynaBean {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String genreJson = "{\"album_title\":\"Support\",\"tag\":\"Fashion\",\"album_comments\":\"__comments__\",\"album_listens\":\"--album_listens--\",\"album_images\":\"--album_images--\"}";
		ObjectMapper mapper = new ObjectMapper();
		DynamicBean dynamicBean = mapper.readValue(genreJson, DynamicBean.class);
		System.out.println(dynamicBean.getAlbumTitle());
		System.out.println(dynamicBean.any());
		mapper.writeValue(System.out, dynamicBean);
	}
}