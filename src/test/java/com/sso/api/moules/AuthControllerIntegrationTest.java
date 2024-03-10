package com.sso.api.moules;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sso.api.models.Client;
import com.sso.api.models.User;
import com.sso.api.modules.auth.dtos.requests.UserLoginRequestDto;
import com.sso.api.repositories.ClientRepository;
import com.sso.api.repositories.UserRepository;
import com.sso.api.utils.Hasher;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private UserRepository userRepository;

  private Client client;
  private User user;

  @BeforeEach
  public void setup() {
    Client client = new Client();
    client.setRedirectUri("http://valid.uri");
    client.setName("Test Client");
    client.setSecret("test-secret");
    // Save the client and use its generated UUID
    this.client = clientRepository.save(client);

    // Create User
    User user = new User();
    user.setClient(client);
    user.setUsername("testUser");
    user.setEmail("test@user.com");
    user.setPassword(Hasher.pass_hash("123456"));

    // Save user
    this.user = userRepository.save(user);
  }

  @Test
  public void checkClientTest() throws Exception {
    // Success
    mockMvc
      .perform(
        get("/api/auth/client/check")
          .param("clientId", this.client.getUid().toString())
          .param("redirectUri", this.client.getRedirectUri())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.status").value(200))
      .andExpect(jsonPath("$.body.valid").value(true));

    // If the redirectUri is invalid
    mockMvc
      .perform(
        get("/api/auth/client/check")
          .param("clientId", this.client.getUid().toString())
          .param("redirectUri", "http://invalid.uri")
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());

    // If the clientId is invalid
    mockMvc
      .perform(
        get("/api/auth/client/check")
          .param("clientId", UUID.randomUUID().toString())
          .param("redirectUri", this.client.getRedirectUri())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void loginTest() throws Exception {
    String state = "testState";
    String redirectUri = this.client.getRedirectUri();
    UUID clientId = this.client.getUid();
    String email = this.user.getEmail();
    String password = "123456"; // The original password before hashing

    UserLoginRequestDto loginRequest = new UserLoginRequestDto();
    loginRequest.setState(state);
    loginRequest.setRedirectUri(redirectUri);
    loginRequest.setClientId(clientId);
    loginRequest.setEmail(email);
    loginRequest.setPassword(password);

    // Perform the login request
    mockMvc
      .perform(
        post("/api/auth/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(loginRequest))
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.status").value(200))
      .andExpect(jsonPath("$.body.redirectUri").exists())
      .andExpect(jsonPath("$.body.state").value(state));

    // Test with invalid password
    loginRequest.setPassword("wrongPassword");
    mockMvc
      .perform(
        post("/api/auth/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(loginRequest))
      )
      .andExpect(status().isBadRequest());

    // Test with invalid redirectUri
    loginRequest.setPassword(password); // Reset to valid password
    loginRequest.setRedirectUri("http://invalid.uri");
    mockMvc
      .perform(
        post("/api/auth/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(loginRequest))
      )
      .andExpect(status().isBadRequest());
  }
}
