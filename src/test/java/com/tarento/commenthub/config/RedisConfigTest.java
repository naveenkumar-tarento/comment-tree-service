package com.tarento.commenthub.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

class RedisConfigTest {

  @Test
  void redisTemplateBeanIsConfiguredWithConnectionFactoryAndKeySerializer() {
    RedisConfig redisConfig = new RedisConfig();
    RedisConnectionFactory connectionFactory = mock(RedisConnectionFactory.class);

    RedisTemplate<String, Object> redisTemplate = redisConfig.redisTemplate(connectionFactory);

    assertNotNull(redisTemplate);
    assertNotNull(redisTemplate.getKeySerializer());
    assertNotNull(redisTemplate.getConnectionFactory());
  }
}
