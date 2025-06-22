package com.example.crud.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public void syncUser(OAuth2User oauthUser) {
    String oid = oauthUser.getAttribute("oid");
    String name = oauthUser.getAttribute("name");

    userRepository.findByOid(oid).orElseGet(() -> {
      User user = new User();
      user.setOid(oid);
      user.setName(name);
      return userRepository.save(user);
    });
  }

  public Optional<User> getUserByOid(String oid) {
    return userRepository.findByOid(oid);
  }
}
