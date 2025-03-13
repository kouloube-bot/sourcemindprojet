package com.publi.gestionpub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.publi.gestionpub")
public class GestionpubBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionpubBackendApplication.class, args);
	}

}
