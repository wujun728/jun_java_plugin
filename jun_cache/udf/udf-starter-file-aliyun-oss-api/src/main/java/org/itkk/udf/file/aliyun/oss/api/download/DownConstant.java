/**
 * BaseMqConstant.java
 * Created at 2016-12-05
 * Created by Administrator
 * Copyright (C) 2016 egridcloud.com, All rights reserved.
 */
package org.itkk.udf.file.aliyun.oss.api.download;

import java.io.File;

/**
 * 描述 : DownConstant
 *
 * @author Administrator
 */
public class DownConstant {

    /**
     * MAX_DOWNLOAD_ROW_COUNT
     */
    public static final int MAX_DOWNLOAD_ROW_COUNT = 10000;


    /**
     * 下载状态
     */
    public enum DOWNLOAD_PROCESS_STATUS {
        /**
         * 待执行
         */
        STATUS_1(1),
        /**
         * 执行中
         */
        STATUS_2(2),
        /**
         * 执行完成
         */
        STATUS_3(3),
        /**
         * 执行错误
         */
        STATUS_4(4);

        /**
         * value
         */
        private Integer value;

        /**
         * PAY_TYPE
         *
         * @param value value
         */
        DOWNLOAD_PROCESS_STATUS(Integer value) {
            this.value = value;
        }

        /**
         * value
         *
         * @return Integer
         */
        public Integer value() {
            return this.value;
        }
    }

    /**
     * 描述 : 私有化构造函数
     */
    private DownConstant() {
    }

    /**
     * tempdir
     *
     * @return String
     */
    public static String tempdir() {
        return System.getProperty("java.io.tmpdir") + File.separator;
    }

}
