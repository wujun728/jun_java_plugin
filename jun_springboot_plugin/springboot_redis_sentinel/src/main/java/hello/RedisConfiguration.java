package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
  @Bean
  ReactiveRedisOperations<String, String> redisOperations(ReactiveRedisConnectionFactory factory) {

    RedisSerializationContext.RedisSerializationContextBuilder<String, String> builder =
      RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

    RedisSerializationContext<String, String> context = builder.value(new StringRedisSerializer()).build();

    return new ReactiveRedisTemplate<>(factory, context);
  }

}
