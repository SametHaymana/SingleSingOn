package com.sso.api.modules.auth.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ClientCheckRequestDto {
    
    @NotBlank(message = "clientId is required")
    private UUID clientId;

    @NotBlank(message = "client redirect url is required")
    private String redirectUri;
}
