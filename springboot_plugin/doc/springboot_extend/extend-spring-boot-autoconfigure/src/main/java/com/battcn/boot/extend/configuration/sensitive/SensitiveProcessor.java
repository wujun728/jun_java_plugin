package com.battcn.boot.extend.configuration.sensitive;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Levin
 * @since 2019/1/2 0002
 */
@Slf4j
public class SensitiveProcessor {

    private SensitiveProcessor() {
    }

    private static class LazySensitiveHolder {
        private static final SensitiveProcessor SENSITIVE_CACHE = new SensitiveProcessor();
        private static final Set<String> DICT_CACHE = new HashSet<>();
    }

    public static SensitiveProcessor getInstance() {
        return LazySensitiveHolder.SENSITIVE_CACHE;
    }

    public void loadWordDict(String dictFilePath, Charset charset) throws IOException {
        final Path path = Paths.get(dictFilePath);
        List<String> lines = Files.readAllLines(path, charset);
        for (String line : lines) {
            if (log.isDebugEnabled()) {
                log.debug("[装载字典] - [{}]", line);
            }
            add(line);
        }
    }

    public Set<String> all() {
        return LazySensitiveHolder.DICT_CACHE;
    }

    public boolean add(String keyWord) {
        return LazySensitiveHolder.DICT_CACHE.add(keyWord);
    }

    public boolean contains(String keyWord) {
        return LazySensitiveHolder.DICT_CACHE.contains(keyWord);
    }

    public boolean remove(String keyWord) {
        return LazySensitiveHolder.DICT_CACHE.remove(keyWord);
    }


    public String format(String text, String replacement) {
        if (text == null || text.length() == 0) {
            return text;
        }
        final Set<String> all = all();
        for (String key : all) {
            text = text.replaceAll(key, replacement);
        }
        return text;
    }
}
