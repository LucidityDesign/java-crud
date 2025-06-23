package com.example.crud.crud;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SoftwareEngineerService {

  private final SoftwareEngineerRepository softwareEngineerRepository;

  public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
    this.softwareEngineerRepository = softwareEngineerRepository;
  }

  public List<SoftwareEngineer> getAllSoftwareEngineers(Integer page) {
    int pageNumber = (page != null) ? page : 0;
    return softwareEngineerRepository.findAll(Pageable.ofSize(10).withPage(pageNumber)).getContent();
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
