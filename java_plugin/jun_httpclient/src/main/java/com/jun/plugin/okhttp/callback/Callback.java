package com.jun.plugin.okhttp.callback;

import com.jun.plugin.okhttp.Response;

import okhttp3.Call;

/**
 * 
 * @author Wujun
 */
public abstract class Callback{
	//
	public abstract void onFailure(Call call,Exception e,int id);
	//
	public abstract void onResponse(Call call,Response response, int id);
}