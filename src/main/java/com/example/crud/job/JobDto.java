package com.example.crud.job;

public class JobDto {

  private Long id;

  private String name;
  private String description;
  private String location;
  private JobType type;
  private JobStatus status;
  private JobLevel level;
  private JobLocation jobLocation;

  private Long companyId;

  private Long authorId;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public JobType getType() {
    return type;
  }

  public void setType(JobType type) {
    this.type = type;
  }

  public JobStatus getStatus() {
    return status;
  }

  public void setStatus(JobStatus status) {
    this.status = status;
  }

  public JobLevel getLevel() {
    return level;
  }

  public void setLevel(JobLevel level) {
    this.level = level;
  }

  public JobLocation getJobLocation() {
    return jobLocation;
  }

  public void setJobLocation(JobLocation jobLocation) {
    this.jobLocation = jobLocation;
  }

  public Long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Long companyId) {
    this.companyId = companyId;
  }

  public Long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }

}
