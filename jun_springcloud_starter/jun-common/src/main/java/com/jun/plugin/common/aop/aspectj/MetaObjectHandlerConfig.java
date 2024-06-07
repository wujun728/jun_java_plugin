package com.jun.plugin.common.aop.aspectj;//package com.jun.plugin.system.common.aop.aspect;
//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import com.jun.plugin.system.common.utils.Constant;
//import com.jun.plugin.system.service.HttpSessionService;
//
//import lombok.extern.slf4j.Slf4j;
//
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashSet;
//
///**
// * mybatis plus 默认值配置
// *
// * @author wujun
// * @version V1.0
// * @date 2020年3月18日
// */
//@Component
//@Slf4j
//public class MetaObjectHandlerConfig implements MetaObjectHandler {
//
//    @Lazy
//    @Resource
//    HttpSessionService httpSessionService;
//
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        Date currentDate = new Date();
//        String[] setterNames = metaObject.getSetterNames();
//        HashSet<String> setterNameSet = new HashSet<>(Arrays.asList(setterNames));
//        if (setterNameSet.contains("deleted")) {
//            //默认未删除
//            setFieldValByName("deleted", Constant.DATA_NOT_DELETED, metaObject);
//        }
//        if (setterNameSet.contains("createTime")) {
//            //创建时间默认当前时间
//            setFieldValByName("createTime", currentDate, metaObject);
//        }
//        if (setterNameSet.contains("createDate")) {
//            //创建时间默认当前时间
//            setFieldValByName("createDate", currentDate, metaObject);
//        }
//        if (setterNameSet.contains("createId")) {
//            //创建时间默认当前时间
//            setFieldValByName("createId", httpSessionService.getCurrentUserId(), metaObject);
//        }
//        if (setterNameSet.contains("editor")) {
//        	setFieldValByName("editor", httpSessionService.getCurrentUsername(), metaObject);
//        }
//        if (setterNameSet.contains("updateId")) {
//            //创建时间默认当前时间
//            setFieldValByName("updateId", httpSessionService.getCurrentUserId(), metaObject);
//        }
//        if (setterNameSet.contains("updateTime")) {
//            //创建时间默认当前时间
//            setFieldValByName("updateTime", currentDate, metaObject);
//        }
//        if (setterNameSet.contains("updateDate")) {
//            //创建时间默认当前时间
//            setFieldValByName("updateDate", currentDate, metaObject);
//        }
//        if (setterNameSet.contains("creator")) {
//        	//创建时间默认当前时间
//        	setFieldValByName("creator", httpSessionService.getCurrentUsername(), metaObject);
//        }
//
//        log.info("start insert fill ....");
////        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
////        this.strictInsertFill(metaObject, "createBy", String.class, httpSessionService.getCurrentUserId());
////        this.strictInsertFill(metaObject, "createDeptId", String.class, httpSessionService.getCurrentUserId());
//
//
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        Date currentDate = new Date();
//        String[] setterNames = metaObject.getSetterNames();
//        HashSet<String> setterNameSet = new HashSet<>(Arrays.asList(setterNames));
//        if (setterNameSet.contains("updateTime")) {
//            //创建时间默认当前时间
//            setFieldValByName("updateTime", currentDate, metaObject);
//        }
//        if (setterNameSet.contains("updateDate")) {
//            //创建时间默认当前时间
//            setFieldValByName("updateDate", currentDate, metaObject);
//        }
//        if (setterNameSet.contains("updateId")) {
//            //创建时间默认当前时间
//            setFieldValByName("updateId", httpSessionService.getCurrentUserId(), metaObject);
//        }
//        log.info("start update fill ....");
////        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
////        this.strictUpdateFill(metaObject, "operator", String.class, "张三");
//    }
//}