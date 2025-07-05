package com.example.crud.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class JobService {

  @Autowired
  private JobRepository jobRepository;

  public List<Job> getJobs() {
    return jobRepository.findAll();
  }

  public Job getJobById(Long id) {
    return jobRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Job with id " + id + " not found"));
  }

  public Job addJob(Job job) {
    return jobRepository.save(job);
  }

  public Job getReferenceById(Long id) {
    return jobRepository.getReferenceById(id);
  }

}
