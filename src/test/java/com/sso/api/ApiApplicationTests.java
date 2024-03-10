package com.sso.api;

import com.sso.api.config.SsoProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(SsoProperties.class)
class ApiApplicationTests {

  @Test
  void contextLoads() {}
}
