package com.sso.api.utils.responses;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> {
  private int status;
  private T body;

  public ApiResponse(int status, T body) {
    this.status = status;
    this.body = body;
  }

  public ApiResponse(T body) {
    this.status = 200;
    this.body = body;
  }

  public static <T> ResponseEntity<ApiResponse<T>> ok(T body) {
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), body));
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(int status, T body) {
    return ResponseEntity.status(status).body(new ApiResponse<>(status, body));
  }
}
