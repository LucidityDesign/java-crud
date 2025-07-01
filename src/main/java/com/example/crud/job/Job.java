package com.example.crud.job;

import com.example.crud.company.Company;
import com.example.crud.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

enum JobType {
  FULL_TIME, PART_TIME, CONTRACT
}

enum JobStatus {
  OPEN, CLOSED, PENDING
}

enum JobLevel {
  JUNIOR, MID_LEVEL, SENIOR
}

enum JobLocation {
  REMOTE, ONSITE, HYBRID
}

@Entity
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;
  @Column(nullable = false, length = 100_000)
  private String description;
  private String location = "Hamburg";
  private JobType type = JobType.FULL_TIME; // Default type
  private JobStatus status = JobStatus.OPEN; // Default status
  private JobLevel level = JobLevel.SENIOR; // Default level
  private JobLocation jobLocation = JobLocation.REMOTE; // Default location

  @ManyToOne
  private Company company;

  @ManyToOne
  private User author;

  // getters and setters
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

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

}
