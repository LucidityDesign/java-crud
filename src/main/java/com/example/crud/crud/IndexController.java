package com.example.crud.crud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

  @Autowired
  private UserService userService;

  @Value("${spring.security.oauth2.client.registration.azure-dev.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.azure-dev.client-secret}")
  private String clientSecret;

  @GetMapping
  public String index(Model model, Authentication user) {
    model.addAttribute("user", user);

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
