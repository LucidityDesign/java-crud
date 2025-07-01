package com.example.crud.frontpage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.job.Job;
import com.example.crud.job.JobService;
import com.example.crud.user.User;
import com.example.crud.user.UserService;

@Service
public class FrontpageFacade {

  @Autowired
  private UserService userService;

  @Autowired
  private JobService jobService;

  public FrontpageData getFrontpageData() {
    Integer page = 0;

    List<User> users = userService.getAllUsers(page);
    List<Job> jobs = jobService.getJobs();
    FrontpageData data = new FrontpageData(users, jobs);

    return data;
  }
}
