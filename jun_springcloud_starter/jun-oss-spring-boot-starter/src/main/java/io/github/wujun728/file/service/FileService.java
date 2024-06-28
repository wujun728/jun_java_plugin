package io.github.wujun728.file.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface FileService {
    String uploadFile(HttpServletRequest request);
    String fileList(HttpServletRequest request);
    void fileDown(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException;
    String fileDel(HttpServletRequest request,HttpServletResponse response);
}
