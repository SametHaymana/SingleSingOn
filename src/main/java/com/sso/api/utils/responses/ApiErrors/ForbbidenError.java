package com.sso.api.utils.responses.ApiErrors;

import com.sso.api.utils.responses.ApiError;
import com.sso.api.utils.responses.ApiResponseCodes;
import org.springframework.http.HttpStatus;

public class ForbbidenError extends ApiError {

  public ForbbidenError(ApiResponseCodes apiRes) {
    super(HttpStatus.FORBIDDEN, apiRes);
  }
}
