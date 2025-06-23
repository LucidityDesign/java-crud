package com.example.crud.frontpage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.softwareEngineer.SoftwareEngineer;
import com.example.crud.softwareEngineer.SoftwareEngineerService;
import com.example.crud.user.User;
import com.example.crud.user.UserService;

@Service
public class FrontpageFacade {

  @Autowired
  private UserService userService;

  @Autowired
  private SoftwareEngineerService softwareEngineerService;

  public FrontpageData getFrontpageData() {
    Integer page = 0;

    List<User> users = userService.getAllUsers(page);
    List<SoftwareEngineer> softwareEngineers = softwareEngineerService.getAllSoftwareEngineers(page);
    FrontpageData data = new FrontpageData(users, softwareEngineers);

    return data;
  }
}
