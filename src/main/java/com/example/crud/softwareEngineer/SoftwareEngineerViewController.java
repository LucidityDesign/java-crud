package com.example.crud.softwareEngineer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/software-engineer")
public class SoftwareEngineerViewController {

  @GetMapping("/add")
  public String getAddSoftwareEngineerForm(SoftwareEngineerForm softwareEngineerForm) {
    return "softwareEngineer/form";
  }

}
