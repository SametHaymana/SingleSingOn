package com.sso.api.modules.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sso.api.models.Client;
import com.sso.api.modules.auth.dtos.ClientCheckRequestDto;
import com.sso.api.repositories.ClientRepository;
import com.sso.api.repositories.UserRepository;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/auth")
public class AuthController {
    
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;


    public AuthController(UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }



    @GetMapping("/client/check")
    public ResponseEntity<Boolean> getMethodName(@Valid @ModelAttribute ClientCheckRequestDto param) {
        UUID clientId = param.getClientId();
        String redirectUri = param.getRedirectUri();

        // Check if client exists
        Client client = clientRepository.findByUid(clientId).orElse(null);

        if (client == null) return ResponseEntity.ok(false);

        // Check if redirect uri is valid
        if (!client.getRedirectUri().equals(redirectUri)) return ResponseEntity.ok(false);

        return ResponseEntity.ok(true);
    }




}
