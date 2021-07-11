package com.battcn.boot.extend.configuration.oss.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.BufferedReader;

/**
 * 响应结果
 *
 * @author Levin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DownloadResponse extends BaseResponse implements java.io.Serializable {

    private static final long serialVersionUID = -498222912510624959L;

    private BufferedReader bufferedReader;

    public static DownloadResponse success(BufferedReader bufferedReader) {
        DownloadResponse response = new DownloadResponse();
        response.setSuccessful(true);
        response.setBufferedReader(bufferedReader);
        return response;
    }

    public static DownloadResponse error(String message) {
        DownloadResponse response = new DownloadResponse();
        response.setSuccessful(false);
        response.setBufferedReader(null);
        response.setMessage(message);
        return response;
    }
}
