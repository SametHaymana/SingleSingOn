package com.sso.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sso")
public class SsoProperties {
  private static final Security security = new Security();

  public Security getSecurity() {
    return security;
  }

  public static class Security {
    private String apiSecret;
    private String tokenSecret;

    public String getApiSecret() {
      return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
      this.apiSecret = apiSecret;
    }

    public String getTokenSecret() {
      return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
      this.tokenSecret = tokenSecret;
    }
  }
}
