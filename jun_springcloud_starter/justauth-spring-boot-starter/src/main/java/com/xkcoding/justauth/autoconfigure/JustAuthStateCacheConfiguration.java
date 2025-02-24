/*
 * Copyright (c) 2019-2029, xkcoding & Yangkai.Shen & 沈扬凯 (237497819@qq.com & xkcoding.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xkcoding.justauth.autoconfigure;

import com.xkcoding.justauth.support.cache.RedisStateCache;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.cache.AuthDefaultStateCache;
import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 * JustAuth 缓存装配类，{@link JustAuthAutoConfiguration.AuthStateCacheAutoConfiguration}
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/8/31 12:00
 */
@Slf4j
abstract class JustAuthStateCacheConfiguration {
    /**
     * Redis 缓存
     */
    @ConditionalOnClass(RedisTemplate.class)
    @ConditionalOnMissingBean(AuthStateCache.class)
    @AutoConfigureBefore(RedisAutoConfiguration.class)
    @ConditionalOnProperty(name = "justauth.cache.type", havingValue = "redis", matchIfMissing = true)
    static class Redis {
        static {
            log.debug("JustAuth 使用 Redis 缓存存储 state 数据");
        }

        @Bean(name = "justAuthRedisCacheTemplate")
        public RedisTemplate<String, String> justAuthRedisCacheTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, String> template = new RedisTemplate<>();
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            template.setConnectionFactory(redisConnectionFactory);
            return template;
        }

        @Bean
        public AuthStateCache authStateCache(RedisTemplate<String, String> justAuthRedisCacheTemplate, JustAuthProperties justAuthProperties) {
            return new RedisStateCache(justAuthRedisCacheTemplate, justAuthProperties.getCache());
        }
    }

    /**
     * 默认缓存
     */
    @ConditionalOnMissingBean(AuthStateCache.class)
    @ConditionalOnProperty(name = "justauth.cache.type", havingValue = "default", matchIfMissing = true)
    static class Default {
        static {
            log.debug("JustAuth 使用 默认缓存存储 state 数据");
        }

        @Bean
        public AuthStateCache authStateCache() {
            return AuthDefaultStateCache.INSTANCE;
        }
    }

    /**
     * 默认缓存
     */
    @ConditionalOnProperty(name = "justauth.cache.type", havingValue = "custom")
    static class Custom {
        static {
            log.debug("JustAuth 使用 自定义缓存存储 state 数据");
        }

        @Bean
        @ConditionalOnMissingBean(AuthStateCache.class)
        public AuthStateCache authStateCache() {
            log.error("请自行实现 me.zhyd.oauth.cache.AuthStateCache");
            throw new RuntimeException();
        }
    }
}
