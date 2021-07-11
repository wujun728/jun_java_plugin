package cn.kiiwii.framework.freemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zhong on 2016/11/10.
 */
@RestController
@Controller
@RequestMapping("/api")
public class ApiController {

    @RequestMapping("/doAction")
    @ResponseBody
    public Object newsList(HttpServletRequest request) {
        try {
            Map<String, MultipartFile> fileMap = null;
            MultipartHttpServletRequest multipartRequest = null;
            if (request instanceof MultipartHttpServletRequest) {
                multipartRequest = (MultipartHttpServletRequest) request;
            }
            if (multipartRequest != null)
                fileMap = multipartRequest.getFileMap();

            return "";
        } catch (Exception e) {
            return "";
        }
    }

}
