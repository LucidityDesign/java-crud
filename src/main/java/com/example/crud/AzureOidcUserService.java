package com.example.crud;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import java.util.*;

public class AzureOidcUserService extends OidcUserService {

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) {
    OAuth2User user = super.loadUser(userRequest);

    Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

    // Extract roles from token claims
    List<String> roles = user.getAttribute("roles");
    if (roles != null) {
      for (String role : roles) {
        if (!role.startsWith("ROLE_")) {
          role = "ROLE_" + role;
        }
        mappedAuthorities.add(new SimpleGrantedAuthority(role));
      }
    }

    // Keep existing authorities too (like SCOPE_*, etc.)
    mappedAuthorities.addAll(user.getAuthorities());

    return new org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser(
        mappedAuthorities,
        userRequest.getIdToken(),
        "name" // or use "preferred_username", etc. based on what you want as principal name
    );
  }
}
