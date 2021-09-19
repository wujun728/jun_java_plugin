import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
public class Base64Utils {
 
    private static Base64Utils utils = null;
 
    private Base64Utils() {
 
    }
 
    public static Base64Utils getInstance() {
        if (utils == null) {
            synchronized (Base64Utils.class) {
                if (utils == null) {
                    utils = new Base64Utils();
                }
            }
        }
        return utils;
    }
 
    /**
     * 返回文件大小
     * 
     * @param inFile
     * @return
     */
    public int getFileSize(File inFile) {
        InputStream in = null;
 
        try {
            in = new FileInputStream(inFile);
            // 文件长度
            int len = in.available();
            return len;
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return -1;
    }
 
    /**
     * 将文件转化为base64
     * 
     * @param inFile
     * @return
     */
    public String file2Base64(File inFile) {
 
        // 将文件转化为字节码
        byte[] bytes = copyFile2Byte(inFile);
        if (bytes == null) {
            return null;
        }
 
        // base64,将字节码转化为base64的字符串
        String result = Base64.getEncoder().encodeToString(bytes);
        return result;
    }
 
    /**
     * 将文件转化为字节码
     * 
     * @param inFile
     * @return
     */
    private byte[] copyFile2Byte(File inFile) {
        InputStream in = null;
 
        try {
            in = new FileInputStream(inFile);
            // 文件长度
            int len = in.available();
 
            // 定义数组
            byte[] bytes = new byte[len];
 
            // 读取到数组里面
            in.read(bytes);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 将字符串转化为文件
     * 
     * @param strBase64 base64 编码的文件
     * @param outFile 输出的目标文件地址
     */
    public boolean base64ToFile(String strBase64, File outFile) {
        try {
            // 解码，然后将字节转换为文件
            byte[] bytes = Base64.getDecoder().decode(strBase64); // 将字符串转换为byte数组
            return copyByte2File(bytes, outFile);
        } catch (Exception ioe) {
            ioe.printStackTrace();
            return false;
        }
    }
 
    /**
     * 将字节码转化为文件
     * 
     * @param bytes
     * @param file
     */
    private boolean copyByte2File(byte[] bytes, File file) {
        FileOutputStream out = null;
        try {
            // 转化为输入流
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
 
            // 写出文件
            byte[] buffer = new byte[1024];
 
            out = new FileOutputStream(file);
 
            // 写文件
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len); // 文件写操作
            }
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }
 
    /**
     * 将base64转换为输入流
     * 
     * @param base64
     * @return
     */
    public ByteArrayInputStream base64ToInputStream(String base64) {
        try {
            // 将字符串转换为byte数组
            byte[] bytes = Base64.getDecoder().decode(base64);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
 
            return inputStream;
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return null;
    }
    
    
    public static void main(String[] args) {
        File file = new File("C:\\Users\\test\\Desktop\\base64-master\\123.JPG");
        File fileNew = new File("C:\\Users\\test\\Desktop\\base64-master\\123-copy.JPG");
        System.out.println(Base64Utils.getInstance().file2Base64(file));
        String fileStr = Base64Utils.getInstance().file2Base64(file);
        System.out.println(Base64Utils.getInstance().base64ToFile(fileStr, fileNew));
    }
}
 