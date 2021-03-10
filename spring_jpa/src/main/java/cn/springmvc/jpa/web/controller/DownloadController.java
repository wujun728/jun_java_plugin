package cn.springmvc.jpa.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.springmvc.jpa.common.constants.Constants;
import cn.springmvc.jpa.common.utils.FileUtil;
import cn.springmvc.jpa.common.utils.ZipUtil;

/**
 * @author Vincent.wang
 *
 */
@Controller
@RequestMapping(value = "download")
public class DownloadController {

    private static final Logger log = LoggerFactory.getLogger(DownloadController.class);

    public static void getFiles(File path, List<File> fileMap) {
        if (!path.exists()) {
            log.error("## file is null.");
        } else {
            if (path.isFile()) {
                fileMap.add(path);
            } else {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    getFiles(files[i], fileMap);
                }
            }
        }
    }

    @RequestMapping(value = "/zip", method = RequestMethod.GET)
    public String download(Model model) {
        String realPath = Constants.PATH;
        FileUtil.isDirectory(realPath, true);
        File file = new File(realPath);
        List<File> files = new ArrayList<File>();
        getFiles(file, files);
        model.addAttribute("files", files);
        // 加载这个目录下所有的文件
        return "download/zip";
    }

    @RequestMapping(value = "/zip", method = RequestMethod.POST)
    public ResponseEntity<byte[]> downloadzip(@RequestParam("zippath") String zippath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File(zippath);
        String zipName = file.getName();
        zipName = zipName.substring(0, zipName.lastIndexOf(".")) + ".zip";

        File zip = new File(file.getParent() + "/" + zipName);
        List<File> paths = new ArrayList<File>();
        paths.add(file);
        ZipUtil.compress(paths, zip.getPath(), false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(zipName.getBytes("UTF-8"), "ISO8859-1"));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(zip), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity<byte[]> downloadfile(@RequestParam("filepath") String filepath) throws IOException {
        log.warn("# download filepath=[{}]", filepath);
        File file = new File(filepath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(file.getName().getBytes("UTF-8"), "ISO8859-1"));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

}
