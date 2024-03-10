package com.sso.api.utils.encyrptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.GeneralSecurityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SymetricEncyrptionTest {

  @Test
  public void testEncryptions() throws GeneralSecurityException {
    SymetricEncryption ss = new SymetricEncryption("password123");
    String data = "data";
    String encryptedData = ss.encrypt(data);

    assertTrue(
      !data.equals(encryptedData),
      "The encrypted data should not match the original data."
    );

    assertTrue(
      ss.decrypt(encryptedData).equals(data),
      "The decrypted data should match the original data."
    );
  }
}
