package com.sso.api.utils.responses.ApiErrors;

import org.springframework.http.HttpStatus;

import com.sso.api.utils.responses.ApiError;
import com.sso.api.utils.responses.ApiResponseCodes;

public class NotFoundError extends ApiError{
    public NotFoundError(ApiResponseCodes apiRes) {
        super(HttpStatus.NOT_FOUND, apiRes);
    }
}
