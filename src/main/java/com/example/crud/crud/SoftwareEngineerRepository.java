package com.example.crud.crud;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Integer> {
  // This interface will automatically provide CRUD operations for
  // SoftwareEngineer entities
  // Additional custom query methods can be defined here if needed

  // For example, you can add methods like:
  // List<SoftwareEngineer> findByRole(String role);

  List<SoftwareEngineer> findByLevel(String level);

  @Query("SELECT se FROM SoftwareEngineer se WHERE se.id > ?1")
  List<SoftwareEngineer> findByIdGreaterThan(Integer id);

}
