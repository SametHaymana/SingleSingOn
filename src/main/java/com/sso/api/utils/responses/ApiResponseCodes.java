package com.sso.api.utils.responses;

import lombok.Getter;

@Getter
public enum ApiResponseCodes {
  InternalServerError(50000, "Internal server error"),

  /*    NotFound 404    */
  PageNotFound(40400, "Page not found"),
  ClientNotFound(40401, "Client not found"),
  UserNotFound(40401, "User not found"),

  /*    Forbbiden 403    */

  /*    Unauthorized 401  */

  /*    BadRequest 400    */
  RedirectUriNotValid(40000, "Redirect uri not valid"),
  InvalidCredentials(40001, "Invalid credentials");

  private final int code;
  private final String message;

  ApiResponseCodes(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
