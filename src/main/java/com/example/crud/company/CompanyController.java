package com.example.crud.company;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.crud.user.User;
import com.example.crud.user.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/api/v1/company")
public class CompanyController {

  @Autowired
  private CompanyService companyService;
  @Autowired
  private UserService userService;

  @GetMapping("/{id}")
  public String getCompanyById(@PathVariable String id, Model model) {
    model.addAttribute("company", companyService.getCompanyById(id));
    return "company/view"; // Placeholder view name
  }

  @ResponseBody
  @GetMapping("/")
  public List<Company> getCompanies() {
    return companyService.getCompanies();
  }

  @PostMapping("/")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CompanyDto> addCompany(@RequestBody Company company, JwtAuthenticationToken token) {
    // TODO validate company object

    String oid = token.getToken().getClaimAsString("oid");
    User me = userService.getUserByOid(oid).orElseThrow();

    Set<User> admins = new HashSet<>();
    admins.add(me);
    company.setAdmins(admins);
    me.setCompany(company);

    Company createdCompany = companyService.addCompany(company);
    CompanyDto createdCompanyDto = CompanyMapper.mapToDto(createdCompany);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdCompanyDto);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteCompany(@PathVariable String id, JwtAuthenticationToken token) {
    String oid = token.getToken().getClaimAsString("oid");
    User me = userService.getUserByOid(oid).orElseThrow();

    if (id == null || !id.equals(me.getCompany().getId().toString())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    companyService.deleteCompany(me.getCompany());
    return ResponseEntity.noContent().build();
  }

}
