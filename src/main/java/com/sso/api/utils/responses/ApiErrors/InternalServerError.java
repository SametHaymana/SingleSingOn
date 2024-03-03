package com.sso.api.utils.responses.ApiErrors;

import com.sso.api.utils.responses.ApiError;
import com.sso.api.utils.responses.ApiResponseCodes;
import org.springframework.http.HttpStatus;

public class InternalServerError extends ApiError {

  public InternalServerError(ApiResponseCodes apiRes) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, apiRes);
  }
}
