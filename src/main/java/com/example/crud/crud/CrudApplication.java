package com.example.crud.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
		return "Welcome to the CRUD application!";
	}

	@GetMapping("/health")
	public String health() {
		return "Application is running!";
	}

}
