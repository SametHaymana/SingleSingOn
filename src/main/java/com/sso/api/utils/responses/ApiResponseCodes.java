package com.sso.api.utils.responses;

import lombok.Getter;
import org.hibernate.query.Page;

@Getter
public enum ApiResponseCodes {
  InternalServerError(50000, "Internal server error"),
  PageNotFound(40400, "Page not found"),
  ClientNotFound(40401, "Client not found"),
  UserNotFound(40401, "User not found");

  private final int code;
  private final String message;

  ApiResponseCodes(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
