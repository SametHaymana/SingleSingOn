package com.sso.api.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public final class Hasher {
  private static final Argon2PasswordEncoder encoder;

  static {
    encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
  }

  public static String hash(String password) {
    return encoder.encode(password);
  }

  public static boolean verify(String password, String hashedPassword) {
    return encoder.matches(password, hashedPassword);
  }
}
