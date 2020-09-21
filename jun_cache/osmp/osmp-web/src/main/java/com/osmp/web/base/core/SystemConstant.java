/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.base.core;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年4月17日
 */
public class SystemConstant {
	
	public static final String GLOB_MES = "global_message";
	
    /** SESSION里保存用户的KEY */
    public static final String LOGIN_USER = "loginUser";
    
    /** 增加操作的编辑框 */
    public static final String ADD_TYPE = "add";
    
    /** 更新操作的编辑框 */
    public static final String EDIT_TYPE = "add";
    
    /**该角色正常*/
    public static final int ROLE_OK = 1;
    
    /**该角色禁用*/
    public static final int ROLE_NO_OK = 0;
    
    /**服务运行正常*/
    public static final int DATASERVICE_GREEN = 1;
    /**服务运行不正常*/
    public static final int DATASERVICE_RED = 0;
    
    
    /**超级管理员Id*/
    public static final String ADMIN_ROLE_ID = "0cf81070-90ee-41d8-a82b-ca8337647868";
    
    /**组件管理url后缀*/
    public static final String BUNDLES_JSON = "/bundles.json";
    
    /**组件管理启动停止刷新卸载url拼接*/
    public static final String BUNDLES_SPLICE = "/bundles/";
    
    /**组件管理服务url设置为"/"时，表示没有设置*/
    public static final String BUNDLES_MANAGER_URL = "/";
    
    /**横坐标显示的个数*/
    public static final int TIME_COUNT = 120;
    
    
    
    /****************分发策略类型**********************/
    /**判断服务名称的编码*/
    public static final String STRATEGY_SERVICE_TYPE = "SERVICE";
    
    /**判断来源IP名称的编码*/
    public static final String STRATEGY_IP_TYPE = "IP";
    
    /**判断项目名称的编码*/
    public static final String STRATEGY_PROJECT_TYPE = "PROJECT";
    
    /**判断参数的编码*/
    public static final String STRATEGY_ARGS_TYPE = "ARGS";
    
    /**判断请求URL的编码*/
    public static final String STRATEGY_URL_TYPE = "URL";

    
    /****************分发策略逻辑条件**********************/
    /**小于的编码*/
    public static final String JUDGE_LESS_TYPE = "less";
    
    /**大于的编码*/
    public static final String JUDGE_GREAT_TYPE = "great";
    
    /**小于等于的编码*/
    public static final String JUDGE_NOTGREAT_TYPE = "notgreat";
    
    /**大于等于的编码*/
    public static final String JUDGE_NOTLESS_TYPE = "notless";
    
    /**等于的编码*/
    public static final String JUDGE_EQ_TYPE = "eq";
    
    /**不等于的编码*/
    public static final String NQ_TYPE = "nq";
    
}
