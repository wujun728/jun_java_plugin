package com.jun.plugin.json.jackson.d01;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

/**
 * Jackson provides classes that can be used to convert a Java Object to JSON
 * and back. In this example, we look at how to convert Java to JSON and JSON to
 * Java.
 */
class Eg1Serialization {
	public static void main(String[] args)
			throws ParseException, JsonGenerationException, JsonMappingException, IOException {
		List<String> songs = new ArrayList<>(3);
		songs.add("So What");
		songs.add("Flamenco Sketches");
		songs.add("Freddie Freeloader");

		Artist artist = new Artist();
		artist.name = "Miles Davis";
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		artist.birthDate = format.parse("26-05-1926");

		Album album = new Album("Kind Of Blue");
		album.setLinks(new String[] { "link1", "link2" });
		album.setArtist(artist);
		album.setSongs(songs);
		album.addMusician("Miles Davis", "Trumpet, Band leader");
		album.addMusician("Julian Adderley", "Alto Saxophone");
		album.addMusician("Paul Chambers", "double bass");

		/*
		 * Java to JSON and JSON to Java using ObjectMapper
		 * 
		 * we use the ObjectMapper to convert the Object to JSON. By Default Jackson
		 * would use BeanSerializer to serialize the POJO. Note that the bean should
		 * have getters for private properties or the property should be public
		 */
		ObjectMapper mapper = new ObjectMapper();

		/*
		 * To make JSON visually more readable we add this line. Note that this should
		 * not be done for production systems, but only during testing or development
		 * since this would increase the size of the json.
		 */
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		/*
		 * tell the mapper to arrange the Map by keys using it natural order.
		 */
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

		/*
		 * The date has been formatted as the epoch time. Lets format it into a more
		 * human readable format.
		 */
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
		mapper.setDateFormat(outputFormat);

		/*
		 * By default, jackson uses the name of the java field as the name of the json
		 * property. You can change that by using annotations. However at times you may
		 * not have access to the java bean or you do not want to bind the java bean to
		 * a Jackson annotation. In this case jackson provides a way to change the
		 * default field name. Use the setPropertyNamingStrategy method on the mapper to
		 * set the naming strategy for the field. You need to override either the the
		 * nameForField method or the nameForGetterMethod depending on whether you have
		 * public field in the bean or a getter in the bean. In our example lets change
		 * the ‘title’ of the album to ‘Album-Title’ and the ‘name’ of the artist to
		 * ‘Artist-Name’.
		 */
		mapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {
			private static final long serialVersionUID = 1L;

			@Override
			public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
				if (field.getFullName().equals("com.company.jackson.Artist#name"))
					return "Artist-Name";
				return super.nameForField(config, field, defaultName);
			}

			@Override
			public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
				if (method.getAnnotated().getDeclaringClass().equals(Album.class) && defaultName.equals("title"))
					return "Album-Title";
				return super.nameForGetterMethod(config, method, defaultName);
			}
		});

		/* We use this configuration to ignore properties that are empty. */
		mapper.setSerializationInclusion(Include.NON_EMPTY);

		mapper.writeValue(System.out, album);
	}
}