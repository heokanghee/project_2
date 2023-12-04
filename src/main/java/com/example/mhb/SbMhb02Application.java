package com.example.mhb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.mhb"}) 
public class SbMhb02Application {

	public static void main(String[] args) {
		SpringApplication.run(SbMhb02Application.class, args);
	}

}
