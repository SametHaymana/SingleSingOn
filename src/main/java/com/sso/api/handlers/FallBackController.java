package com.sso.api.handlers;

import com.sso.api.utils.responses.ApiErrors.NotFoundError;
import com.sso.api.utils.responses.ApiResponseCodes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FallBackController {

  @RequestMapping("/**")
  public ResponseEntity<Object> notFound() {
    throw new NotFoundError(ApiResponseCodes.PageNotFound);
  }
}
