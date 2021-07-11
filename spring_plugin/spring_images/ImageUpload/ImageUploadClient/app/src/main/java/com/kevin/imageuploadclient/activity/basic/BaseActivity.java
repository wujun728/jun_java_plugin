package com.kevin.imageuploadclient.activity.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewConfiguration;

import com.kevin.imageuploadclient.KevinApplication;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * 版权所有：XXX有限公司</br>
 * 
 * BaseActivity </br>
 * 
 * @author zhou.wenkai ,Created on 2015-7-21 09:16:37</br>
 * 		Major Function：Activity基类 </br>
 * 
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！</br>
 * @author mender，Modified Date Modify Content:
 */
public abstract class BaseActivity extends ActionBarActivity {

	protected Context mContext			= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		mContext = this;
        KevinApplication.getInstance().mActivityStack.addActivity(this);
		setOverflowShowingAlways();
        super.onCreate(savedInstanceState);

		initContentView();
		ButterKnife.bind(this);
		init();
		initViews();
		initEvents();
    }

	/**
	 * 设置总是显示溢出菜单
	 *
	 * @return void
	 * @date 2015-7-25 12:01:31
	 */
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}

	public void finish() {
		super.finish();
		KevinApplication.getInstance().mActivityStack.removeActivity(this);
	}

	/**
	 * 初始化布局
	 */
	protected abstract void initContentView();

	/**
	 * 初始化
	 */
	protected void init() {}

	/**
	 * 初始化View
	 */
	protected abstract void initViews();

	/**
	 * 初始化事件
	 */
	protected abstract void initEvents();
}
