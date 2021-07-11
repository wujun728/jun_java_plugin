package cc.mrbird.febs.common.event;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.SetUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * 用户权限更新事件
 *
 * @author MrBird
 */
@Getter
@Setter
public class UserAuthenticationUpdatedEvent extends ApplicationEvent {

    private Set<Long> userIds = Sets.newCopyOnWriteArraySet();

    public UserAuthenticationUpdatedEvent(@NonNull Object source) {
        super(source);
    }

    public void cleanSet(Set<Long> toClean) {
        userIds = SetUtils.disjunction(userIds, toClean);
    }

}
