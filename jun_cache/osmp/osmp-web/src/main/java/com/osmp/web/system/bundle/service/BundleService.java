package com.osmp.web.system.bundle.service;

import java.io.File;
import java.util.List;

import com.osmp.web.system.bundle.entity.Bundle;
import com.osmp.web.system.bundle.entity.BundleInfo;

/**
 * Description:
 *
 * @author: wangkaiping
 * @date: 2014年11月19日 下午2:36:04
 */
public interface BundleService {

    /**
     * 自动调用接口获并解析BUNDLE列表
     *
     * @return
     */
    public List<Bundle> getBundles(String serverIp);

    /**
     * 根据BUNDLE ID 获取BUNDLE数据
     *
     * @param id
     * @return
     */
    public Bundle getBundleById(String id);

    /**
     * 卸载BUNDLE
     *
     * @param id
     */
    public void uninstall(String id, String serverIp);

    /**
     * 启动BUNDLE
     *
     * @param id
     */
    public void start(String id, String serverIp);

    /**
     * 停止BUNDLE
     *
     * @param id
     */
    public void stop(String id, String serverIp);

    /**
     * 刷新BUNDLE
     *
     * @param id
     */
    public void refresh(String id, String serverIp);

    /**
     * 根据BUNDLE ID 获取BUNDLE详细数据
     *
     * @param id
     * @param serverIp
     * @return
     */
    public BundleInfo getBundleInfoById(String id, String serverIp);

    /**
     * 安装或更新BUNDLE
     *
     * @param bundlestart
     *            是否安装BUNDLE
     * @param refreshPackages
     *            是否刷新packages
     * @param bundlestartlevel
     *            BUNDLE启动级别，默认80
     * @param bundlefile
     *            安装或更新的BUNDLE文件
     */
    public void installOrUpdate(String bundlestart, String refreshPackages, String bundlestartlevel, File bundlefile, String serverIp);

}
