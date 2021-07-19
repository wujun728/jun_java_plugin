package com.jun.plugin.picturemanage.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/4 11:38
 */
@Data
public class FileResultVo {

    private List<FileResult> files;

    private List<PathPackage> currentPath;


    @Data
    public static class PathPackage {
        private String name;
        private String path;

        public PathPackage(String name, String path) {
            this.name = name;
            this.path = path;
        }
    }
}
