package com.sam.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStart {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebStart.class);
        //SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        // Check if the selected profile has been set as argument.
        // if not the development profile will be added
        //app.setAdditionalProfiles(getDefaultProfile(source));
        app.run(args);
	}
}
