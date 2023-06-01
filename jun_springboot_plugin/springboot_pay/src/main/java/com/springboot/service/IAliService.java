package com.springboot.service;

import com.springboot.model.PayModel;

public interface IAliService {

	public String h5Pay(PayModel payModel);

	public String qrCodePay(PayModel payModel) throws Exception;

	public String pcPay(PayModel payModel);

}
