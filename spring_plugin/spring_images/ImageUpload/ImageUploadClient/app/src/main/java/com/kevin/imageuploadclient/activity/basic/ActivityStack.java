package com.kevin.imageuploadclient.activity.basic;

import android.app.Activity;

import java.util.LinkedList;

/**
 * 版权所有：XXX有限公司</br>
 *
 * ActivityStack </br>
 *
 * @author zhou.wenkai ,Created on 2015-7-24 13:58:50</br>
 * 		Major Function：Activity 栈 </br>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！</br>
 * @author mender，Modified Date Modify Content:
 */
public class ActivityStack {

    /** 存activity的list，方便管理activity */
    public LinkedList<Activity> activityList = null;

    public ActivityStack() {
        activityList = new LinkedList<Activity>();
    }

    /**
     * 将Activity添加到activityList中
     *
     * @param activity
     */
    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    /**
     * 获取栈顶Activity
     *
     * @return
     */
    public Activity getLastActivity(){
        return activityList.getLast();
    }

    /**
     * 将Activity移除
     *
     * @param activity
     */
    public void removeActivity(Activity activity){
        if(null != activity && activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

    /**
     * 判断某一Activity是否在运行
     *
     * @param className
     * @return
     */
    public boolean isActivityRunning(String className) {
        if (className != null) {
            for (Activity activity : activityList) {
                if (activity.getClass().getName().equals(className)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 退出所有的Activity
     */
    public void finishAllActivity(){
        for(Activity activity:activityList){
            if(null != activity){
                activity.finish();
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }

}
