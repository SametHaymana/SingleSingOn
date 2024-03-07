package com.sso.api.modules.auth.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;
import lombok.Data;

@Data
public class UserLoginRequestDto {
  @NotBlank(message = "state is required")
  private String state;

  @NotBlank(message = "redirect_uri is required")
  private String redirectUri;

  @NotEmpty(message = "client_id is required")
  private UUID clientId;

  @NotBlank(message = "timezone is required")
  private String timezone;

  @NotBlank(message = "email is required")
  private String email;

  @NotBlank(message = "password is required")
  private String password;
}
