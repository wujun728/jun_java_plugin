package com.kevin.imageuploadclient.fragment.basic;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevin.imageuploadclient.R;

import butterknife.ButterKnife;

/**
 * 版权所有：----有限公司</br>
 *
 * BaseFragment </br>
 *
 * @author zhou.wenkai ,Created on 2015-1-22 22:35:48</br>
 * @Description Major Function：Fragment基类 </br>
 *
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！</br>
 * @author mender，Modified Date Modify Content:
 */
public abstract class BaseFragment extends Fragment {

	/** 类标签 */
	protected static String TAG = "";
	/** 上下文 */
	protected Context mContext 	= null;
	/** 依附的Activity */
	protected Activity mActivity= null;

	private AlertDialog mAlertDialog;
	protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
	protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		TAG = this.getClass().getSimpleName();
		mContext  = activity;
		mActivity = activity;
//		LogUtils.i(getFragmentName() + " onAttach()");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		LogUtils.i(getFragmentName() + " onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
//		LogUtils.i(getFragmentName() + " onCreateView()");
		if (getContentViewId() != 0) {
			return inflater.inflate(getContentViewId(), null);
		} else {
			return super.onCreateView(inflater, container, savedInstanceState);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
//		LogUtils.i(getFragmentName() + " onViewCreated()");
		ButterKnife.bind(this, view);
		init();
		initViews(view);
		initData();
		initEvents();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		LogUtils.i(getFragmentName() + " onActivityCreated()");
	}

	@Override
	public void onStart() {
		super.onStart();
//		LogUtils.i(getFragmentName() + " onStart()");
	}

	@Override
	public void onResume() {
		super.onResume();
//		LogUtils.i(getFragmentName() + " onResume()");
	}

	@Override
	public void onPause() {
		super.onPause();
//		LogUtils.i(getFragmentName() + " onPause()");
	}

	@Override
	public void onStop() {
		super.onStop();
//		LogUtils.i(getFragmentName() + " onStop()");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
//		LogUtils.i(getFragmentName() + " onDestroyView()");
		ButterKnife.unbind(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		LogUtils.i(getFragmentName() + " onDestroy()");
	}

	@Override
	public void onDetach() {
		super.onDetach();
//		LogUtils.i(getFragmentName() + " onDetach()");
	}

	/**
	 * 使用Toolbar代替ActionBar
	 *
	 * @return void
	 */
	protected void initToolbar(Toolbar toolbar) {
		((AppCompatActivity)mActivity).setSupportActionBar(toolbar);
	}

	/**
	 * 使用Toolbar代替ActionBar
	 *
	 * @return void
	 */
	protected void initToolbar(Toolbar toolbar, String title) {
		toolbar.setTitle(title);
		initToolbar(toolbar);
	}

	/**
	 * 请求权限
	 *
	 * 如果权限被拒绝过，则提示用户需要权限
	 */
	protected void requestPermission(final String permission, String rationale, final int requestCode) {
		if (shouldShowRequestPermissionRationale(permission)) {
			showAlertDialog(getString(R.string.permission_title_rationale), rationale,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							requestPermissions(new String[]{permission}, requestCode);
						}
					}, getString(R.string.label_ok), null, getString(R.string.label_cancel));
		} else {
			requestPermissions(new String[]{permission}, requestCode);
		}
	}

	/**
	 * 显示指定标题和信息的对话框
	 *
	 * @param title                         - 标题
	 * @param message                       - 信息
	 * @param onPositiveButtonClickListener - 肯定按钮监听
	 * @param positiveText                  - 肯定按钮信息
	 * @param onNegativeButtonClickListener - 否定按钮监听
	 * @param negativeText                  - 否定按钮信息
	 */
	protected void showAlertDialog(@Nullable String title, @Nullable String message,
								   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
								   @NonNull String positiveText,
								   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
								   @NonNull String negativeText) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
		builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
		mAlertDialog = builder.show();
	}

	/**
	 * 获取当前Fragment的名称
	 * @return
	 */
	public String getFragmentName(){
		return TAG;
	}

	/** 初始化方法 */
	public void init() {}
	/** 设置布局 */
	protected abstract int getContentViewId();
	/** 初始化View的抽象方法 */
	public abstract void initViews(View view);
	/** 初始化数据 */
	protected void initData() {}
	/** 初始化事件的抽象方法 */
	public abstract void initEvents();

}