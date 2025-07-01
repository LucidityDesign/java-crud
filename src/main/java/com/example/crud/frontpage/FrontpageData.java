package com.example.crud.frontpage;

import java.util.List;

import com.example.crud.job.Job;
import com.example.crud.user.User;

public record FrontpageData(List<User> users, List<Job> jobs) {
}
