package com.jun.plugin.json.jackson.d05;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//ignore the property with name 'tags'.
@JsonIgnoreProperties({ "tag" })
class DynamicBean {
	private String album_title;
	private String album_comments;
	private Map<String, Object> otherProperties = new HashMap<String, Object>();

	@JsonCreator
	public DynamicBean(@JsonProperty("album_title") String album_title) {
		this.album_title = album_title;
	}

	public String getAlbum_title() {
		return album_title;
	}

	public void setAlbum_title(String album_title) {
		this.album_title = album_title;
	}

	// ignore the property specified by this getter.
	@JsonIgnore
	public String getAlbum_comments() {
		return album_comments;
	}

	public Object get(String name) {
		return otherProperties.get(name);
	}

	@JsonAnySetter
	public void set(String name, Object value) {
		otherProperties.put(name, value);
	}

	// this method is used to get all properties not specified earlier.
	@JsonAnyGetter
	public Map<String, Object> any() {
		return otherProperties;
	}
}
