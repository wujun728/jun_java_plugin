package com.zengtengpeng.aop;

import com.zengtengpeng.annotation.Lock;
import com.zengtengpeng.enums.LockModel;
import com.zengtengpeng.excepiton.LockException;
import com.zengtengpeng.properties.RedissonProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 分布式锁aop
 */
@Aspect
@Order(-10)
public class LockAop {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedissonProperties redissonProperties;
    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(lock)")
    public void controllerAspect(Lock lock) {
    }

    /**
     * 通过spring Spel 获取参数
     * @param key               定义的key值 以#开头 例如:#user
     * @param parameterNames    形参
     * @param values             形参值
     * @param keyConstant key的常亮
     * @return
     */
    public List<String> getVauleBySpel(String key, String[] parameterNames, Object[] values, String keyConstant) {
        List<String> keys=new ArrayList<>();
        if(!key.contains("#")){
            String s = "redisson:lock:" + key+keyConstant;
            log.info("没有使用spel表达式value->{}",s);
            keys.add(s);
            return keys;
        }
        //spel解析器
        ExpressionParser parser = new SpelExpressionParser();
        //spel上下文
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], values[i]);
        }
        Expression expression = parser.parseExpression(key);
        Object value = expression.getValue(context);
        if(value!=null){
            if(value instanceof List){
                List value1 = (List) value;
                for (Object o : value1) {
                    keys.add("redisson:lock:" + o.toString()+keyConstant);
                }
            }else if(value.getClass().isArray()){
                Object[] obj= (Object[]) value;
                for (Object o : obj) {
                    keys.add("redisson:lock:" + o.toString()+keyConstant);
                }
            }else {
                keys.add("redisson:lock:" + value.toString()+keyConstant);
            }
        }
        log.info("spel表达式key={},value={}",key,keys);
        return keys;
    }

    @Around("controllerAspect(lock)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, Lock lock) throws Throwable {
        String[] keys = lock.keys();
        if (keys.length == 0) {
            throw new RuntimeException("keys不能为空");
        }
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(((MethodSignature) proceedingJoinPoint.getSignature()).getMethod());
        Object[] args = proceedingJoinPoint.getArgs();

        long attemptTimeout = lock.attemptTimeout();
        if (attemptTimeout == 0) {
            attemptTimeout = redissonProperties.getAttemptTimeout();
        }
        long lockWatchdogTimeout = lock.lockWatchdogTimeout();
        if (lockWatchdogTimeout == 0) {
            lockWatchdogTimeout = redissonProperties.getLockWatchdogTimeout();
        }
        LockModel lockModel = lock.lockModel();
        if (lockModel.equals(LockModel.AUTO)) {
            LockModel lockModel1 = redissonProperties.getLockModel();
            if (lockModel1 != null) {
                lockModel = lockModel1;
            } else if (keys.length > 1) {
                lockModel = LockModel.REDLOCK;
            } else {
                lockModel = LockModel.REENTRANT;
            }
        }
        if (!lockModel.equals(LockModel.MULTIPLE) && !lockModel.equals(LockModel.REDLOCK) && keys.length > 1) {
            throw new RuntimeException("参数有多个,锁模式为->" + lockModel.name() + ".无法锁定");
        }
        log.info("锁模式->{},等待锁定时间->{}秒.锁定最长时间->{}秒",lockModel.name(),attemptTimeout/1000,lockWatchdogTimeout/1000);
        boolean res = false;
        RLock rLock = null;
        //一直等待加锁.
        switch (lockModel) {
            case FAIR:
                rLock = redissonClient.getFairLock(getVauleBySpel(keys[0],parameterNames,args,lock.keyConstant()).get(0));
                break;
            case REDLOCK:
                List<RLock> rLocks=new ArrayList<>();
                for (String key : keys) {
                    List<String> vauleBySpel = getVauleBySpel(key, parameterNames, args, lock.keyConstant());
                    for (String s : vauleBySpel) {
                        rLocks.add(redissonClient.getLock(s));
                    }
                }
                RLock[] locks=new RLock[rLocks.size()];
                int index=0;
                for (RLock r : rLocks) {
                    locks[index++]=r;
                }
                rLock = new RedissonRedLock(locks);
                break;
            case MULTIPLE:
                rLocks=new ArrayList<>();

                for (String key : keys) {
                    List<String> vauleBySpel = getVauleBySpel(key, parameterNames, args, lock.keyConstant());
                    for (String s : vauleBySpel) {
                        rLocks.add(redissonClient.getLock(s));
                    }
                }
                locks=new RLock[rLocks.size()];
                index=0;
                for (RLock r : rLocks) {
                    locks[index++]=r;
                }
                rLock = new RedissonMultiLock(locks);
                break;
            case REENTRANT:
                List<String> vauleBySpel = getVauleBySpel(keys[0], parameterNames, args, lock.keyConstant());
                //如果spel表达式是数组或者LIST 则使用红锁
                if(vauleBySpel.size()==1){
                    rLock= redissonClient.getLock(vauleBySpel.get(0));
                }else {
                    locks=new RLock[vauleBySpel.size()];
                    index=0;
                    for (String s : vauleBySpel) {
                        locks[index++]=redissonClient.getLock(s);
                    }
                    rLock = new RedissonRedLock(locks);
                }
                break;
            case READ:
                RReadWriteLock rwlock = redissonClient.getReadWriteLock(getVauleBySpel(keys[0],parameterNames,args, lock.keyConstant()).get(0));
                rLock = rwlock.readLock();
                break;
            case WRITE:
                RReadWriteLock rwlock1 = redissonClient.getReadWriteLock(getVauleBySpel(keys[0],parameterNames,args, lock.keyConstant()).get(0));
                rLock = rwlock1.writeLock();
                break;
        }

        //执行aop
        if(rLock!=null) {
            try {
                if (attemptTimeout == -1) {
                    res = true;
                    //一直等待加锁
                    rLock.lock(lockWatchdogTimeout, TimeUnit.MILLISECONDS);
                } else {
                    res = rLock.tryLock(attemptTimeout, lockWatchdogTimeout, TimeUnit.MILLISECONDS);
                }
                if (res) {
                    Object obj = proceedingJoinPoint.proceed();
                    return obj;
                }else{
                    throw new LockException("获取锁失败");
                }
            } finally {
            	if (res) {
            		rLock.unlock();
            	}
            }
        }
        throw new LockException("获取锁失败");
    }
}
