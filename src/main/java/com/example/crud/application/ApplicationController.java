package com.example.crud.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.crud.AzureBlobService;
import com.example.crud.job.Job;
import com.example.crud.job.JobService;
import com.example.crud.user.User;
import com.example.crud.user.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class ApplicationController implements WebMvcConfigurer {

  @Autowired
  private JobService jobService;

  @Autowired
  private UserService userService;

  @Autowired
  private ApplicationService applicationService;

  @Autowired
  private AzureBlobService azureBlobService;

  // TODO don't mix job and application logic
  @GetMapping("/job/{id}/apply")
  public String applyForJob(ApplicationForm applicationForm, Model model, Authentication user,
      @PathVariable("id") Long id) {
    // Extract job ID from the URL path variable
    model.addAttribute("user", user);
    model.addAttribute("job", jobService.getJobById(id));

    return "job/apply"; // Placeholder view name for job application
  }

  @PostMapping("/job/{id}/apply")
  public String saveApplication(@Valid ApplicationForm applicationForm, BindingResult bindingResult, Model model,
      Authentication user, @PathVariable("id") Long id) {

    model.addAttribute("user", user);
    model.addAttribute("job", jobService.getJobById(id));

    if (bindingResult.hasErrors()) {
      return "job/apply";
    }

    try {
      String url = azureBlobService.uploadFile(applicationForm.getResume());

      Job job = jobService.getReferenceById(id);
      OAuth2User oauth2User = (OAuth2User) user.getPrincipal();
      User userEntry = userService.getReferenceByOid(oauth2User.getAttribute("oid"));
      Application application = new Application();
      application.setName(applicationForm.getName());
      application.setEmail(applicationForm.getEmail());
      application.setCoverLetter(applicationForm.getCoverLetter());
      application.setResumeUrl(url);
      application.setJob(job);
      application.setApplicant(userEntry);

      applicationService.save(application);
      System.out.println("Uploaded resume to: " + url);
    } catch (Exception e) {
      // TODO delete file and DB entry
      bindingResult.rejectValue("resume", "error.resume", "Failed to upload resume. Please try again.");
      return "job/apply";
    }

    // TODO: Redirect to a confirmation page or the job details page
    return "redirect:/";
  }

  @GetMapping("/api/v1/application")
  public String getApplications(JwtAuthenticationToken token, Model model) {
    String oid = token.getToken().getClaimAsString("oid");
    User userEntry = userService.getReferenceByOid(oid);

    model.addAttribute("applications", applicationService.getApplicationsByApplicantId(userEntry.getId()));
    return "application/list";
  }

}
