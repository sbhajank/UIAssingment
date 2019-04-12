package com.afkl.exercises.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SimpleTravelApiBootStrap {

	public static void main(String[] args) {
		SpringApplication.run(SimpleTravelApiBootStrap.class, args);
	}


}

