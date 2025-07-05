package com.example.crud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CrudApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void contextLoads() {
  }

  @Test
  @WithMockUser
  void homeEndpointReturnsWelcomeMessage() throws Exception {
    mockMvc.perform(get("/api"))
        .andDo(System.out::println)
        .andExpect(status().isOk())
        .andExpect(content().string("Welcome to the CRUD application!"));
  }

  @Test
  @WithMockUser
  void healthEndpointReturnsRunningMessage() throws Exception {
    mockMvc.perform(get("/health"))
        .andExpect(status().isOk())
        .andExpect(content().string("Application is running!"));
  }
}
