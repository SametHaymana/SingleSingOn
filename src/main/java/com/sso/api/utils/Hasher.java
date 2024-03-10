package com.sso.api.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public final class Hasher {
  private static final Argon2PasswordEncoder encoderArgon2;

  static {
    encoderArgon2 = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
  }

  public static String pass_hash(String password) {
    return encoderArgon2.encode(password);
  }

  public static boolean pass_verify(String password, String hashedPassword) {
    return encoderArgon2.matches(password, hashedPassword);
  }

  public static String hashSHA256(String data) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
    return new String(Hex.encode(hash));
  }
}
