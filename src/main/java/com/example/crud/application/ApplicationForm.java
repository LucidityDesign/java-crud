package com.example.crud.application;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public class ApplicationForm {

  @NotNull
  @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
  private String name;

  @NotNull
  @Email(message = "Email should be valid")
  private String email;

  @NotNull
  @Size(min = 10, max = 10_0000, message = "Cover letter must be between 10 and 10.0000 characters")
  private String coverLetter;

  @NotNull
  // TODO: validate file
  private MultipartFile resume;
  @NotNull
  @Min(value = 1, message = "Job ID must be a positive number")
  private Long jobId;

  // Getters and Setters

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;

  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCoverLetter() {
    return coverLetter;
  }

  public void setCoverLetter(String coverLetter) {
    this.coverLetter = coverLetter;
  }

  public MultipartFile getResume() {
    return resume;
  }

  public void setResume(MultipartFile resume) {
    this.resume = resume;
  }

  public Long getJobId() {
    return jobId;
  }

  public void setJobId(Long jobId) {
    this.jobId = jobId;
  }
}
