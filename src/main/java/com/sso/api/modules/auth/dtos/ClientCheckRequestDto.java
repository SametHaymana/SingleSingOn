package com.sso.api.modules.auth.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientCheckRequestDto {
    
    @NotNull(message = "clientId is required")
    private UUID clientId;

    @NotBlank(message = "client redirect url is required")
    private String redirectUri;
}
