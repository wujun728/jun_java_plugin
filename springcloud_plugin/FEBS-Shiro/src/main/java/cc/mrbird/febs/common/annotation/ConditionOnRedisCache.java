package cc.mrbird.febs.common.annotation;

import cc.mrbird.febs.common.condition.OnRedisCacheCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author MrBird
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnRedisCacheCondition.class)
public @interface ConditionOnRedisCache {
}
