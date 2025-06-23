package com.example.crud.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * Get all users with optional pagination.
   *
   * @param page Optional page number for pagination, can be null.
   * @return A list of users, paginated if a page number is provided.
   */
  @GetMapping
  public String getAllUsers(
      Model model,
      // Optional pagination parameter, can be null
      Integer page) {
    List<User> users = userService.getAllUsers(page);
    model.addAttribute("users", users);
    return "user/list";
  }

  @GetMapping("/me")
  public String getMe(JwtAuthenticationToken token, Model model) throws Exception {
    String oid = token.getToken().getClaimAsString("oid");
    User me = userService.getUserByOid(oid).orElseThrow();
    model.addAttribute("user", me);
    return "user/me";
  }
}
