package com.sso.api.utils.responses;

import org.springframework.http.HttpStatus;

import lombok.Getter;


@Getter
public abstract class ApiError extends RuntimeException {
    private final int status;
    private final ResBody body;

    public ApiError(HttpStatus status, ApiResponseCodes errorCode) {
        super(errorCode.getMessage()); 
        this.status = status.value();
        this.body = new ResBody(errorCode.getCode(), errorCode.getMessage());
    }

    @Getter
    public static class ResBody {
        private final int apiError;
        private final String message;

        public ResBody(int apiError, String message) {
            this.apiError = apiError;
            this.message = message;
        }
    }
}