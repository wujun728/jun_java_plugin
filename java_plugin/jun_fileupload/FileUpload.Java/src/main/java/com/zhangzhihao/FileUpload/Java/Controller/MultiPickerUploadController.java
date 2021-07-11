package com.zhangzhihao.FileUpload.Java.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 张志豪 on 2016/12/5 0005.
 * 多个文件选择器上传文件，一个选择器对应一个文件
 */
@Controller
@RequestMapping("/MultiPickerUpload")
public class MultiPickerUploadController {

    @GetMapping(value = "/Index")
    public String Index() {
        return "MultiPicker/Index";
    }

    @PostMapping("/")
    public ResponseEntity<Void> fileUpload(@RequestParam("type") String type,
                                           @RequestParam("name") String name,
                                           @RequestParam("file") MultipartFile file) throws Exception {

        switch (type) {
            case "researchReport": //研究报告
                //save file
                break;
            case "researchReportStuff": //研究报告支撑材料(限PDF)
                //save file
                break;
            case "applyReport": //应用报告
                //save file
                break;
            case "applyReportStuff": //应用报告支撑材料(限PDF)
                //save file
                break;
            default:
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
