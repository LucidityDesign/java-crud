package com.example.crud.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

  @Autowired
  private ApplicationRepository applicationRepository;

  public Application addApplication(Application application) {
    return applicationRepository.save(application);
  }

  public Application getApplicationById(Long id) {
    return applicationRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
  }

  public void deleteApplication(Long id) {
    applicationRepository.deleteById(id);
  }

  public List<Application> getAllApplications() {
    return applicationRepository.findAll();
  }

  public List<Application> getApplicationsByJobId(Long jobId) {
    return applicationRepository.findByJobId(jobId);
  }

  public List<Application> getApplicationsByApplicantEmail(String email) {
    return applicationRepository.findByApplicantEmail(email);
  }

  public List<Application> getApplicationsByApplicantId(Long applicantId) {
    return applicationRepository.findByApplicantId(applicantId);
  }

  public Application save(Application application) {
    return applicationRepository.save(application);
  }
}
