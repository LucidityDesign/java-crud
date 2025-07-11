package com.example.crud.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByOid(String oid);

  User getReferenceByOid(String oid);
}
