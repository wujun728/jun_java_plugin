package com.jun.web;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
 
/**
 * 文件的相关帮助类
 */
public class FileHelp {
  private static final String TAG = "FileHelp";
 
  public static final String JPG = ".jpg";
  public static final String PNG = ".png";
 
  public static final String MP3 = ".mp3";
  public static final String MP4 = ".mp4";
  public static final String APK = ".apk";
 
  //上下文
//  private static Context context;
 
  /**
   * txt文本
   */
  public static int ISTXT = 0;
 
  private static String TXT = ".txt";
 
  /**
   * 文件删除
   */
  public static boolean deletfile(File file) {
    if (file.isDirectory()) {
      if (file.listFiles().length > 0) {
        for (File i : file.listFiles()) {
          deletfile(i);
        }
      } else {
        file.delete();
      }
    } else {
      file.delete();
    }
    file.delete();
    return true;
  }
 
  /**
   * 新建文件夹
   * 返回true 文件创建成功
   * 返回false 文件创建失败 ->文件存在
   * 返回true 文件创建成功,返回false 文件创建失败 (文件存在、权限不够）
   */
  public static boolean creatFile(String filename, String path) {
    File file = new File(path + File.separator + filename);
    if (file.exists()) {
      return false;
    } else {
      file.mkdir();
      return true;
    }
  }
 
  /**
   * 创建自定义文件类型文件
   * 随意为文件夹
   * 0 txt文本
   *
   * @return boolean
   * 返回true 文件创建成功,返回false 文件创建失败 (文件存在、权限不够）
   * *
   */
  public static boolean creatFile(String filename, String path, int type) {
    String ptr = path + File.separator + filename;
    File file;
    switch (type) {
      case 0:
        file = new File(ptr + TXT);
        break;
      default:
        file = new File(ptr);
        break;
    }
    if (file.exists()) {
      return false;
    } else {
      try {
        file.createNewFile();
        return true;
      } catch (IOException e) {
        return false;
      }
    }
  }
 
 
  /**
   * 文件重名
   *
   * @param name 新创建的文件名
   * @param file 创建文件的地方
   */
  public static boolean reName(String name, File file) {
    String pathStr = file.getParent() + File.separator + name;
    return file.renameTo(new File(pathStr));
  }
 
  /**
   * 文件复制
   *
   * @param oldFile  要被复制的文件
   * @param toNewPath 复制到的地方
   * @return boolean trun 复制成功，false 复制失败
   * *
   */
  public static boolean copeyFile(File oldFile, String toNewPath) {
    String newfilepath = toNewPath + File.separator + oldFile.getName();
 
    File temp = new File(newfilepath);
    //判断复制到的文件路径是否存在相对文件，如果存在，停止该操作
    if (temp.exists()) {
      return false;
    }
    //判断复制的文件类型是否是文件夹
    if (oldFile.isDirectory()) {
      temp.mkdir();
      for (File i : oldFile.listFiles()) {
        copeyFile(i, temp.getPath());
      }
    } else {
      //如果是文件，则进行管道复制
      try {
        //从文件流中创建管道
        FileInputStream fis = new FileInputStream(oldFile);
        FileChannel creatChannel = fis.getChannel();
        //在文件输出目标创建管道
        FileOutputStream fos = new FileOutputStream(newfilepath);
        FileChannel getChannel = fos.getChannel();
        //进行文件复制（管道对接）
        getChannel.transferFrom(creatChannel, 0, creatChannel.size());
 
        getChannel.close();
        creatChannel.close();
        fos.flush();
        fos.close();
        fis.close();
      } catch (Exception e) {
//        Log.i(TAG, "copey defeated,mebey file was existed");
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }
 
  /**
   * 文件剪切
   *
   * @param oldFile   要被剪切的文件
   * @param newFilePath 剪切到的地方
   * @return boolean trun 剪切成功，false 剪切失败
   */
  public static boolean cutFile(File oldFile, String newFilePath) {
    if (copeyFile(oldFile, newFilePath)) {
      oldFile.delete();
      return true;
    } else {
      return false;
    }
  }
 
 
  /**
   * 获取对应文件类型的问件集
   *
   * @param dir 文件夹
   * @param type 文件类型，格式".xxx"
   * @return List<file> 文件集合
   */
  public static List<File> getTheTypeFile(File dir, String type) {
    List<File> files = new ArrayList<File>();
    for (File i : dir.listFiles()) {
      String filesTyepe = getFileType(i);
      if (type.equals(filesTyepe)) {
        files.add(i);
      }
    }
    return files;
  }
 
  /**
   * 获取文件类型
   *
   * @param file 需要验证的文件
   * @return String 文件类型
   * 如:
   * 传入文件名为“test.txt”的文件
   * 返回 .txt
   * *
   */
  public static String getFileType(File file) {
    String fileName = file.getName();
    if (fileName.contains(".")) {
 
      String fileType = fileName.substring(fileName.lastIndexOf("."),
          fileName.length());
      return fileType;
    } else {
      return null;
    }
  }
 
 
  /**
   * 获取文件最后操作时间类
   *
   * @param file 需要查询的文件类
   * @return “yy/MM/dd HH:mm:ss”的数据字符串
   * 如：
   * 14/07/01 01:02:03
   */
  public static String getCreatTime(File file) {
    long time = file.lastModified();
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    String date = dateFormat.format(calendar.getTime());
    return date;
  }
 
}