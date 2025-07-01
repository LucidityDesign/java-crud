package com.example.crud.application;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

  // Custom query methods can be defined here if needed
  // For example, to find applications by job ID or applicant email
  List<Application> findByJobId(Long jobId);

  List<Application> findByApplicantEmail(String email);

  List<Application> findByApplicantId(Long applicantId);

}
