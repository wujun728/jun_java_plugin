package io.github.wujun728.file.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.wujun728.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Controller
@RequestMapping(value = "/file")
public class FileControl {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/Login")
    public String index() {
        return "login";
    }

    //http://localhost:8080/public//Qrcode?url=baidu.com&name=wujun
    @RequestMapping(value = "/Qrcode")
    public void test(HttpServletResponse response,HttpServletRequest request) throws IOException, WriterException {
        //生成二维码
        int width = 150;
        int height = 150;
        String url = request.getParameter("url");
        String name = request.getParameter("name");
        System.out.println(url+"&name="+name);
        String format = "png";
        String content = url+"&name="+name;
        ServletOutputStream out = response.getOutputStream();
        Map<EncodeHintType,Object> config = new HashMap<>();
        config.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        config.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        config.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,width,height,config);
        MatrixToImageWriter.writeToStream(bitMatrix,format,out);
        System.out.println("二维码生成完毕，已经输出到页面中。");
    }

//    @RequestMapping(value = "/register")
//    @ResponseBody
//    public String register(HttpServletRequest request) {
////        获取账号密码
//        String name = request.getParameter("name");
//        String pass = request.getParameter("pass");
//        //创建一个用户对象
//        User user = new User(name, pass);
//        String s = registerService.addOne(user);
//        return s;
//    }

//    @RequestMapping(value = "/login")
//    @ResponseBody
//    public String login(HttpServletRequest request) {
//        //        获取账号密码
//        String name = request.getParameter("name");
//        String pass = request.getParameter("pass");
//        User user = new User(name, pass);
//        String s = loginService.loginService(user);
//        return s;
//    }


    //文件上传
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(HttpServletRequest request, HttpServletResponse response) {
        String s = fileService.uploadFile(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("result", s);
        //转为json
        JSONObject jsonObject = new JSONObject(map);
        String res = jsonObject.toString();
        return res;
    }

    //文件列表
    @RequestMapping(value = "/filedir")
    @ResponseBody
    public JSONObject filedir(HttpServletRequest request) {
        String res = fileService.fileList(request);
        JSONObject jsonObject = JSON.parseObject(res);
        jsonObject.remove("message");
        return jsonObject;
    }
    @RequestMapping(value = "/filelist")
    @ResponseBody
    public JSONObject filelist(HttpServletRequest request) {
        String res = fileService.fileList(request);
        JSONObject jsonObject = JSON.parseObject(res);
        jsonObject.remove("message");
        return jsonObject;
    }
//    文件下载
    @RequestMapping(value = "/filedown")
    public void filedown(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        fileService.fileDown(request, response);
    }
    //文件删除
    @RequestMapping(value = "/filedel")
    @ResponseBody
    public String filedel(HttpServletRequest request,HttpServletResponse response){
        String msg = "";
        msg = fileService.fileDel(request,response);
        return msg;

    }
}
