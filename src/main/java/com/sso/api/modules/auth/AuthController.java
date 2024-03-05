package com.sso.api.modules.auth;

import com.sso.api.models.Client;
import com.sso.api.modules.auth.dtos.requests.ClientCheckRequestDto;
import com.sso.api.modules.auth.dtos.responses.ClientCheckResponseDto;
import com.sso.api.modules.auth.services.ClientService;
import com.sso.api.utils.responses.ApiErrors.BadRequestError;
import com.sso.api.utils.responses.ApiResponse;
import com.sso.api.utils.responses.ApiResponseCodes;
import jakarta.validation.Valid;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
  private final ClientService clientService;

  public AuthController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping("/client/check")
  public ResponseEntity<ApiResponse<ClientCheckResponseDto>> getMethodName(
    @Valid @ModelAttribute ClientCheckRequestDto param
  ) {
    UUID clientId = param.getClientId();
    String redirectUri = param.getRedirectUri();

    Client client = clientService.getClient(clientId);

    if (!client.getRedirectUri().equals(redirectUri)) {
      throw new BadRequestError(ApiResponseCodes.RedirectUriNotValid);
    }
    return ApiResponse.ok(new ClientCheckResponseDto(true));
  }

  /*
   * Login function
   *
   * params:
   *        - state
   *        - redirect_uri
   *        - client_id
   *        - timezome
   *        - email
   *        - password
   * return:
   *      - updated redirect_uri with state and code
   *      - state
   *
   */
  @GetMapping("/login")
  public ResponseEntity<Object> login(
    @RequestParam String state,
    @RequestParam String redirect_uri,
    @RequestParam UUID client_id,
    @RequestParam String timezone,
    @RequestParam String email,
    @RequestParam String password
  ) {
    return ResponseEntity.ok(null);
  }
}
