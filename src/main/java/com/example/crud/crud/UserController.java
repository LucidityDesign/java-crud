package com.example.crud.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/me")
  public ResponseEntity<User> getMe(@AuthenticationPrincipal OAuth2User oauth2User) {
    String oid = oauth2User.getAttribute("oid");
    return userService.getUserByOid(oid)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
