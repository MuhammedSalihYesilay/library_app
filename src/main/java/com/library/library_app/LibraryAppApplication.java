package com.library.library_app;

import com.library.library_app.config.libraryConfig.LibraryProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(LibraryProperties.class) // "@ConfigurationProperties(prefix = "library")  public class LibraryProperties" bu buranın çalışması için kullanılıyor
public class LibraryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryAppApplication.class, args);
	}
}
