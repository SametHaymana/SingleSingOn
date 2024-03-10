package com.sso.api.utils.encyrptions;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SymetricEncryption {
  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
  private Cipher cipher;
  private SecretKey secretKey;

  public SymetricEncryption(String key) {
    try {
      // Hash the key
      MessageDigest sha = MessageDigest.getInstance("SHA-256");
      byte[] hashedKey = sha.digest(key.getBytes(StandardCharsets.UTF_8));

      secretKey = new SecretKeySpec(hashedKey, ALGORITHM);
      cipher = Cipher.getInstance(TRANSFORMATION);
    } catch (GeneralSecurityException e) {
      throw new RuntimeException("Failed to initialize SymetricEncryption", e);
    }
  }

  public String encrypt(String original) throws GeneralSecurityException {
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedData = cipher.doFinal(original.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(encryptedData);
  }

  public String decrypt(String cypherText) throws GeneralSecurityException {
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] encryptedData = Base64.getDecoder().decode(cypherText);
    byte[] decryptedData = cipher.doFinal(encryptedData);
    return new String(decryptedData, StandardCharsets.UTF_8);
  }
}
