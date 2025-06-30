package com.example.crud.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

  @Autowired
  private JobRepository jobRepository;

  public List<Job> getJobs() {
    return jobRepository.findAll();
  }

  public Job getJobById(String id) {
    return jobRepository.findById(Long.parseLong(id))
        .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
  }

  public Job addJob(Job job) {
    return jobRepository.save(job);
  }

}
