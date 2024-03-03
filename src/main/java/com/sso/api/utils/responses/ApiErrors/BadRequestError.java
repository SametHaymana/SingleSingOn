package com.sso.api.utils.responses.ApiErrors;

import com.sso.api.utils.responses.ApiError;
import com.sso.api.utils.responses.ApiResponseCodes;
import org.springframework.http.HttpStatus;

public class BadRequestError extends ApiError {

  public BadRequestError(ApiResponseCodes apiRes) {
    super(HttpStatus.BAD_REQUEST, apiRes);
  }
}
