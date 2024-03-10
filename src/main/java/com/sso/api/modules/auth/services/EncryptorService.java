package com.sso.api.modules.auth.services;

import com.sso.api.config.SsoProperties;
import com.sso.api.utils.encyrptions.SymetricEncryption;
import java.security.GeneralSecurityException;
import org.springframework.stereotype.Service;

@Service
public class EncryptorService {
  private final SymetricEncryption symetricEncryption;

  public EncryptorService(SsoProperties ssoProperties) {
    this.symetricEncryption =
      new SymetricEncryption(ssoProperties.getSecurity().getApiSecret());
  }

  public String encrypt(String value) throws GeneralSecurityException {
    return symetricEncryption.encrypt(value);
  }

  public String decrypt(String value) throws GeneralSecurityException {
    return symetricEncryption.decrypt(value);
  }
}
