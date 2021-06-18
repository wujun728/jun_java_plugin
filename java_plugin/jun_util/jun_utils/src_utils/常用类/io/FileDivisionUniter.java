package book.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 文件分割合并器，将大文件分割成若干小文件；将多个小文件合并到一个大文件。
 */
public class FileDivisionUniter {
	//分割后的文件名后缀
	public static final String SUFFIX = ".pp";

	/**
	 * 分割文件
	 * @param fileName	待分割的文件名
	 * @param size	小文件的大小，单位字节
	 * @return		分割成的小文件的文件名
	 * @throws Exception
	 */
	public static String[] divide(String fileName, long size) throws Exception {

		File inFile = new File(fileName);
		if (!inFile.exists() || (!inFile.isFile())) {
			throw new Exception("指定文件不存在！");
		}
		//获得被分割文件父文件，将来被分割成的小文件便存在这个目录下
		File parentFile = inFile.getParentFile();
		
		//	取得文件的大小
		long fileLength = inFile.length(); 
		if (size <=0){
			size = fileLength / 2;
		}
		// 取得被分割后的小文件的数目
		int num = (fileLength % size != 0) ? (int)(fileLength / size + 1)
				: (int)(fileLength / size); 
		// 存放被分割后的小文件名
		String[] outFileNames = new String[num];
		// 输入文件流，即被分割的文件
		FileInputStream in = new FileInputStream(inFile);
		
		// 读输入文件流的开始和结束下标
		long inEndIndex = 0;
		int inBeginIndex = 0;
		
		//根据要分割的数目输出文件
		for (int outFileIndex = 0; outFileIndex < num; outFileIndex++) {
			//对于前num - 1个小文件，大小都为指定的size
			File outFile = new File(parentFile, inFile.getName()
					+ outFileIndex + SUFFIX);
			// 构建小文件的输出流
			FileOutputStream out = new FileOutputStream(outFile);
			//将结束下标后移size
			inEndIndex += size;
			inEndIndex = (inEndIndex > fileLength) ? fileLength : inEndIndex;
			// 从输入流中读取字节存储到输出流中
			for (; inBeginIndex < inEndIndex; inBeginIndex++) {
				out.write(in.read());
			}
			out.close();
			outFileNames[outFileIndex] = outFile.getAbsolutePath();
		}
		in.close();
		return outFileNames;
	}

	/**
	 * 合并文件
	 * @param fileNames		待合并的文件名，是一个数组
	 * @param TargetFileName	目标文件名
	 * @return		目标文件的全路径
	 * @throws Exception
	 */
	public static String unite(String[] fileNames, String TargetFileName)
			throws Exception {
		File inFile = null; 
		//构建文件输出流
		File outFile = new File(TargetFileName);
		FileOutputStream out = new FileOutputStream(outFile);

		for (int i = 0; i < fileNames.length; i++) {
			// 打开文件输入流
			inFile = new File(fileNames[i]);
			FileInputStream in = new FileInputStream(inFile);
			// 从输入流中读取数据，并写入到文件数出流中
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close();
		}
		out.close();
		
		return outFile.getAbsolutePath();
	}

	public static void main(final String[] args) throws Exception {
		//分割文件
		String fileName = "C:/temp/temp.xls";
		long size = 1000;
		String[] fileNames = FileDivisionUniter.divide(fileName, size);
		System.out.println("分割文件" + fileName + "结果：");
		for (int i=0; i<fileNames.length; i++){
			System.out.println(fileNames[i]);	
		}
		//合并文件
		String newFileName = "C:/temp/newTemp.xls";
		System.out.println("合并文件结果：" + 
				FileDivisionUniter.unite(fileNames, newFileName));
	}
}