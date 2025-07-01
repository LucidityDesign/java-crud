package com.example.crud.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.crud.company.Company;
import com.example.crud.user.User;
import com.example.crud.user.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/api/v1/job")
public class JobApiController {

  @Autowired
  private JobService jobService;
  @Autowired
  private UserService userService;

  @GetMapping("/")
  public String getJobs(Model model) {
    List<Job> jobs = jobService.getJobs();
    model.addAttribute("jobs", jobs);
    return "job/list"; // Placeholder view name
  }

  @GetMapping("/{id}")
  public String getCompanyById(@PathVariable Long id, Model model) {
    model.addAttribute("job", jobService.getJobById(id));
    return "job/view"; // Placeholder view name
  }

  @PostMapping("/")
  public ResponseEntity<JobDto> addJob(@RequestBody Job job, JwtAuthenticationToken token) {
    // TODO validate job object

    String oid = token.getToken().getClaimAsString("oid");
    User me = userService.getUserByOid(oid).orElseThrow();
    Company company = me.getCompany();

    job.setAuthor(me);
    job.setCompany(company);

    me.setJobs(List.of(job));

    Job createdJob = jobService.addJob(job);
    JobDto createdJobDto = JobMapper.mapToDto(createdJob);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdJobDto);
  }
}
