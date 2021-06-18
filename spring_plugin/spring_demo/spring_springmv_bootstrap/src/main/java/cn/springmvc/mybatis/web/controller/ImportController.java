package cn.springmvc.mybatis.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.springmvc.mybatis.common.constants.Constants;
import cn.springmvc.mybatis.common.utils.FileUtil;
import cn.springmvc.mybatis.service.simple.ImportExcelService;

/**
 * @author Vincent.wang
 *
 */
@Controller
@RequestMapping("import")
public class ImportController {

    private static final Logger log = LoggerFactory.getLogger(ImportController.class);

    @Autowired
    private ImportExcelService importExcelService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/import/index";
    }

    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public String springupload(@RequestParam("uploadfile") MultipartFile[] ajaxuploadfile, HttpServletRequest request, HttpServletResponse response, Model model) {
        String realPath = Constants.PATH;
        FileUtil.isDirectory(realPath, true);
        response.setContentType("text/plain; charset=UTF-8");
        String originalFilename = null;
        for (MultipartFile file : ajaxuploadfile) {
            if (file.isEmpty()) {
                return "/import/error";
            } else {
                originalFilename = file.getOriginalFilename();
                log.warn("# originalFilename=[{}] , name=[{}] , size=[{}] , contentType=[{}] ", originalFilename, file.getName(), file.getSize(), file.getContentType());
                try {
                    // FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, originalFilename));
                    importExcelService.toImport(file.getInputStream());
                } catch (IOException e) {
                    log.error("# import fail . error message={}", e.getMessage());
                    e.printStackTrace();
                    return "/import/error";
                }
            }
        }
        model.addAttribute("msg", "上传成功！");
        return "/import/index";
    }

    @RequestMapping(value = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxupload(@RequestParam("uploadfile") MultipartFile[] uploadfile, HttpServletRequest request, HttpServletResponse response) {
        String realPath = Constants.PATH;
        FileUtil.isDirectory(realPath, true);
        response.setContentType("text/plain; charset=UTF-8");
        String originalFilename = null;
        for (MultipartFile file : uploadfile) {
            if (file.isEmpty()) {
                return "导入失败";
            } else {
                originalFilename = file.getOriginalFilename();
                log.warn("# originalFilename=[{}] , name=[{}] , size=[{}] , contentType=[{}] ", originalFilename, file.getName(), file.getSize(), file.getContentType());
                try {
                    importExcelService.toImport(file.getInputStream());
                } catch (IOException e) {
                    log.error("# import fail . error message={}", e.getMessage());
                    e.printStackTrace();
                    return "/import/error";
                }
            }
        }
        return "导入成功";
    }
}
