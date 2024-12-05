package io.github.wujun728.rest.entity;

import cn.hutool.core.io.FileUtil;
import lombok.Data;

import java.io.File;

@Data
public class ApiDataSource {
    String id;
    String name;
    String url;
    String username;
    String password;
    String type;
    String driver;

    public static void main(String[] args) {
        File[] files = FileUtil.ls("D:\\byk-engine");
        for (File file : files) {
//            System.out.println(file.getAbsoluteFile());
            System.out.println(file.getAbsolutePath());
            System.out.println("             "+FileUtil.getName(file));

        }
    }
}
