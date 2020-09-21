package com.yisin.dbc.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class IniReader {
    private static Logger log = Logger.getLogger(IniReader.class);

    private static IniReader reader = null;
    public static HashMap<String, HashMap<String, HashMap<String, String>>> sections = new HashMap<String, HashMap<String, HashMap<String, String>>>();
    private String currentSecion, currTag;
    private HashMap<String, HashMap<String, String>> current;
    private HashMap<String, String> cfgMap;
    private static String fileName = "/dbcompare-cfg.ini";
    public static String file = "/dbcompare-cfg.ini";
    public static String baseDir = "/";

    private IniReader() {
    }

    private IniReader(String filename) {
        file = filename;
        init();
    }

    public static IniReader getIniReader() {
        if (reader == null) {
            baseDir = Utililies.getBaseDir();
            reader = new IniReader(baseDir + fileName);
        }
        return reader;
    }

    private void init() {
        try {
            FileUtils.createDir(baseDir);
            FileUtils.createFile(file);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            read(reader);
            reader.close();
        } catch (Exception e) {
            log.error("初始化配置失败", e);
        }
    }

    private void read(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
    }

    private void parseLine(String line) {
        line = line.trim();
        if (line.matches("\\[.*\\]")) {
            currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
            current = new HashMap<String, HashMap<String, String>>();
            sections.put(currentSecion, current);
            cfgMap = new HashMap<String, String>();
        } else if (line.matches("<.*>")) {
            currTag = line.replaceFirst("<(.*)>", "$1");
            cfgMap = new HashMap<String, String>();
            current.put(currTag, cfgMap);
        } else if (line.matches(".*=.*")) {
            if (cfgMap != null) {
                int i = line.indexOf('=');
                String name = line.substring(0, i);
                String value = line.substring(i + 1);
                cfgMap.put(name, value);
            }
        }
    }

    public HashMap<String, HashMap<String, String>> getConfig(String section) {
        HashMap<String, HashMap<String, String>> p = sections.get(section);
        if (p == null) {
            return null;
        }
        return p;
    }

    public HashMap<String, String> getConfig(String section, String tag) {
        HashMap<String, HashMap<String, String>> p = sections.get(section);
        if (p == null) {
            return null;
        }
        HashMap<String, String> valMap = p.get(tag);
        if (valMap == null) {
            return null;
        }
        return valMap;
    }

    public String getValue(String section, String tag, String name) {
        HashMap<String, String> valMap = getConfig(section, tag);
        if (valMap == null) {
            return null;
        }
        return valMap.get(name);
    }

    public IniReader putValue(String section, String tag, String name, String value) {
        HashMap<String, HashMap<String, String>> map = sections.get(section);
        HashMap<String, String> cfg = null;
        if (map == null) {
            map = new HashMap<String, HashMap<String, String>>();
            sections.put(section, map);
            cfg = new HashMap<String, String>();
            map.put(tag, cfg);
        } else {
            cfg = map.get(tag);
            if (cfg == null) {
                cfg = new HashMap<String, String>();
                map.put(tag, cfg);
            }
        }
        cfg.put(name, value);
        return this;
    }

    /**
     * 获取正激活使用的配置
     * 
     * @param section
     * @return
     */
    public HashMap<String, String> getActiveCfg(String section) {
        HashMap<String, HashMap<String, String>> map = sections.get(section);
        HashMap<String, String> result = null;
        if (map != null) {
            for (Map.Entry<String, HashMap<String, String>> ent : map.entrySet()) {
                result = ent.getValue();
                if(result != null){
                    break;
                }
            }
        }
        return result;
    }
    
    public IniReader save() {
        try {
            StringBuffer sb = new StringBuffer();
            HashMap<String, HashMap<String, String>> currMap = null;
            HashMap<String, String> cfgMap = null;
            for (Map.Entry<String, HashMap<String, HashMap<String, String>>> entry : sections.entrySet()) {
                sb.append("[").append(entry.getKey()).append("]\r\n");
                currMap = entry.getValue();

                if (currMap != null && currMap.entrySet().size() > 0) {
                    for (Map.Entry<String, HashMap<String, String>> ent : currMap.entrySet()) {
                        sb.append("<").append(ent.getKey()).append(">\r\n");
                        cfgMap = ent.getValue();

                        for (Map.Entry<String, String> e : cfgMap.entrySet()) {
                            sb.append(e.getKey() + "=").append(e.getValue() + "\r\n");
                        }
                    }
                }
                sb.append("\r\n");
            }
            OutputStream fos = new FileOutputStream(file);// 加载读取文件流
            fos.write(sb.toString().getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public static void main(String[] args) {
        IniReader reader = IniReader.getIniReader();
        System.out.println(IniReader.sections);
    }

}
