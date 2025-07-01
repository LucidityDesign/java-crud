package com.example.crud.user;

public class UserDto {
  private Long id;
  private String username;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;

  }
}
