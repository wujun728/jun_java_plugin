package com.battcn.sample.extend.oss;

import com.battcn.boot.extend.configuration.crypto.encrypt.CryptoType;
import com.battcn.boot.extend.configuration.crypto.encrypt.CryptoUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CryptoApplicationTests {

    @Test
    public void contextLoads() {
        String s = CryptoUtils.encryptToString(CryptoType.AES, "123", "{\"id\":1,\"name\":\"213\"}", StandardCharsets.UTF_8);
        System.out.println(s);

    }

}
