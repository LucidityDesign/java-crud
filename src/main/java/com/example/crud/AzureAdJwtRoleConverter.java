package com.example.crud;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.*;
import java.util.stream.Collectors;

public class AzureAdJwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
  @Override
  public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
    Collection<String> roles = new ArrayList<>();

    // App roles
    List<String> jwtRoles = jwt.getClaimAsStringList("roles");
    if (jwtRoles != null) {
      roles.addAll(jwtRoles);
    }

    return roles.stream()
        .map(role -> role.toUpperCase())
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
