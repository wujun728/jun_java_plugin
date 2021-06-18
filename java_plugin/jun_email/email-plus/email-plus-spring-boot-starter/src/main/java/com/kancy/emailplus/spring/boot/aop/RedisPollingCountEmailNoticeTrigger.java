package com.kancy.emailplus.spring.boot.aop;

import com.kancy.emailplus.spring.boot.properties.EmailplusProperties;
import com.kancy.emailplus.spring.boot.properties.NoticeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * redis轮询计数电子邮件通知触发器
 * RedisPollingCountEmailNoticeTrigger
 *
 * @author Wujun
 * @date 2020/2/23 18:11
 */
public class RedisPollingCountEmailNoticeTrigger implements EmailNoticeTrigger{
    private static final Logger log = LoggerFactory.getLogger(RedisPollingCountEmailNoticeTrigger.class);

    @Resource
    private EmailplusProperties emailplusProperties;

    @Resource
    private RedisTemplate redisBucketTemplate;
    /**
     * 是否触发
     *
     * @param name
     * @return
     */
    @Override
    public boolean isTrigger(String name) {
        NoticeProperties noticeProperties = emailplusProperties.getEmailNotices().get(name);
        String project = emailplusProperties.getConfiguration().getProject();
        String key = String.format("%s:email:notice:polling-count:%s", project, name);
        Object result = redisBucketTemplate.execute(
                (RedisConnection connection) -> connection.eval(
                        getRedisScript().getScriptAsString().getBytes(),
                        ReturnType.INTEGER,
                        1,
                        key.getBytes(),
                        String.valueOf(noticeProperties.getCapacity()).getBytes())
        );
        return Objects.equals(String.valueOf(result), "1");
    }

    private static DefaultRedisScript<String> getRedisScript() {
        DefaultRedisScript defaultRedisScript =new DefaultRedisScript<List>();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/polling-count.lua")));
        defaultRedisScript.setResultType(Integer.class);
        return defaultRedisScript;
    }
}
