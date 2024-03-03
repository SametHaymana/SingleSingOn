package com.sso.api.handlers;

import com.sso.api.utils.responses.ApiError;
import com.sso.api.utils.responses.ApiErrors.InternalServerError;
import com.sso.api.utils.responses.ApiResponseCodes;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Object> handleException(Exception e) {
    ApiError err;
    if (e instanceof ApiError) {
      err = (ApiError) e;
    } else {
      // Log the exception
      e.printStackTrace();
      // Fallback to Internal Server Error
      err = new InternalServerError(ApiResponseCodes.InternalServerError);
    }
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("status", err.getStatus());
    responseBody.put(
      "body",
      new HashMap<String, Object>() {

        {
          put("apiError", err.getBody().getApiError());
          put("message", err.getBody().getMessage());
        }
      }
    );

    return ResponseEntity.status(err.getStatus()).body(responseBody);
  }
}
