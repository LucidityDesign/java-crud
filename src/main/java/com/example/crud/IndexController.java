package com.example.crud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.crud.softwareEngineer.SoftwareEngineer;
import com.example.crud.softwareEngineer.SoftwareEngineerService;
import com.example.crud.user.User;
import com.example.crud.user.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class IndexController {

  // TODO: Create facade for user and software engineer services
  @Autowired
  private UserService userService;
  @Autowired
  private SoftwareEngineerService softwareEngineerService;

  @Value("${spring.security.oauth2.client.registration.azure-dev.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.azure-dev.client-secret}")
  private String clientSecret;

  @GetMapping
  public String index(Model model, Authentication user) {
    model.addAttribute("user", user);

    List<User> users = userService.getAllUsers(0);
    model.addAttribute("users", users);

    List<SoftwareEngineer> softwareEngineers = softwareEngineerService.getAllSoftwareEngineers(0);
    model.addAttribute("softwareEngineers", softwareEngineers);

    return "index";
  }

  @GetMapping("/secure")
  @ResponseBody
  public String getSecureData(JwtAuthenticationToken token) {
    return "Hello " + token.getToken().getClaimAsString("name");
  }

  @GetMapping("/logout")
  public String logout() {
    return "redirect:/login?logout";
  }

  @GetMapping("/me")
  public ResponseEntity<User> getMe(@AuthenticationPrincipal OAuth2User oauth2User) {
    String oid = oauth2User.getAttribute("oid");
    return userService.getUserByOid(oid)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

}
