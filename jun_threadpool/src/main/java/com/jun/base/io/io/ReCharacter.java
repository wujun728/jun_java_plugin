package com.jun.base.io.io;

import java.io.*;

/*

 字符流


 */
public class ReCharacter {

    public static void main(String[] args) {
        inputNoCache();
    }

    /**
     * 使用字符流输入，字符流输出。使用了字符缓冲区
     */
    public static void input() {

        try {
            FileReader fr2 = new FileReader("D:\\2.txt");

            char[] buf2 = new char[1024];
            int len2 = fr2.read(buf2);
            String myStr2 = new String(buf2, 0, len2);
            System.out.println(myStr2);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用字符流输入，字符流输出。使用了字符缓冲区
     */
    public static void out() {

        String  str = "阿弥陀佛";

        FileWriter fw = null;
        try {
            fw = new FileWriter("D:\\2.txt");
            fw.write(str);
            //fw.flush();
            fw.close();//注释掉就文件就没有内容。因为是操作的缓冲，fw.flush()才会有。

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /****
     *
     *【使用字符流输入，字符流输出，没有使用缓冲区，直接读行】
     *
     *
     */

    public static void outNoCache() {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream("D:\\3.txt"), "utf-8"));
            String myStr3 = br.readLine();
            br.close();
            System.out.println(myStr3);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void inputNoCache() {
        String  str = "阿弥陀佛";

        try {
            PrintWriter pw = new PrintWriter("D:\\3.txt", "utf-8");

            pw.write(str);

            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
