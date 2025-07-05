package com.example.crud.admin;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @GetMapping
  public String renderAdminPage(Model model, Authentication user) {
    model.addAttribute("user", user);
    return "admin/index";
  }

}
