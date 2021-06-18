package com.jun.plugin.demo;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class TestIO {

    public static void main(String args[]) throws IOException {
        // 源文件夹
        String url1 = "D:/1/";
        // 目标文件夹
        String url2 = "D:/2/";
        // 创建目标文件夹
        (new File(url2)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(url1)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 复制文件
                String type = file[i].getName().substring(file[i].getName().lastIndexOf(".") + 1);

/*                if (type.equalsIgnoreCase("txt"))// 转码处理
                    copyFile(file[i], new File(url2 + file[i].getName()), MTOServerConstants.CODE_UTF_8, MTOServerConstants.CODE_GBK);
                else*/
                    copyFile(file[i], new File(url2 + file[i].getName()));
            }
            if (file[i].isDirectory()) {
                // 复制目录
                String sourceDir = url1 + File.separator + file[i].getName();
                String targetDir = url2 + File.separator + file[i].getName();
                copyDirectiory(sourceDir, targetDir);
            }
        }
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * 
     * @param srcFileName
     * @param destFileName
     * @param srcCoding
     * @param destCoding
     * @throws IOException
     */
    public static void copyFile(File srcFileName, File destFileName, String srcCoding, String destCoding) throws IOException {// 把文件转换为GBK文件
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFileName), srcCoding));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFileName), destCoding));
            char[] cbuf = new char[1024 * 5];
            int len = cbuf.length;
            int off = 0;
            int ret = 0;
            while ((ret = br.read(cbuf, off, len)) > 0) {
                off += ret;
                len -= ret;
            }
            bw.write(cbuf, 0, off);
            bw.flush();
        } finally {
            if (br != null)
                br.close();
            if (bw != null)
                bw.close();
        }
    }

    /**
     * 
     * @param filepath
     * @throws IOException
     */
    public static void del(String filepath) throws IOException {
        File f = new File(filepath);// 定义文件路径
        if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
            if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
                f.delete();
            } else {// 若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();// 删除文件
                }
            }
        }
    }
    /**
	 * @param args
	 * @throws IOException 
	 */
	public static void main2(String[] args) throws IOException {
		File file = new File("d:"+File.separator+"1.txt");
		File file2 = new File("d:"+File.separator+"2.txt");
		
		
/*		try {
			FileReader fis = new FileReader(file);
			FileWriter fw = new FileWriter(file2);
			char[] b = new char[1024];
			int len = 0;
			while((len=fis.read(b))!=-1){
				System.out.println(String.valueOf(b));
				fw.write(b);
			}
			fw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		   FileInputStream fis=new FileInputStream(file);      //文件字节流
		   InputStreamReader isr=new InputStreamReader(fis,"GBK"); //字节流和字符流的桥梁，可以指定指定字符格式
		   String str=null;
		  
		   //直接读取字符，只要编码问题没问题
		   int c=0;
		   while((c=isr.read())!=-1)
		   System.out.print((char)c);
		   System.out.println("______________________________________________________");
	}

}