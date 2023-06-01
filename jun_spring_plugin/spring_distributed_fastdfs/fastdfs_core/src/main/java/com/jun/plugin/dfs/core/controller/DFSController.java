package com.jun.plugin.dfs.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.StorageClient1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.dfs.base.BaseController;
import com.jun.plugin.dfs.base.ErrorCodeEnum;
import com.jun.plugin.dfs.core.entity.AppInfoEntity;
import com.jun.plugin.dfs.core.entity.FileInfoEntity;
import com.jun.plugin.dfs.core.fastdfs.HttpClient;
import com.jun.plugin.dfs.core.service.AppInfoService;
import com.jun.plugin.dfs.core.service.FileInfoService;
import com.jun.plugin.dfs.utils.MimeUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping(value = "/dfs")
public class DFSController extends BaseController {

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private AppInfoService appInfoService;

    /**
     * 上传文件信息
     *
     * @param appKey
     * @param fileName
     * @param fileLength
     * @return
     */
    @RequestMapping(value = "/v1/sUpload")
    public String startUpload(@RequestParam("appKey") String appKey,
                              @RequestParam("fileName") String fileName,
                              @RequestParam("fileLength") Long fileLength) {
        int fileInfoId = -1;
        try {
            fileInfoId = fileInfoService.addFileInfo(appKey, FileInfoEntity.FILE_ACCESS_TYPE_NO_AUTH, fileName, fileLength);
        } catch (Exception e) {
            log.error("add file info error!", e);
        }
        if (fileInfoId > 0) {
            String body = "{\"fileInfoId\":" + fileInfoId + "}";
            return getResponseOKWithBody(body);
        } else {
            return getResponseByCode(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    /**
     * 更新fileId
     *
     * @param fileInfoId
     * @param fileId
     */
    @RequestMapping(value = "/v1/eUpload")
    public void endUpload(@RequestParam("fileInfoId") Integer fileInfoId, @RequestParam("fileId") String fileId) {
        if (fileId != null) {
            FileInfoEntity fileInfo = new FileInfoEntity();
            int pos = fileId.indexOf(StorageClient1.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR);
            if (pos > 0) {
                String groupName = fileId.substring(0, pos);
                fileInfo.setGroupName(groupName);
            }
            fileInfo.setId(fileInfoId);
            fileInfo.setFileId(fileId);
            fileInfo.setUpdateDate(new Date());
            fileInfo.setStatus(FileInfoEntity.FILE_STATUS_UPLOADED);
            fileInfoService.updateFileInfoById(fileInfo);
        }
    }

    /**
     * 获取服务器信息
     *
     * @param appKey
     * @return
     */
    @RequestMapping(value = "/v1/server")
    public String server(@RequestParam("appKey") String appKey) {
        String trackerServers = HttpClient.getInstance().getTrackersConfig();
        AppInfoEntity app = appInfoService.getAppInfo(appKey);
        String groupName, body;
        if (app != null) {
            groupName = app.getGroupName();
            body = "{\"trackerServers\":\"" + trackerServers + "\",\"groupName\":\"" + groupName + "\"}";
            return getResponseOKWithBody(body);
        } else {
            return getResponseByCode(ErrorCodeEnum.APP_NOT_EXIST);
        }
    }

    /**
     * 下载
     *
     * @param fileId   fastdfs返回的fileId
     * @param direct   是否直接显示，true表示可以直接显示，false表示可以下载保存成文件(默认)
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/v1/download", method = RequestMethod.GET)
    public void downloadFile(@RequestParam("fileId") String fileId, boolean direct, HttpServletResponse request, HttpServletResponse response) throws IOException {
        try {
            response.setCharacterEncoding(CharEncoding.UTF_8);
            // 注意区分大小写
            response.setHeader("Connection", "close");
            String fileExtName = FilenameUtils.getExtension(fileId);
            String contextType = MimeUtils.guessMimeTypeFromExtension(fileExtName);
            if (contextType != null) {
                response.setContentType(contextType);
            }
            String fileName = fileInfoService.getFileNameByFileId(fileId);
            if (StringUtils.isBlank(fileName)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                StringBuilder sb = new StringBuilder(128);
                fileName = sb.append(format.format(new Date())).append(".").append(fileExtName).toString();
            }
            String userAgent = request.getHeader("User-Agent");
            byte[] bytes;
            if (userAgent != null) {
                bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes(StandardCharsets.UTF_8);
            } else {
                bytes = fileName.getBytes();
            }
            // 各浏览器基本都支持ISO编码
            fileName = new String(bytes, StandardCharsets.ISO_8859_1);
            ErrorCodeEnum eCode = HttpClient.getInstance().httpDownloadFile(fileId, response, direct, fileName, fileExtName);
            if (eCode != ErrorCodeEnum.OK) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("download file error! fileId:" + fileId, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除指定文件
     *
     * @param fileId fastdfs返回的fileId
     */
    @RequestMapping(value = "/v1/delete")
    public void deleteFile(@RequestParam("fileId") String fileId) {
        try {
            FileInfoEntity fileInfo = new FileInfoEntity();
            fileInfo.setFileId(fileId);
            fileInfo.setUpdateDate(new Date());
            fileInfo.setStatus(FileInfoEntity.FILE_STATUS_DELETED);
            fileInfoService.updateFileInfoByFileId(fileInfo);
        } catch (Exception e) {
            log.error("delete file data error!", e);
        }
    }

}
