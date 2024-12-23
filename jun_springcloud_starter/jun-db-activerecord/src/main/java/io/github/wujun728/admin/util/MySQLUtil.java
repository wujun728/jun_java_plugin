package io.github.wujun728.admin.util;

import cn.hutool.core.io.IoUtil;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class MySQLUtil {
    private static final Set<String> keywords = new HashSet<>();
    static {
        InputStream in = MySQLUtil.class.getClassLoader().getResourceAsStream("mysql_key_word.txt");
        InputStreamReader reader = new InputStreamReader(new BufferedInputStream(in));
        IoUtil.readLines(reader,keywords);
        IoUtil.close(reader);
        IoUtil.close(in);
    }

    public static boolean isKeyword(String name){
        return keywords.contains(name.toUpperCase());
    }
}
