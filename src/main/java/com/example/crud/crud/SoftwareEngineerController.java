package com.example.crud.crud;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/api/v1/software-engineers")
public class SoftwareEngineerController {

  // This class will handle HTTP requests related to SoftwareEngineer entities
  // It will include methods for creating, reading, updating, and deleting
  // software engineers

  public final SoftwareEngineerService softwareEngineerService;

  public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
    this.softwareEngineerService = softwareEngineerService;
  }

  @GetMapping("/")
  public String getAllSoftwareEngineers(
      @AuthenticationPrincipal OAuth2User oauth2User, Model model, Integer page) {
    // Logic to retrieve all software engineers from the database
    // return softwareEngineerService.getAllSoftwareEngineers(page);
    model.addAttribute("softwareEngineers", softwareEngineerService.getAllSoftwareEngineers(page));
    return "softwareEngineer/list";
  }

  @GetMapping("/highId")
  @ResponseBody
  public List<SoftwareEngineer> getSoftwareEngineersWithHighId() {
    // Logic to retrieve a software engineer by ID from the database
    return softwareEngineerService.getWithHighId();
  }

  @PostMapping("/")
  public ResponseEntity<SoftwareEngineer> createSoftwareEngineer(@RequestBody SoftwareEngineer engineer) {
    // Logic to save the software engineer to the database

    System.out.println(engineer);
    SoftwareEngineer savedEngineer = softwareEngineerService.createSoftwareEngineer(engineer);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedEngineer);
  }

  @GetMapping("/secure")
  @ResponseBody
  public String getSecuredData(Authentication user) {
    if (user != null) {
      // Assuming the user is authenticated, return a message with the user's name
      return "Hello " + user.getName();
    }

    // Return status code 401 Unauthorized if the user is not authenticated
    return "Unauthorized";
  }

  // Example method to create a new software engineer
  // public ResponseEntity<SoftwareEngineer> createSoftwareEngineer(@RequestBody
  // SoftwareEngineer engineer) {
  // // Logic to save the engineer to the database
  // return ResponseEntity.status(HttpStatus.CREATED).body(savedEngineer);
  // }

  // Additional methods for reading, updating, and deleting software engineers
  // will go here
}
