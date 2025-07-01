package com.example.crud.company;

import java.util.HashSet;
import java.util.Set;

import com.example.crud.job.Job;
import com.example.crud.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "company")
  private Set<User> admins = new HashSet<>();

  @OneToMany(mappedBy = "company")
  private Set<Job> jobs = new HashSet<>();

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

  public Set<User> getAdmins() {
    return admins;
  }

  public void setAdmins(Set<User> admins) {
    this.admins = admins;
  }

  public Set<Job> getJobs() {
    return jobs;
  }

  public void setJobs(Set<Job> jobs) {
    this.jobs = jobs;
  }

}
