package com.sso.api.modules.auth.dtos.responses;

public class ClientCheckResponseDto {
  private boolean valid;

  public ClientCheckResponseDto(boolean valid) {
    this.valid = valid;
  }

  public boolean isValid() {
    return valid;
  }
}
