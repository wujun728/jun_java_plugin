package cn.springmvc.mybatis.common.utils;

import java.io.Reader;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {

    public static String convertNullToString(String str) {
        return str == null ? "" : str;
    }

    public static Date convertStringToDate(String value) throws Exception {
        return convertStringToDate(value, "yyyy-MM-dd");
    }

    public static Date convertStringToDate(String value, String patten) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (value == null)
            return null;
        if (value.trim().length() == 0)
            return null;
        try {
            return df.parse(value);
        } catch (Exception ex) {
        }
        throw new Exception("输入的日期类型不合乎yyyy/MM/dd" + value.getClass());
    }

    public static String convertDateToString(Date value) {
        return convertDateToString(value, "yyyy-MM-dd");
    }

    public static String convertDateToString(Date value, String patten) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (value == null)
            return null;
        return df.format(value);
    }

    public static String getMetaType(String strFileExce) {
        if (strFileExce.equals("asf")) {
            return "video/x-ms-asf";
        }
        if (strFileExce.equals("avi")) {
            return "video/avi";
        }
        if (strFileExce.equals("doc")) {
            return "application/msword";
        }
        if (strFileExce.equals("zip")) {
            return "application/zip";
        }
        if (strFileExce.equals("xls")) {
            return "application/vnd.ms-excel";
        }
        if (strFileExce.equals("gif")) {
            return "image/gif";
        }
        if ((strFileExce.equals("jpeg")) || (strFileExce.equals("jpg"))) {
            return "image/jpeg";
        }
        if (strFileExce.equals("wav")) {
            return "audio/wav";
        }
        if (strFileExce.equals("mp3")) {
            return "audio/mpeg3";
        }
        if (strFileExce.equals("mpeg")) {
            return "video/mpeg";
        }
        if (strFileExce.equals("rtf")) {
            return "application/rtf";
        }
        if ((strFileExce.equals("htm")) || (strFileExce.equals("html"))) {
            return "text/html";
        }
        if (strFileExce.equals("asp")) {
            return "text/asp";
        }

        return "application/octet-stream";
    }

    @SuppressWarnings("unused")
    public static String getClobValue(Clob c) {
        StringBuffer sb = new StringBuffer(1024);
        Reader instream = null;
        try {
            instream = c.getCharacterStream();
            char[] buffer = new char[(int) c.length()];
            int length = 0;
            while ((length = instream.read(buffer)) != -1) {
                sb.append(buffer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        try {
            if (instream != null)
                instream.close();
        } catch (Exception dx) {
            instream = null;
        }
        return sb.toString();
    }

}
