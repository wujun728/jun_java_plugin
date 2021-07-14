package book.io;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFileUtil {

	/**
	 * 复制单个文件， 如果目标文件存在，则不覆盖。
	 * @param srcFileName	待复制的文件名
	 * @param destFileName	目标文件名
	 * @return		如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName){
		return CopyFileUtil.copyFile(srcFileName, destFileName, false);
	}
	
	/**
	 * 复制单个文件
	 * @param srcFileName	待复制的文件名	
	 * @param destFileName	目标文件名
	 * @param overlay		如果目标文件存在，是否覆盖
	 * @return	如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, 
			String destFileName, boolean overlay) {
		//判断原文件是否存在
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()){
			System.out.println("复制文件失败：原文件" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()){
			System.out.println("复制文件失败：" + srcFileName + "不是一个文件！");
			return false;
		}
		//判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()){
			//如果目标文件存在，而且复制时允许覆盖。
			if (overlay){
				//删除已存在的目标文件，无论目标文件是目录还是单个文件
				System.out.println("目标文件已存在，准备删除它！");
				if(!DeleteFileUtil.delete(destFileName)){
					System.out.println("复制文件失败：删除目标文件" + destFileName + "失败！");
					return false;
				}
			} else {
				System.out.println("复制文件失败：目标文件" + destFileName + "已存在！");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()){
				//如果目标文件所在的目录不存在，则创建目录
				System.out.println("目标文件所在的目录不存在，准备创建它！");
				if(!destFile.getParentFile().mkdirs()){
					System.out.println("复制文件失败：创建目标文件所在的目录失败！" );
					return false;
				}
			}
		}
		//准备复制文件
		int byteread = 0;//读取的位数
		InputStream in = null;
		OutputStream out = null;
		try {
			//打开原文件
			in = new FileInputStream(srcFile);  
			//打开连接到目标文件的输出流
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			//一次读取1024个字节，当byteread为-1时表示文件已经读完
			while ((byteread = in.read(buffer)) != -1) {
				//将读取的字节写入输出流
				out.write(buffer, 0, byteread);
			}
			System.out.println("复制单个文件" + srcFileName + "至" + destFileName + "成功！");
			return true;
		} catch (Exception e) {
			System.out.println("复制文件失败：" + e.getMessage());
			return false;
		} finally {
			//关闭输入输出流，注意先关闭输出流，再关闭输入流
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 复制整个目录的内容，如果目标目录存在，则不覆盖
	 * @param srcDirName	待复制的目录名
	 * @param destDirName	目标目录名
	 * @return		如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName){
		return CopyFileUtil.copyDirectory(srcDirName, destDirName, false);
	}
	/**
	 * 复制整个目录的内容
	 * @param srcDirName	待复制的目录名
	 * @param destDirName	目标目录名
	 * @param overlay		如果目标目录存在，是否覆盖
	 * @return	如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName,
			boolean overlay) {
		// 判断原目录是否存在
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {
			System.out.println("复制目录失败：原目录" + srcDirName + "不存在！");
			return false;
		} else if (!srcDir.isDirectory()) {
			System.out.println("复制目录失败：" + srcDirName + "不是一个目录！");
			return false;
		}
		// 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		// 如果目标文件夹存在，
		if (destDir.exists()) {
			if (overlay) {
				// 允许覆盖则删除已存在的目标目录
				System.out.println("目标目录已存在，准备删除它！");
				if (!DeleteFileUtil.delete(destDirName)) {
					System.out.println("复制目录失败：删除目标目录" + destDirName + "失败！");
				}
			} else {
				System.out.println("复制目录失败：目标目录" + destDirName + "已存在！");
				return false;
			}
		} else {
			// 创建目标目录
			System.out.println("目标目录不存在，准备创建它！");
			if (!destDir.mkdirs()) {
				System.out.println("复制目录失败：创建目标目录失败！");
				return false;
			}
		}
		boolean flag = true;
		// 列出源文件夹下所有文件（包括子目录）的文件名
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 如果是一个单个文件，则进行复制
			if (files[i].isFile()) {
				flag = CopyFileUtil.copyFile(files[i].getAbsolutePath(), 
						destDirName + files[i].getName());
				if (!flag){
					break;
				}
			}
			// 如果是子目录，继续复制目录
			if (files[i].isDirectory()) {
				flag = CopyFileUtil.copyDirectory(files[i].getAbsolutePath(), 
						destDirName + files[i].getName());
				if (!flag){
					break;
				}
			}
		}
		if (!flag){
			System.out.println("复制目录" + srcDirName + "至" + destDirName+ "失败！");
			return false;
		}
		System.out.println("复制目录" + srcDirName + "至" + destDirName+ "成功！");
		return true;

	}

	public static void main(String[] args) {
		//复制单个文件，如果目标存在，则覆盖
		String srcPath = "C:/temp/tempfile0.txt";
		String destPath = "C:/temp_bak/tempfile0_bak.txt";
		CopyFileUtil.copyFile(srcPath, destPath, true);
		//如果目标存在，则不覆盖
		CopyFileUtil.copyFile(srcPath, destPath);
		System.out.println();
		//复制文件夹，如果目标存在，则覆盖
		String srcDir = "C:/temp";
		String destDir = "D:/temp";
		CopyFileUtil.copyDirectory(srcDir, destDir, true);
	}
}