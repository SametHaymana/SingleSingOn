package com.sso.api.modules.auth.services;

import com.sso.api.models.User;
import com.sso.api.repositories.UserRepository;
import com.sso.api.utils.responses.ApiErrors.NotFoundError;
import com.sso.api.utils.responses.ApiResponseCodes;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserByEmail(String email) {
    return userRepository
      .findByEmail(email)
      .orElseThrow(() -> new NotFoundError(ApiResponseCodes.UserNotFound));
  }
}
