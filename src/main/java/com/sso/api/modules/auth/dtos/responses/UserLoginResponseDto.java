package com.sso.api.modules.auth.dtos.responses;

public class UserLoginResponseDto {
  private final String url;
  private final String state;

  public UserLoginResponseDto(String url, String state) {
    this.url = url;
    this.state = state;
  }
}
