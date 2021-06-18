spring cache 和 j2cache 继承示例代码，spring 配置类

```java
/**
 * @author Chen
 */
@Configuration
@EnableCaching
public class MyCacheConfig extends CachingConfigurerSupport {
    @Override
    public CacheManager cacheManager() {
        // 引入配置
        J2CacheConfig config = J2CacheConfig.initFromConfig("/j2cache.properties");
        // 生成 J2CacheBuilder
        J2CacheBuilder j2CacheBuilder = J2CacheBuilder.init(config);
        // 构建适配器
        J2CacheSpringCacheManageAdapter j2CacheSpringCacheManageAdapter = new J2CacheSpringCacheManageAdapter(j2CacheBuilder, true);

        return j2CacheSpringCacheManageAdapter;
    }
}
```