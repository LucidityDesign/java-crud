package com.example.crud.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.crud.application.ApplicationForm;

@Controller
@RequestMapping("/job")
public class JobViewController implements WebMvcConfigurer {

  @Autowired
  private JobService jobService;

  @GetMapping("/{id}/apply")
  public String applyForJob(ApplicationForm personForm, Model model, Authentication user,
      @PathVariable("id") Long id) {
    // Extract job ID from the URL path variable
    model.addAttribute("user", user);
    model.addAttribute("job", jobService.getJobById(id));
    return "job/apply"; // Placeholder view name for job application
  }
}
