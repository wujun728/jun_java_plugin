package com.fiwan.qrgenerator;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 生成码的配置
 * 使用单例生成一个配置类。
 * */
public class Settings {
    private Settings() {}  
    private static final Settings single = new Settings();  
    public static Settings getInstance() {  
        return single;  
    }  
    //设置二维码的颜色 默认是黑色
	private int qrcodeColor =0;  //0 彩色    1 黑白
	//设置二维码的格式 默认是png
	private String qrcodeFiletype ="png";
	//设置二维码的大小 默认是400px
	private int qrcodeSize =400;
	//设置二维码的容错级别 默认是H
	private ErrorCorrectionLevel qrcodeErrorRate =ErrorCorrectionLevel.H;
	public int getQrcodeColor() {
		return qrcodeColor;
	}
	public void setQrcodeColor(int qrcodeColor) {
		this.qrcodeColor = qrcodeColor;
	}
	public String getQrcodeFiletype() {
		return qrcodeFiletype;
	}
	public void setQrcodeFiletype(String qrcodeFiletype) {
		this.qrcodeFiletype = qrcodeFiletype;
	}
	public int getQrcodeSize() {
		return qrcodeSize;
	}
	public void setQrcodeSize(int qrcodeSize) {
		this.qrcodeSize = qrcodeSize;
	}
	public ErrorCorrectionLevel getQrcodeErrorRate() {
		return qrcodeErrorRate;
	}
	public void setQrcodeErrorRate(ErrorCorrectionLevel qrcodeErrorRate) {
		this.qrcodeErrorRate = qrcodeErrorRate;
	}
}
