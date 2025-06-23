package com.example.crud.softwareEngineer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SoftwareEngineerForm {

  @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
  @NotNull(message = "Name cannot be null")
  private String name;

  @Size(min = 2, max = 30, message = "Role must be between 2 and 30 characters")
  private String role;

  @Size(min = 2, max = 30, message = "Level must be between 2 and 30 characters")
  private String level;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;

  }
}
