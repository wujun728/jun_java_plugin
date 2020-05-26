package com.jun.plugin.zxing;

import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;

/**
 * 二维码配置信息
 * 
 * @author X-rapido
 * 
 */
public class ZXingConfig {
    private boolean logoFlg = false;    // 是否添加Log图片
    private String content; // 二维码编码内容
    private BarcodeFormat barcodeformat = BarcodeFormat.QR_CODE;    //编码类型
    private int width = 200;    // 生成图片宽度
    private int height = 200;   //  生成图片高度
    private Map<EncodeHintType, ?> hints;   // 设置参数
    private String logoPath;    // Logo图片路径
    private String putPath; // 图片输出路径
    private LogoConfig LogoConfig;  // logo图片参数

    /**
     * 获取 是否添加Log图片
     * 
     * @return logoFlg 是否添加Log图片
     */
    public boolean isLogoFlg() {
        return logoFlg;
    }

    /**
     * 设置 是否添加Log图片
     * 
     * @param logoFlg 是否添加Log图片
     */
    public void setLogoFlg(boolean logoFlg) {
        this.logoFlg = logoFlg;
    }

    /**
     * 获取 二维码编码内容
     * 
     * @return content 二维码编码内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置 二维码编码内容
     * 
     * @param content 二维码编码内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取 编码类型
     * 
     * @return barcodeformat 编码类型
     */
    public BarcodeFormat getBarcodeformat() {
        return barcodeformat;
    }

    /**
     * 设置 编码类型
     * 
     * @param barcodeformat 编码类型
     */
    public void setBarcodeformat(BarcodeFormat barcodeformat) {
        this.barcodeformat = barcodeformat;
    }

    /**
     * 获取 生成图片宽度
     * 
     * @return width 生成图片宽度
     */
    public int getWidth() {
        return width;
    }

    /**
     * 设置 生成图片宽度
     * 
     * @param width 生成图片宽度
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 获取 生成图片高度
     * 
     * @return height 生成图片高度
     */
    public int getHeight() {
        return height;
    }

    /**
     * 设置 生成图片高度
     * 
     * @param height 生成图片高度
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 获取 设置参数
     * 
     * @return hints 设置参数
     */
    public Map<EncodeHintType, ?> getHints() {
        return hints;
    }

    /**
     * 设置 设置参数
     * 
     * @param hints 设置参数
     */
    public void setHints(Map<EncodeHintType, ?> hints) {
        this.hints = hints;
    }

    /**
     * 获取 Logo图片路径
     * 
     * @return logoPath Logo图片路径
     */
    public String getLogoPath() {
        return logoPath;
    }

    /**
     * 设置 Logo图片路径
     * 
     * @param logoPath Logo图片路径
     */
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    /**
     * 获取 图片输出路劲
     * 
     * @return putPath 图片输出路劲
     */
    public String getPutPath() {
        return putPath;
    }

    /**
     * 设置 图片输出路劲
     * 
     * @param putPath 图片输出路劲
     */
    public void setPutPath(String putPath) {
        this.putPath = putPath;
    }

    /**
     * 获取 logoConfig
     * 
     * @return logoConfig logoConfig
     */
    public LogoConfig getLogoConfig() {
        return LogoConfig;
    }

    /**
     * 设置 logoConfig
     * 
     * @param logoConfig logoConfig
     */
    public void setLogoConfig(LogoConfig logoConfig) {
        LogoConfig = logoConfig;
    }

}