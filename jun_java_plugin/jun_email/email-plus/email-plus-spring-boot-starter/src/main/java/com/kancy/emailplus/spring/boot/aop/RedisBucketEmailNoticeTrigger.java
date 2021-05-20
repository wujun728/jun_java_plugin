package com.kancy.emailplus.spring.boot.aop;

import com.kancy.emailplus.spring.boot.properties.EmailplusProperties;
import com.kancy.emailplus.spring.boot.properties.NoticeProperties;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;

/**
 * RamBucketEmailNoticeTrigger
 *
 * @author Wujun
 * @date 2020/2/22 21:23
 */
public class RedisBucketEmailNoticeTrigger implements EmailNoticeTrigger {

    private static final DefaultRedisScript<String> redisBucketScript = getRedisBucketScript();

    @Resource
    private EmailplusProperties emailplusProperties;

    @Resource
    private RedisTemplate redisBucketTemplate;

    /**
     * 是否触发
     * 能获取令牌标识异常容忍，否则触发邮件通知
     *
     * @param name
     * @return
     */
    @Override
    public boolean isTrigger(String name) {
        NoticeProperties noticeProperties = emailplusProperties.getEmailNotices().get(name);
        String project = emailplusProperties.getConfiguration().getProject();
        String key = String.format("%s:email:notice:bucket:%s", project, name);
        Long capacity = noticeProperties.getCapacity();
        Duration refillDuration = noticeProperties.getRefillDuration();
        Long refillTokens = noticeProperties.getRefillTokens();
        return !requireToken(key, capacity, refillDuration, refillTokens);
    }

    /**
     * 从令牌桶获取一个token
     * @return
     */
    protected boolean requireToken(String key, Long capacity, Duration refillDuration, Long refillTokens) {
        Object result = redisBucketTemplate.execute(
                (RedisConnection connection) -> connection.eval(
                        redisBucketScript.getScriptAsString().getBytes(),
                        ReturnType.INTEGER,
                        1,
                        key.getBytes(),
                        String.valueOf(capacity).getBytes(),
                        String.valueOf(refillDuration.toMillis()).getBytes(),
                        String.valueOf(refillTokens).getBytes())
        );
        return Objects.equals(String.valueOf(result), "1");
    }

    /**
     * 获取令牌桶redis实现脚本
     * capacity：令牌容量
     * refillIntervalTime：间隔时间 （单位毫秒）
     * refillTokens: 每次间隔加入的令牌个数
     * expireTime：refillIntervalTime * capacity * refillTokens
     * @return
     */
    private static DefaultRedisScript<String> getRedisBucketScript() {
        StringBuilder sb = new StringBuilder();
        sb.append("local key = KEYS[1] ");
        sb.append("local capacity = tonumber(ARGV[1]) ");
        sb.append("local refillIntervalTime = tonumber(ARGV[2]) ");
        sb.append("local refillTokens = tonumber(ARGV[3]) ");
        sb.append("local expireTime = refillIntervalTime * capacity * refillTokens ");
        sb.append("local ttlTime = redis.call(\"pttl\",key) ");
        sb.append("local sum = math.floor((expireTime - ttlTime) / refillIntervalTime) ");
        sb.append("if sum > 0 then ");
        sb.append("    if redis.call(\"incrby\",key,sum) > capacity then ");
        sb.append("        redis.call(\"set\",key,capacity) ");
        sb.append("    end ");
        sb.append("    redis.call(\"pexpire\",key,expireTime) ");
        sb.append("end ");
        sb.append("if redis.call(\"decr\",key) >= 0 ");
        sb.append("        then ");
        sb.append("    return 1 ");
        sb.append("else ");
        sb.append("    redis.call(\"incr\",key) ");
        sb.append("    return 0 ");
        sb.append("end ");
        DefaultRedisScript<String> script = new DefaultRedisScript<>();
        script.setScriptText(sb.toString());
        return script;
    }

}
