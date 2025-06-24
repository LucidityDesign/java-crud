package com.example.crud.softwareEngineer;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SoftwareEngineerService {

  private final SoftwareEngineerRepository softwareEngineerRepository;

  public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
    this.softwareEngineerRepository = softwareEngineerRepository;
  }

  public List<SoftwareEngineer> getAllSoftwareEngineers(Integer page) {
    int pageNumber = (page != null) ? page : 0;
    return softwareEngineerRepository.findAll(PageRequest.of(pageNumber, 10, Sort.by("id").descending())).getContent();
  }

  public SoftwareEngineer getSoftwareEngineerById(Integer id) {
    return softwareEngineerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Software Engineer not found with id: " + id));
  }

  public SoftwareEngineer createSoftwareEngineer(SoftwareEngineer engineer) {
    return softwareEngineerRepository.save(engineer);
  }

  public List<SoftwareEngineer> getByLevel(String string) {
    return softwareEngineerRepository.findByLevel(string);

  }

  public List<SoftwareEngineer> getWithHighId() {
    return softwareEngineerRepository.findByIdGreaterThan(5);
  }

}
