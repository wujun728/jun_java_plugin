
package book.io;
import java.io.File;
/**
 * 移动文件或目录
 */
public class MoveFileUtil {

	/**
	 * 移动单个文件，不覆盖已存在的目标文件
	 * @param srcFileName	待移动的原文件名	
	 * @param destFileName	目标文件名
	 * @return		文件移动成功返回true，否则返回false
	 */
	public static boolean moveFile(String srcFileName, String destFileName){
		//默认为不覆盖目标文件
		return MoveFileUtil.moveFile(srcFileName, destFileName, false);
	}
	/**
	 * 移动单个文件
	 * @param srcFileName	待移动的原文件名
	 * @param destFileName	目标文件名
	 * @param overlay		如果目标文件存在，是否覆盖
	 * @return	文件移动成功返回true，否则返回false
	 */
	public static boolean moveFile(String srcFileName, 
			String destFileName, boolean overlay){
		//判断原文件是否存在
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()){
			System.out.println("移动文件失败：原文件" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()){
			System.out.println("移动文件失败：" + srcFileName + "不是一个文件！");
			return false;
		}
		File destFile = new File(destFileName);
		//如果目标文件存在
		if (destFile.exists()){
			//如果允许文件覆盖
			if (overlay){
				//删除已存在的目标文件，无论目标文件是目录还是单个文件
				System.out.println("目标文件已存在，准备删除它！");
				if(!DeleteFileUtil.delete(destFileName)){
					System.out.println("移动文件失败：删除目标文件" + destFileName + "失败！");
					return false;
				}
			} else {
				System.out.println("移动文件失败：目标文件" + destFileName + "已存在！");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()){
				//如果目标文件所在的目录不存在，则创建目录
				System.out.println("目标文件所在目录不存在，准备创建它！");
				if(!destFile.getParentFile().mkdirs()){
					System.out.println("移动文件失败：创建目标文件所在的目录失败！" );
					return false;
				}
			}
		}
		//移动原文件至目标文件
		if (srcFile.renameTo(destFile)){
			System.out.println("移动单个文件" + srcFileName + "至" + destFileName + "成功！");
			return true;
		} else {
			System.out.println("移动单个文件" + srcFileName + "至" + destFileName  + "失败！");
			return true;
		}
	}
	
	/**
	 * 移动目录，不覆盖已存在的目标目录
	 * @param srcDirName	待移动的原目录名
	 * @param destDirName	目标目录名
	 * @return		目录移动成功返回true，否则返回false
	 */
	public static boolean moveDirectory(String srcDirName, String destDirName){
		//默认为不覆盖目标文件
		return MoveFileUtil.moveDirectory(srcDirName, destDirName, false);
	}
	
	/**
	 * 移动目录。
	 * @param srcDirName	待移动的原目录名
	 * @param destDirName	目标目录名
	 * @param overlay		如果目标目论存在，是否覆盖
	 * @return		目录移动成功返回true，否则返回false
	 */
	public static boolean moveDirectory(String srcDirName, 
			String destDirName, boolean overlay){
		//判断原目录是否存在
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()){
			System.out.println("移动目录失败：原目录" + srcDirName + "不存在！");
			return false;
		} else if (!srcDir.isDirectory()){
			System.out.println("移动目录失败：" + srcDirName + "不是一个目录！");
			return false;
		}
		// 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
		if (!destDirName.endsWith(File.separator)){
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		//如果目标文件夹存在，
		if (destDir.exists()){
			if (overlay){
				//允许覆盖则删除已存在的目标目录
				System.out.println("目标目录已存在，准备删除它！");
				if (!DeleteFileUtil.delete(destDirName)){
					System.out.println("移动目录失败：删除目标目录" + destDirName + "失败！");
				}
			} else {
				System.out.println("移动目录失败：目标目录" + destDirName + "已存在！");
				return false;
			}
		} else {
			//创建目标目录
			System.out.println("目标目录不存在，准备创建它！");
			if(!destDir.mkdirs()){
				System.out.println("移动目录失败：创建目标目录失败！" );
				return false;
			}
		}
		boolean flag = true;
		//移动原目录下的文件和子目录到目标目录下
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			//移动子文件
			if (files[i].isFile()){
				flag = MoveFileUtil.moveFile(files[i].getAbsolutePath(), 
						destDirName + files[i].getName(), overlay);
				if (!flag){
					break;
				}
			}
			//移动子目录
			else if (files[i].isDirectory()){
				flag = MoveFileUtil.moveDirectory(files[i].getAbsolutePath(), 
						destDirName + files[i].getName(), overlay);
				if (!flag){
					break;
				}
			}
		}
		if (!flag){
			System.out.println("移动目录" + srcDirName + "至" + destDirName+ "失败！");
			return false;
		}
		// 删除原目录
		if (DeleteFileUtil.deleteDirectory(srcDirName)){
			System.out.println("移动目录" + srcDirName + "至" + destDirName+ "成功！");
			return true;
		} else {
			System.out.println("移动目录" + srcDirName + "至" + destDirName+ "失败！");
			return false;
		}
	}
	
	public static void main(String[] args) {
		//移动单个文件，如果目标文件存在，则替换
		String srcFileName = "C:/temp/temp.txt";
		String destFileName = "C:/tempbak/temp_bak.txt.";
		MoveFileUtil.moveFile(srcFileName, destFileName, true);
		System.out.println();
		//移动目录，如果目标目录存在，则不覆盖
		String srcDirName = "C:/temp";
		String destDirName = "C:/tempbak";
		MoveFileUtil.moveDirectory(srcDirName, destDirName);
	}
}
