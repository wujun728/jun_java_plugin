package com.jun.plugin.utils.log;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文本记录日志
 * 
 * @author Ming
 * @update 2016-11-30 17:13:00 （1，使用子线程写入文本日志；2，添加异常日志记录）
 * 
 */
public class Logger {

    private static String log_path = getBasePath();// 日志保存路径
    private static String log_name = "yxt_log";// 日志文件名（前部分）
    private static boolean console_out = true;// 日志是否输出到控制台
    /*
     * yyyy-MM: 每个月更新一个log日志 yyyy-ww: 每个星期更新一个log日志 yyyy-MM-dd: 每天更新一个log日志
     * yyyy-MM-dd-a: 每天的午夜和正午更新一个log日志 yyyy-MM-dd-HH: 每小时更新一个log日志
     * yyyy-MM-dd-HH-mm: 每分钟更新一个log日志
     */
    private static String update_hz = "yyyy-MM";// 更新日志的频率，每月更新一次
    private static long max_log_size = 1024 * 1024 * 10;// 单个日志文件最大大小 10M

    public static void debug(String msg) {
        runWrite(msg, log_path, log_name + "_debug");
    }

    public static void info(String msg) {
        runWrite(msg, log_path, log_name + "_info");
    }

    public static void error(String msg) {
        runWrite(msg, log_path, log_name + "_error");
    }

    public static void exception(Exception e) {
        String errorMessage = e.getMessage() + "";
        StackTraceElement[] eArray = e.getCause().getStackTrace();
        for (int i = 0; i < eArray.length; i++) {
            String className = e.getCause().getStackTrace()[i].getClassName();
            String MethodName = e.getCause().getStackTrace()[i].getMethodName();
            int LineNumber = e.getCause().getStackTrace()[i].getLineNumber();
            errorMessage = errorMessage + "\n\t---" + className + "." + MethodName + ",\tline:" + LineNumber;
        }
        logResult(errorMessage, log_path, log_name + "_exception");
    }

    /**
     * 日志根目录
     * 
     * @return
     */
    public static String getBasePath() {
        String s = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        s = s.substring(0, s.indexOf("WEB-INF")) + "log" + File.separator;
        return s;
    }

    /**
     * 写日志
     * 
     * @param sWord
     *            要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        runWrite(sWord, log_path, log_name);
    }

    public static void logResult(String sWord, String logPath, String logName) {
        FileWriter writer = null;
        try {
            File dir = new File(logPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
            File f = new File(logPath + logName + "_" + new SimpleDateFormat(update_hz).format(new Date()) + ".txt");
            if (!f.exists()) {
                f.createNewFile();
                sWord = "AllMing 日志\r\n" + "[" + dt + "]\t" + sWord;
            } else {
                long logSize = f.length();
                // 文件大小超过10M，备份
                if (logSize >= max_log_size) {
                    String backLogName = logPath + logName
                            + new SimpleDateFormat("_yyyy-MM-dd.HHmmss.SSS").format(new Date()) + ".txt";
                    f.renameTo(new File(backLogName));
                }
            }
            writer = new FileWriter(f, true);
            writer.write("[" + dt + "]\t" + sWord + "\r\n");
            if (console_out) {
                System.out.println("[" + dt + "]\t" + sWord);
            }
        } catch (Exception e) {
            System.out.println("记录日志异常：" + e.toString());
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void runWrite(final String sWord,final String logPath,final String logName) {
            new Thread() {
                public void run() {
                    logResult(sWord, logPath, logName);
                }
            }.start();;
    }
    
    public static void main(String[] args) {
        for (int i = 0; i <1000; i++) {
            error(""+i);
        }
    }
}
