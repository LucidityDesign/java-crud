package com.example.crud.job;

public class JobMapper {
  public static JobDto mapToDto(Job job) {
    if (job == null) {
      return null;
    }

    JobDto dto = new JobDto();
    dto.setId(job.getId());
    dto.setName(job.getName());
    dto.setDescription(job.getDescription());
    dto.setLevel(job.getLevel());
    dto.setLocation(job.getLocation());
    dto.setCompanyId(job.getCompany().getId());
    dto.setAuthorId(job.getAuthor().getId());
    dto.setJobLocation(job.getJobLocation());
    dto.setStatus(job.getStatus());
    dto.setType(job.getType());

    return dto;
  }
}
