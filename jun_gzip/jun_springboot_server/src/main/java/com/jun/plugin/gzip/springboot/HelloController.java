package com.jun.plugin.gzip.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.gzip.springboot.compress.CompressUtils;
import com.jun.plugin.gzip.springboot.encrypt.AESUtils;

/**
 * @Author:Yolanda
 * @Date: 2020/5/7 13:41
 */

@RestController
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(String data){

        return "Hello, Yolanda!我在服务端接收到了你发送过来的数据，解密解压后是:"
                + CompressUtils.decompressToStr(AESUtils.decrypt(data));
    }
}
