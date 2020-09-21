package com.yisin.dbc.util;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.swing.SwingUtilities;

public class Utililies {
    public static void setToplevelLocation(Component toplevel, Component component, int relativePosition) {
        Rectangle compBounds = component.getBounds();

        Point p = new Point();
        SwingUtilities.convertPointToScreen(p, component);
        int x;
        int y;
        switch (relativePosition) {
        case 1:
            x = p.x + compBounds.width / 2 - toplevel.getWidth() / 2;
            y = p.y - toplevel.getHeight();
            break;
        case 3:
            x = p.x + compBounds.width;
            y = p.y + compBounds.height / 2 - toplevel.getHeight() / 2;
            break;
        case 5:
            x = p.x + compBounds.width / 2 - toplevel.getWidth() / 2;
            y = p.y + compBounds.height;
            break;
        case 7:
            x = p.x - toplevel.getWidth();
            y = p.y + compBounds.height / 2 - toplevel.getHeight() / 2;
            break;
        case 2:
            x = p.x + compBounds.width;
            y = p.y - toplevel.getHeight();
            break;
        case 8:
            x = p.x - toplevel.getWidth();
            y = p.y - toplevel.getHeight();
            break;
        case 4:
            x = p.x + compBounds.width;
            y = p.y + compBounds.height;
            break;
        case 6:
            x = p.x - toplevel.getWidth();
            y = p.y + compBounds.height;
            break;
        case 0:
        default:
            x = p.x + compBounds.width / 2 - toplevel.getWidth() / 2;
            y = p.y + compBounds.height / 2 - toplevel.getHeight() / 2;
        }
        toplevel.setLocation(x, y);
    }

    public static String tableToEntity(String table) {
        String entity = "";
        if (table != null && table.length() > 1) {
            if (table.indexOf("_") != -1) {
                String[] strs = table.split("_");
                for (int i = 0; i < strs.length; i++) {
                    if (strs[i] != null && strs[i].length() > 0) {
                        entity += CommonUtils.firstCharToUpperCase(strs[i]);
                    }
                }
            } else {
                entity = CommonUtils.firstCharToUpperCase(table);
            }
            if (entity.matches("[A-Z]+")) {
                entity = CommonUtils.firstCharToUpperCase(entity.toLowerCase());
            }
        }
        return entity;
    }

    public static String entityToTable(String entity) {
        String table = null;
        if (entity != null && entity.length() > 1) {
            table = entity.replaceAll("[A-Z]", "_$0").toLowerCase();
        }
        return table;
    }

    public static String feildToColumn(String feild) {
        return entityToTable(feild);
    }

    public static String columnToFeild(String column) {
        return CommonUtils.firstCharToLowerCase(tableToEntity(column));
    }

    /**
     * 包路径转文件夹路径
     * 
     * @param entity
     * @return
     */
    public static String packageToDir(String packages) {
        String dir = packages;
        if (packages != null && packages.length() > 1) {
            dir = packages.replaceAll("[.]", "/");
        }
        return dir;
    }

    /**
     * 获取类的包路径的父级包
     * 
     * @param entity
     * @return
     */
    public static String getSuperPackage(String packages) {
        String pack = packages;
        if (packages != null && packages.indexOf(".") != -1) {
            pack = packages.substring(0, packages.lastIndexOf("."));
        }
        return pack;
    }

    public static String getRealPackage(String packages) {
        String pack = packages;
        if (packages != null && packages.indexOf(".") != -1) {
            if (packages.startsWith("main.java.")) {
                pack = packages.substring(10);
            }
        }
        return pack;
    }

    public static String readResourceFile(String file) {
        String content = "";
        try {
            InputStream is = Utililies.class.getClassLoader().getResourceAsStream(file);
            byte[] b = null;
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = is.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            content = new String(b, "utf-8");
            baos.flush();
            baos.close();
            is.close();
        } catch (Exception e) {

        }
        return content;
    }

    public static String parseTemplate(String content, String key, String value) {
        try {
            if (content != null) {
                content = content.replaceAll("\\{" + key + "\\}", value);
            }
        } catch (Exception e) {
        }
        return content;
    }

    public static void writeContentToFile(String file, String content) throws Exception {
        FileOutputStream fos = new FileOutputStream(new File(file));
        fos.write(content.getBytes("UTF-8"));
        fos.flush();
        fos.close();
    }

    public static String parseFilePath(String dir, String file) {
        if (!dir.endsWith("/")) {
            dir = dir + "/";
        }
        if (file.startsWith("/")) {
            file = file.substring(1);
        }
        String filePath = dir + file;
        String dirPath = FileUtils.getFileDir(filePath);
        if(dirPath.endsWith("/impl/")){
        	dirPath = dirPath.replace("/impl/", "/");
        }
        FileUtils.createDir(dirPath);
        return filePath;
    }

    public static String getJavaType(String jdbcType) {
        String javaType = Consts.OBJECT;
        if (jdbcType.equalsIgnoreCase("varchar") || jdbcType.equalsIgnoreCase("char")) {
            javaType = Consts.STRING;
        } else if (jdbcType.equalsIgnoreCase("int") || jdbcType.equalsIgnoreCase("integer")) {
            javaType = Consts.INTEGER;
        } else if (jdbcType.equalsIgnoreCase("long") || jdbcType.equalsIgnoreCase("bigint")) {
            javaType = Consts.LONG;
        } else if (jdbcType.equalsIgnoreCase("float") || jdbcType.equalsIgnoreCase("number")) {
            javaType = Consts.FLOAT;
        } else if (jdbcType.equalsIgnoreCase("double")) {
            javaType = Consts.DOUBLE;
        } else if (jdbcType.equalsIgnoreCase("decimal")) {
            javaType = Consts.BIGDECIMAL;
        } else if (jdbcType.equalsIgnoreCase("date")) {
            javaType = Consts.DATE;
        } else if (jdbcType.equalsIgnoreCase("timestamp") || jdbcType.equalsIgnoreCase("time")) {
            javaType = Consts.TIMESTAMP;
        } else if (jdbcType.equalsIgnoreCase("blob")) {
            javaType = Consts.LBYTE;
        }
        return javaType;
    }

    public static String getVarJavaType(String jdbcType) {
        String javaType = Consts.PRIVATE_OBJECT;
        if (jdbcType.equalsIgnoreCase("varchar") || jdbcType.equalsIgnoreCase("char")) {
            javaType = Consts.PRIVATE_STRING;
        } else if (jdbcType.equalsIgnoreCase("int") || jdbcType.equalsIgnoreCase("integer")) {
            javaType = Consts.PRIVATE_INTEGER;
        } else if (jdbcType.equalsIgnoreCase("long") || jdbcType.equalsIgnoreCase("bigint")) {
            javaType = Consts.PRIVATE_LONG;
        } else if (jdbcType.equalsIgnoreCase("float") || jdbcType.equalsIgnoreCase("number")) {
            javaType = Consts.PRIVATE_FLOAT;
        } else if (jdbcType.equalsIgnoreCase("double")) {
            javaType = Consts.PRIVATE_DOUBLE;
        } else if(jdbcType.equalsIgnoreCase("decimal")){
        	javaType = Consts.PRIVATE_BIGDECIMAL;
        } else if (jdbcType.equalsIgnoreCase("date")) {
            javaType = Consts.PRIVATE_DATE;
        } else if (jdbcType.equalsIgnoreCase("timestamp") || jdbcType.equalsIgnoreCase("time")) {
            javaType = Consts.PRIVATE_TIMESTAMP;
        } else if (jdbcType.equalsIgnoreCase("blob")) {
            javaType = Consts.PRIVATE_LBYTE;
        }
        return javaType;
    }

    public static String getAttrDeclare(String javaType, String attr, Object value) {
        String declare = "";
        if (javaType.indexOf(" String") != -1) {
            declare = javaType + attr;
            if (value != null) {
                declare += " = \"" + value.toString() + "\"";
            }
        } else if (javaType.indexOf(" Integer") != -1) {
            declare = javaType + attr;
            if (value != null) {
                declare += " = " + value.toString();
            }
        } else if (javaType.indexOf(" Long") != -1) {
            declare = javaType + attr;
            if (value != null) {
                declare += " = " + value.toString() + "l";
            }
        } else if (javaType.indexOf(" Float") != -1) {
            declare = javaType + attr;
            if (value != null) {
                declare += " = " + value.toString() + "f";
            }
        } else if (javaType.indexOf(" Double") != -1) {
            declare = javaType + attr;
            if (value != null) {
                declare += " = " + value.toString() + "d";
            }
        } else if (javaType.indexOf(" Date") != -1) {
            declare = javaType + attr;
            if (value != null) {
                declare += " = new Date()";
            }
        } else if (javaType.indexOf(" Timestamp") != -1) {
            declare = javaType + attr;
            if (value != null) {
                declare += " = new Timestamp(new Date().getTime())";
            }
        } else {
            declare = javaType + attr;
        }
        return declare + ";";
    }

    public static void copyFormJar(Utililies wm, String fileUrl, String dest) {
        try {
            // 返回读取指定资源的输入流
            InputStream in = wm.getClass().getResourceAsStream(fileUrl);
            int BUFF_SIZE = 100000;
            byte[] buffer = new byte[BUFF_SIZE];
            OutputStream out = null;
            try {
                out = new FileOutputStream(dest);
                while (true) {
                    synchronized (buffer) {
                        int amountRead = in.read(buffer);
                        if (amountRead == -1) {
                            break;
                        }
                        out.write(buffer, 0, amountRead);
                    }
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public static void byteToString(byte[] byt) {
        String s = new BASE64Encoder().encode(byt);
        System.out.print(s);
    }*/

    public static String getJarDir(String url) {
        if (url != null && url.startsWith("file")) {
            url = url.substring(6);
            // System.out.println("-getJarDir-----------" + url);
            url = url.substring(0, url.indexOf("!"));
            // System.out.println("-getJarDir-----------" + url);
            File jar = new File(url);
            url = jar.getParentFile().getPath();
            // System.out.println("-getJarDir-----------" + url);
        } else {
            url = url.substring(0, url.lastIndexOf("/"));
        }
        return url;
    }
    
    public static String getBaseDir(){
        Utililies wm = new Utililies();
        String url = wm.getClass().getResource("/log4j.xml").getPath();
        url = Utililies.StringDecode(url);
        url = getJarDir(url);
        if(url.startsWith("/") && url.indexOf(":") != -1){
            url = url.substring(1);
        }
        url = url.replaceAll("\\\\", "/");
        return url;
    }
    
    public static String StringDecode(String param){
        if (param != null && !param.trim().equals("")) {
            try {
                param = URLDecoder.decode(param, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return param;
    }

    public static void main(String[] args) {
        String content = readResourceFile("template/action.tlp");
        content = Utililies.parseTemplate(content, "package", "com.byph.community.modal");
        System.out.println(content);
    }
}
