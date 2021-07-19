package com.jun.plugin.picturemanage.model;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.jun.plugin.picturemanage.conf.Constant;
import com.jun.plugin.picturemanage.conf.InitState;
import com.jun.plugin.picturemanage.entity.ConfEntity;
import com.jun.plugin.picturemanage.entity.UserEntity;
import com.jun.plugin.picturemanage.service.ConfService;
import com.jun.plugin.picturemanage.util.UserUtil;

import java.io.File;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/10/31 15:44
 */
@Component
@Log4j2
public class ContextStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private ConfService confService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("监听到服务启动成功 - 执行初始化操作");

        ConfEntity urlPrefix = confService.getById(Constant.URL_PREFIX_KEY_NAME);
        if (urlPrefix != null) {
            //未注册
            if (StringUtils.isNotBlank(urlPrefix.getValue())) {
                InitState.IS_INIT = true;
                InitState.URL_PREFIX = urlPrefix.getKey();
                log.info("1. 初始化Url前缀成功");
            }
        }

        String rootPath = confService.get(Constant.FILE_ROOT_KEY_NAME);
        if (!StringUtils.isBlank(rootPath)) {
            File file = new File(rootPath);
            if (file != null && file.isDirectory()) {
                Constant.ROOT_DIR = file.getAbsolutePath();
                log.info("2. 初始化文件夹成功");
            } else {
                InitState.IS_INIT = false;
                log.error("初始化文件夹失败");
            }
        }

        if (UserUtil.getUserInfo() == null) {
            UserEntity entity = UserUtil.setUserInfo("root", "root");
            if (entity == null) {
                throw new RuntimeException("服务器初始化失败 无法添加用户");
            } else {
                log.info("3. 初始化默认用户成功");
            }
        }


        log.info("数据初始全部成功...");
    }
}
