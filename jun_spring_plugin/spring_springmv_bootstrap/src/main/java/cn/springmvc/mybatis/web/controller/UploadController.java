package cn.springmvc.mybatis.web.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.springmvc.mybatis.common.constants.Constants;
import cn.springmvc.mybatis.common.utils.FileUtil;

/**
 * @author Vincent.wang
 *
 */
@Controller
@RequestMapping(value = "upload")
public class UploadController {

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping(value = "/spring", method = RequestMethod.GET)
    public String spring() {
        return "/upload/spring";
    }

    @RequestMapping(value = "/spring", method = RequestMethod.POST)
    public String springupload(@RequestParam("uploadfile") MultipartFile[] ajaxuploadfile, HttpServletRequest request, HttpServletResponse response, Model model) {
        String realPath = Constants.PATH;
        FileUtil.isDirectory(realPath, true);
        response.setContentType("text/plain; charset=UTF-8");
        String originalFilename = null;
        for (MultipartFile file : ajaxuploadfile) {
            if (file.isEmpty()) {
                return "upload/error";
            } else {
                originalFilename = file.getOriginalFilename();
                log.warn("# originalFilename=[{}] , name=[{}] , size=[{}] , contentType=[{}] ", originalFilename, file.getName(), file.getSize(), file.getContentType());
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, originalFilename));
                } catch (IOException e) {
                    log.error("# upload fail . error message={}", e.getMessage());
                    e.printStackTrace();
                    return "upload/error";
                }
            }
        }
        model.addAttribute("msg", "上传成功！");
        return "upload/spring";
    }

    @RequestMapping(value = "/ajax", method = RequestMethod.GET)
    public String ajax() {
        return "/upload/ajax";
    }

    @RequestMapping(value = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxupload(@RequestParam("ajaxuploadfile") MultipartFile[] ajaxuploadfile, HttpServletRequest request, HttpServletResponse response) {
        String realPath = Constants.PATH;
        FileUtil.isDirectory(realPath, true);
        response.setContentType("text/plain; charset=UTF-8");
        String originalFilename = null;
        for (MultipartFile file : ajaxuploadfile) {
            if (file.isEmpty()) {
                return "上传失败";
            } else {
                originalFilename = file.getOriginalFilename();
                log.warn("# originalFilename=[{}] , name=[{}] , size=[{}] , contentType=[{}] ", originalFilename, file.getName(), file.getSize(), file.getContentType());
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, originalFilename));
                } catch (IOException e) {
                    log.error("# upload fail . error message={}", e.getMessage());
                    e.printStackTrace();
                    return "上传失败";
                }
            }
        }
        return "上传成功";
    }

}
