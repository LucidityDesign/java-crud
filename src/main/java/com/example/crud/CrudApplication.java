package com.example.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCaching
@RequestMapping("/")
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@GetMapping("/api")
	public String home() {
		return "Welcome to the CRUD application!";
	}

	@GetMapping("/health")
	public String health() {
		return "Application is running!";
	}

	// @GetMapping("/admin/jobs")
	// @PreAuthorize("hasRole('ADMIN')")
	// public String getAdminJobs() {
	// return "admin";
	// }

	// @GetMapping("/user/jobs")
	// @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	// public String getAdminJob() {
	// return "hans wurst";
	// }

}
