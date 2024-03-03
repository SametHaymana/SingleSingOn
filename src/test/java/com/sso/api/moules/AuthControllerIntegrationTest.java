package com.sso.api.moules;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.sso.api.models.Client;
import com.sso.api.repositories.ClientRepository;

import jakarta.transaction.Transactional;


import org.springframework.http.MediaType;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


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
    public void whenClientCheckWithValidData_thenReturnsTrue() throws Exception {
        mockMvc.perform(get("/api/auth/client/check")
                .param("clientId", this.client.getUid().toString())
                .param("redirectUri", this.client.getRedirectUri()) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void whenClientCheckWithInvalidData_thenReturnsFalse() throws Exception {
        mockMvc.perform(get("/api/auth/client/check")
                .param("clientId", this.client.getUid().toString())
                .param("redirectUri", "http://invalid.uri")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

}
