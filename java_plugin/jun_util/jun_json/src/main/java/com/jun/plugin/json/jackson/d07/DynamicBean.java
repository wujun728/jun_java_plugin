package com.jun.plugin.json.jackson.d07;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @JsonProperty This annotation is used to mark a method as a getter or setter
 *               for a property. In other words, it associates a json field with
 *               a java property. If a name is specified (@JsonProperty(“age”))
 *               then the java property that is annotated with this annotation
 *               is mapped to the ‘age’ field of the json, If no name is
 *               specified the java property name is used.
 * 
 * @JsonCreator This annotation is used the define constructors that are used to
 *              create java objects from json string. It is used during data
 *              binding and specifies properties that will be used to create
 *              java objects during deserialization.
 * 
 * @JsonAnyGetter and @JsonAnySetter This annotations are used to mark methods
 *                that set or read fields that are not handled by any other java
 *                property. These act like catch-all and handle all fields that
 *                are not handled by any other java property. The fields are
 *                stored in a map as key value pairs.
 */
class DynamicBean {
	private String albumTitle;
	private Map<String, Object> otherProperties = new HashMap<String, Object>();

	@JsonCreator
	public DynamicBean(@JsonProperty("album_title") String albumTitle) {
		this.albumTitle = albumTitle;
	}

	@JsonProperty("album_title")
	public String getAlbumTitle() {
		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	public Object get(String name) {
		return otherProperties.get(name);
	}

	@JsonAnyGetter
	public Map<String, Object> any() {
		return otherProperties;
	}

	@JsonAnySetter
	public void set(String name, Object value) {
		otherProperties.put(name, value);
	}
}