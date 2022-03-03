package com.feri.fyw.config;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-15 17:32
 */
public class SystemConfig {
    //记录会话中存储用户的名称
    public static final String CURR_USER="curruser";

    //记录管理员操作日志的类型
    public static final int ALOG_TYPE_ADD=1;//新增
    public static final int ALOG_TYPE_LS=2;//登陆成功
    public static final int ALOG_TYPE_LE=3;//登陆失败
    public static final int ALOG_TYPE_UP=4;//修改密码
    public static final int ALOG_TYPE_OUT=5;//退出登陆
}
