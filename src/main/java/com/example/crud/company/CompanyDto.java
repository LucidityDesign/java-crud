package com.example.crud.company;

import java.util.List;

import com.example.crud.job.JobDto;

public class CompanyDto {
  private Long id;
  private String name;
  private List<String> adminUserNames;
  private List<JobDto> jobs;

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

  public List<String> getAdminUserNames() {
    return adminUserNames;
  }

  public void setAdminUserNames(List<String> adminUserNames) {
    this.adminUserNames = adminUserNames;
  }

  public List<JobDto> getJobs() {
    return jobs;
  }

  public void setJobs(List<JobDto> jobs) {
    this.jobs = jobs;
  }

}
