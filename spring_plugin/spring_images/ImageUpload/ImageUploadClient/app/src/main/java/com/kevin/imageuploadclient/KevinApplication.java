package com.kevin.imageuploadclient;

import android.app.Application;
import android.content.Context;

import com.kevin.imageuploadclient.activity.basic.ActivityStack;

/**
 * 版权所有：XXX有限公司
 *
 * KevinApplication
 *
 * @author zhou.wenkai ,Created on 2015-6-14 12:44:01
 * 		   Major Function：<b>KevinApplication</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class KevinApplication extends Application {

    protected static KevinApplication kevinApplication = null;
    /** 上下文 */
    protected Context mContext          = null;
    /** Activity 栈 */
    public ActivityStack mActivityStack = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // 由于Application类本身已经单例，所以直接按以下处理即可
        kevinApplication = this;
        mContext = getApplicationContext();     // 获取上下文
        mActivityStack = new ActivityStack();   // 初始化Activity 栈

        initConfiguration();
    }

    /**
     * 获取当前类实例对象
     * @return
     */
    public static KevinApplication getInstance(){
        return kevinApplication;
    }

    /**
     * @description 初始化配置文件
     *
     * @return void
     * @date 2015-5-22 10:44:48
     */
    private void initConfiguration() {

    }
}
