package mybatis.mate.audit.service;

import mybatis.mate.audit.DataAuditEvent;
import mybatis.mate.audit.DataAuditor;
import mybatis.mate.audit.entity.User;
import mybatis.mate.audit.mapper.UserMapper;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public void dataAudit() {
        // 执行数据库操作
        User user = userMapper.selectById(1L);
        System.err.println(user);
        System.err.println("手动调用审计对比方法");
        DataAuditor.compare(user, userMapper.selectById(3L)).forEach(change -> printChange(change));

        System.err.println("-----publishEvent-----begin");
        // 发送事务监听的事件，异步回调
        applicationEventPublisher.publishEvent(new DataAuditEvent((t) -> {
            System.err.println("-----触发异步回调-----");
            User user2 = userMapper.selectById(2L);
            System.err.println(user2);
            t.apply(user, user2).forEach(change -> printChange(change));
        }));
        System.err.println("-----publishEvent-----end");
    }

    private void printChange(Change change) {
        ValueChange vc = (ValueChange) change;
        System.err.println(String.format("%s不匹配，期望值 %s 实际值 %s", vc.getPropertyName(),
                vc.getLeft(), vc.getRight()));
    }
}
