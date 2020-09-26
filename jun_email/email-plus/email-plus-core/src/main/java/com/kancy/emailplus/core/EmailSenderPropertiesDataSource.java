package com.kancy.emailplus.core;

import com.kancy.emailplus.core.EmailSenderProperties;

import java.util.List;

/**
 * EmailSenderPropertiesDataSource
 *
 * @author kancy
 * @date 2020/2/20 3:24
 */
public interface EmailSenderPropertiesDataSource {

    /**
     * 加载
     * @return
     */
    List<EmailSenderProperties> load();

}
