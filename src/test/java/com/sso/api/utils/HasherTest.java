package com.sso.api.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class HasherTest {

  @Test
  public void hashAndVerifyPassword() {
    String originalPassword = "password123";
    String hashedPassword = Hasher.pass_hash(originalPassword);

    assertNotEquals(
      originalPassword,
      hashedPassword,
      "The hashed password should not match the original password."
    );

    assertTrue(
      Hasher.pass_verify(originalPassword, hashedPassword),
      "The original password should match the hashed password."
    );

    // Assert that the verification of a wrong password against the hashed password fails
    assertFalse(
      Hasher.pass_verify("wrongPassword", hashedPassword),
      "A wrong password should not match the hashed password."
    );
  }

  @Test
  public void hashSHA256() throws Exception {
    String data = "data";
    String hashedData = Hasher.hashSHA256(data);

    assertNotEquals(
      data,
      hashedData,
      "The hashed data should not match the original data."
    );

    assertTrue(
      hashedData.equals(Hasher.hashSHA256(data)),
      "The hashed data should match the original data."
    );
  }
}
