package com.springboot.service;

import com.springboot.model.PayModel;

public interface IWxService {

	public String wxQrCode(PayModel payModel) throws Exception;

	public String wxJssdkPay(PayModel payModel) throws Exception;

	public String h5Pay(PayModel payModel) throws Exception;
}
