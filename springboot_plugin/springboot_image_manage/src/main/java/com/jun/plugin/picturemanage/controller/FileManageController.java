package com.jun.plugin.picturemanage.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jun.plugin.picturemanage.entity.FileResultVo;
import com.jun.plugin.picturemanage.util.FileManage;
import com.jun.plugin.picturemanage.util.JsonResult;

import java.util.List;
import java.util.Map;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/4 15:16
 */
@RestController
@RequestMapping("/api/file")
public class FileManageController {

    @Autowired
    private FileManage manage;

    /**
     * 打开一个文件夹
     *
     * @param path
     * @return
     */
    @PostMapping("/list")
    public JsonResult fileList(String path) {
        FileResultVo resultVo = manage.openDirectory(path);
        return JsonResult.success(resultVo);
    }

    /**
     * 计算一个文件夹的大小
     *
     * @param path
     * @return
     */
    @PostMapping("/computerFolderSize")
    public JsonResult computerFolderSize(String path) {
        if (StringUtils.isBlank(path)) {
            return JsonResult.errorForEmpty();
        }
        return JsonResult.success(manage.computerFolderSize(path));
    }


    /**
     * 创建一个文件夹
     *
     * @param path
     * @param folderName
     * @return
     */
    @PostMapping("/createFolder")
    public JsonResult createFolder(String path, String folderName) {
        if (StringUtils.isBlank(path) || StringUtils.isBlank(folderName)) {
            return JsonResult.errorForEmpty();
        }
        return manage.createFolder(folderName, path) ? JsonResult.actionSuccess() : JsonResult.actionFailure();
    }


    /**
     * 上传一个文件
     *
     * @param path
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public JsonResult upload(String path, MultipartFile file) {
        if (StringUtils.isBlank(path) || file == null || file.isEmpty()) {
            return JsonResult.errorForEmpty();
        }

        boolean state = manage.uploadFile(path, file);
        return state ? JsonResult.actionSuccess() : JsonResult.actionFailure();
    }

    /**
     * 删除一个文件
     *
     * @param pathList
     * @return
     */
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody List<String> pathList) {
        if (CollectionUtils.isEmpty(pathList)) {
            return JsonResult.errorForEmpty();
        }
        int count = 0;
        for (String path : pathList) {
            if (manage.delete(path)) {
                count++;
            }
        }
        return JsonResult.success(count + "个文件删除成功");
    }

    /**
     * 重命名文件
     *
     * @param path
     * @param name
     * @return
     */
    @PostMapping("/rename")
    public JsonResult rename(String path, String name) {
        if (StringUtils.isBlank(path) || StringUtils.isBlank(name)) {
            return JsonResult.errorForEmpty();
        }
        boolean state = manage.rename(name, path);
        return state ? JsonResult.actionSuccess() : JsonResult.actionFailure();
    }

    /**
     * 移动文件
     *
     * @param params
     * @return
     */
    @PostMapping("/move")
    public JsonResult moveFile(@RequestBody Map<String, Object> params) {
        List<String> files = (List<String>) MapUtils.getObject(params, "files");
        String targetPath = MapUtils.getString(params, "targetPath");
        if (StringUtils.isBlank(targetPath) || CollectionUtils.isEmpty(files)) {
            return JsonResult.errorForEmpty();
        }
        return manage.moveFile(files, targetPath);
    }

}
