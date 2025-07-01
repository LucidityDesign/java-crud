package com.example.crud.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

  @Autowired
  private CompanyRepository companyRepository;

  public List<Company> getCompanies() {
    return companyRepository.findAll();
  }

  public Company getCompanyById(String id) {
    return companyRepository.findById(Long.parseLong(id)).orElse(null);
  }

  public Company addCompany(Company company) {
    return companyRepository.save(company);
  }

  public void deleteCompany(Company company) {
    companyRepository.delete(company);
  }

}
