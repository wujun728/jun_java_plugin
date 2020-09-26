package com.kancy.emailplus.spring.boot.listener;

import com.kancy.emailplus.core.EmailSenderRefreshCapable;
import com.kancy.emailplus.spring.boot.listener.event.RefreshEmailSenderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * RefreshEmailSenderEventListener
 *
 * @author kancy
 * @date 2020/2/21 21:44
 */
public class RefreshEmailSenderEventListener implements ApplicationListener<RefreshEmailSenderEvent> {

    private static final String REFRESH_SENDER_CHANGE_KEY_PREFIX = "emailplus.sender";

    @Autowired
    private List<EmailSenderRefreshCapable> emailSenderRefreshCapableList;

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(RefreshEmailSenderEvent event) {
        if (CollectionUtils.isEmpty(emailSenderRefreshCapableList)){
            return;
        }
        Set<String> changeKeys = event.getChangeKeys();
        if (!CollectionUtils.isEmpty(changeKeys)){
            Optional<String> firstKey = changeKeys.stream()
                    .filter(changeKey -> changeKey.startsWith(REFRESH_SENDER_CHANGE_KEY_PREFIX)).findFirst();
            if (firstKey.isPresent()){
                doRefresh();
            }
        }else {
            doRefresh();
        }
    }

    private void doRefresh() {
        // sender的属性发生变化
        for (EmailSenderRefreshCapable emailSenderRefreshCapable : emailSenderRefreshCapableList) {
            emailSenderRefreshCapable.refresh();
        }
    }
}
