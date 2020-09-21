/**
 * IRabbitmqListener.java
 * Created at 2016-11-17
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.file.aliyun.oss.api.download;

import java.io.File;

/**
 * 描述 : IDownLoadProcess
 *
 * @author wangkang
 */
public interface IDownLoadProcess<T> {

    /**
     * process
     *
     * @param param param
     * @return File
     */
    File process(DownLoadParam<T> param);

}
