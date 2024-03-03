package com.sso.api.utils.responses.ApiErrors;

import com.sso.api.utils.responses.ApiError;
import com.sso.api.utils.responses.ApiResponseCodes;
import org.springframework.http.HttpStatus;

public class NotFoundError extends ApiError {

  public NotFoundError(ApiResponseCodes apiRes) {
    super(HttpStatus.NOT_FOUND, apiRes);
  }
}
