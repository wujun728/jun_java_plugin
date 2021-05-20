package com.jun.plugin.json.jackson.d09;

import com.fasterxml.jackson.annotation.JsonProperty;

abstract class BirdMixIn {
	BirdMixIn(@JsonProperty("b-name") String name) {
	};

	@JsonProperty("b-name")
	abstract String getName();

	@JsonProperty("b-sound")
	abstract String getSound();

	@JsonProperty("b-habitat")
	abstract String getHabitat();
}