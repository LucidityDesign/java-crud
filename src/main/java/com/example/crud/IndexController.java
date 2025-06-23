package com.example.crud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.crud.frontpage.FrontpageData;
import com.example.crud.frontpage.FrontpageFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class IndexController {

  @Autowired
  private FrontpageFacade frontpageFacade;

  @Value("${spring.security.oauth2.client.registration.azure-dev.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.azure-dev.client-secret}")
  private String clientSecret;

  @GetMapping
  public String index(Model model, Authentication user) {
    FrontpageData frontpageData = frontpageFacade.getFrontpageData();

    model.addAttribute("user", user);
    model.addAttribute("frontpageData", frontpageData);

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

}
