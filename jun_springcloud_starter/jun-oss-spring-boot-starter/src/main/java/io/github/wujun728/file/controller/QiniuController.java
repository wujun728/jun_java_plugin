package io.github.wujun728.file.controller;

import java.io.File;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.wujun728.file.utils.FileUtils;
import io.github.wujun728.file.utils.QiniuUtils;
import io.github.wujun728.file.utils.ExecuteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "")
public class QiniuController {

    private static Logger logger = LoggerFactory.getLogger(QiniuController.class);


    @RequestMapping("qiniuCommon")
    private String qiniuCommon(HttpServletRequest request){
        return "qiniuCommon";
    }


    /**
     * @param request
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/qiniuUpload", method = RequestMethod.POST)
    @ResponseBody
    public String qiniuUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("imagefile") MultipartFile multipartFile) {
        ExecuteResult<String> executeResult = new ExecuteResult<String>();

        QiniuUtils QiniuUtils = new QiniuUtils();
        try {
            /**
             * 上传文件扩展名
             */
            String filenameExtension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."), multipartFile.getOriginalFilename().length());

            /**
             * MultipartFile 转 file 类型
             */
            File file = FileUtils.multipartToFile(multipartFile);

            /**
             * 七牛云文件上传 服务  file文件 以及 文件扩展名
             */
            executeResult = QiniuUtils.uploadFile2(file, filenameExtension);
            if (!executeResult.isSuccess()) {
                return "失败" + executeResult.getErrorMessages();
            }

        } catch (AuthException e) {
            logger.error("AuthException", e);
        }

        return executeResult.getResult();
    }

}
