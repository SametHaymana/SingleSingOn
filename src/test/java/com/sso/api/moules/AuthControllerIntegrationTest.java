package com.sso.api.moules;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sso.api.models.Client;
import com.sso.api.repositories.ClientRepository;
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
  private ClientRepository clientRepository;

  private Client client;

  @BeforeEach
  public void setup() {
    Client client = new Client();
    client.setRedirectUri("http://valid.uri");
    client.setName("Test Client");
    client.setSecret("test-secret");
    // Save the client and use its generated UUID
    this.client = clientRepository.save(client);
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
}
