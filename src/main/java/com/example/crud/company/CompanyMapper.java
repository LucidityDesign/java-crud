package com.example.crud.company;

import java.util.stream.Collectors;

import com.example.crud.job.JobMapper;

public class CompanyMapper {
  public static CompanyDto mapToDto(Company company) {
    CompanyDto dto = new CompanyDto();
    dto.setId(company.getId());
    dto.setName(company.getName());
    dto.setAdminUserNames(
        company.getAdmins()
            .stream()
            .map(user -> user.getEmail())
            .collect(Collectors.toList()));

    dto.setJobs(
        company.getJobs()
            .stream()
            .map(job -> JobMapper.mapToDto(job))
            .collect(Collectors.toList()));

    return dto;
  }

  // Optional reverse mapping if needed
  public static Company mapToEntity(CompanyDto dto) {
    Company company = new Company();
    company.setId(dto.getId());
    company.setName(dto.getName());
    // Skipping admins because this would require loading Users
    return company;
  }
}
