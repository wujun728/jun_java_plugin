

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
/**
 * 多种方式写文件
 */
public class WriteToFile {
	/**
	 * 以字节为单位写文件。适合于写二进制文件。如图片等
	 * @param fileName	文件名
	 */
	public static void writeFileByBytes(String fileName){
		File file = new File(fileName);
		OutputStream out= null;
		try {
			// 打开文件输出流
			out = new FileOutputStream(file);
			String content = "文件内容：\n1,The First line;\n2,The second line.";
			byte[] bytes = content.getBytes();
			//写入文件
			out.write(bytes);
			System.out.println("写文件" + file.getAbsolutePath() + "成功！");
		} catch (IOException e){
			System.out.println("写文件" + file.getAbsolutePath() + "失败！");
			e.printStackTrace();
		} finally {
			if (out != null){
				try {
					//关闭输出文件流
					out.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	/**
	 * 以字符为单位写文件。
	 * @param fileName	文件名
	 */
	public static void writeFileByChars(String fileName){
		File file = new File(fileName);
		Writer writer = null;
		try {
			//打开文件输出流
			writer = new OutputStreamWriter(new FileOutputStream(file));
			String content = "文件内容：\n1,The First line;\n2,The second line.";
			writer.write(content);
			System.out.println("写文件" + file.getAbsolutePath() + "成功！");
		} catch (IOException e){
			System.out.println("写文件" + file.getAbsolutePath() + "失败！");
			e.printStackTrace();
		} finally {
			if (writer != null){
				try {
					//关闭输出文件流
					writer.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	/**
	 * 以行为单位写文件
	 * @param fileName	文件名
	 */
	public static void writeFileByLines(String fileName){
		File file = new File(fileName);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(file));
			//写字符串
			writer.println("文件内容：");
			//能写各种基本类型数据
			writer.print(true);
			writer.print(155);
			//换行
			writer.println();
			//写入文件
			writer.flush();
			System.out.println("写文件" + file.getAbsolutePath() + "成功！");
		} catch (FileNotFoundException e) {
			System.out.println("写文件" + file.getAbsolutePath() + "失败！");
			e.printStackTrace();
		} finally {
			if (writer != null){
				//关闭输出文件流
				writer.close();
			}
		}
	}
	
	public static void main(String[] args) {
		String fileName = "c:/temp/tempfile0.txt";
		WriteToFile.writeFileByBytes(fileName);
		fileName = "c:/temp/tempfile1.txt";
		WriteToFile.writeFileByChars(fileName);
		fileName = "c:/temp/tempfile2.txt";
		WriteToFile.writeFileByLines(fileName);
	}
}
