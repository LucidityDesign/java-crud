package com.example.crud.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public void syncUser(OAuth2User oauthUser) {
    String oid = oauthUser.getAttribute("oid");
    String name = oauthUser.getAttribute("name");
    String email = oauthUser.getAttribute("email");

    userRepository.findByOid(oid).orElseGet(() -> {
      User user = new User();
      user.setOid(oid);
      user.setName(name);
      user.setEmail(email);
      return userRepository.save(user);
    });
  }

  public User updateUser(User user) {
    return userRepository.save(user);
  }

  public Optional<User> getUserByOid(String oid) {
    return userRepository.findByOid(oid);
  }

  public List<User> getAllUsers(Integer page) {
    int pageNumber = (page != null) ? page : 0;
    return userRepository.findAll(Pageable.ofSize(10).withPage(pageNumber)).getContent();
  }

  public User getReferenceByOid(String oid) {
    return userRepository.getReferenceByOid(oid);
  }
}
