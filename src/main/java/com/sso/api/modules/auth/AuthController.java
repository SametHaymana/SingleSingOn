package com.sso.api.modules.auth;

import com.sso.api.models.Client;
import com.sso.api.models.User;
import com.sso.api.modules.auth.dtos.requests.ClientCheckRequestDto;
import com.sso.api.modules.auth.dtos.requests.UserLoginRequestDto;
import com.sso.api.modules.auth.dtos.responses.ClientCheckResponseDto;
import com.sso.api.modules.auth.dtos.responses.UserLoginResponseDto;
import com.sso.api.modules.auth.services.ClientService;
import com.sso.api.modules.auth.services.EncryptorService;
import com.sso.api.modules.auth.services.UserService;
import com.sso.api.utils.Hasher;
import com.sso.api.utils.responses.ApiErrors.BadRequestError;
import com.sso.api.utils.responses.ApiResponse;
import com.sso.api.utils.responses.ApiResponseCodes;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
  private final ClientService clientService;
  private final UserService userService;
  private final EncryptorService encryptorService;

  public AuthController(
    ClientService clientService,
    UserService userService,
    EncryptorService encryptorService
  ) {
    this.clientService = clientService;
    this.userService = userService;
    this.encryptorService = encryptorService;
  }

  private boolean validateRedirectUri(Client client, String redirectUri) {
    if (client == null) {
      return false;
    }
    return client.getRedirectUri().equals(redirectUri);
  }

  @GetMapping("/client/check")
  public ResponseEntity<ApiResponse<ClientCheckResponseDto>> getMethodName(
    @Valid @ModelAttribute ClientCheckRequestDto param
  ) {
    UUID clientId = param.getClientId();
    String redirectUri = param.getRedirectUri();

    Client client = clientService.getClient(clientId);

    if (!this.validateRedirectUri(client, redirectUri)) {
      throw new BadRequestError(ApiResponseCodes.RedirectUriNotValid);
    }
    return ApiResponse.ok(new ClientCheckResponseDto(true));
  }

  /*
   * Login function
   *
   * params:
   * - state
   * - redirect_uri
   * - client_id
   * - timezome
   * - email
   * - password
   * return:
   * - updated redirect_uri with state and code
   * - state
   *
   */
  @GetMapping("/login")
  public ResponseEntity<ApiResponse<UserLoginResponseDto>> login(
    @Valid @RequestBody UserLoginRequestDto body
  )
    throws Exception {
    Client client = clientService.getClient(body.getClientId());

    if (!this.validateRedirectUri(client, body.getRedirectUri())) {
      throw new BadRequestError(ApiResponseCodes.RedirectUriNotValid);
    }

    // Get User
    User user = this.userService.getUserByEmail(body.getEmail());

    // Validate user password
    if (!Hasher.pass_verify(body.getPassword(), user.getPassword())) {
      throw new BadRequestError(ApiResponseCodes.InvalidCredentials);
    }

    // TODO
    // Check user other verification
    // 2FA, email verification, etc

    // Generate code
    /*
     * Code Structure
     *
     * created_at:expired_at:client_id:user_id:state:API_SECRET1:hash
     *
     * as encypted base64
     *
     */

    String code = "";
    code += System.currentTimeMillis() + ":";
    code += (System.currentTimeMillis() + 1000 * 60 * 5) + ":"; // 5 minutes
    code += client.getUid() + ":";
    code += user.getUid() + ":";
    code += body.getState() + ":";
    code += Hasher.hashSHA256(code);

    // Encrypt code
    code = this.encryptorService.encrypt(code);

    // Update redirect uri
    String updatedRedirectUri =
      body.getRedirectUri() + "?code=" + code + "&state=" + body.getState();

    // ToDo
    // Save code to database
    // Auditing
    // Log user login
    // etc

    return ApiResponse.ok(new UserLoginResponseDto(updatedRedirectUri, body.getState()));
  }
}
