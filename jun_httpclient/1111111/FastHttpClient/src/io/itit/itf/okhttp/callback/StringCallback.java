package io.itit.itf.okhttp.callback;

import java.io.IOException;

import io.itit.itf.okhttp.Response;
import okhttp3.Call;

/**
 * 
 * @author icecooly
 *
 */
public abstract class StringCallback extends Callback{
	//
	@Override
	public void onResponse(Call call, Response response, int id) {
		try {
			onSuccess(call,response.body().string(),id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//
	public void onSuccess(Call call,String response,int id) {
		
	}
}
