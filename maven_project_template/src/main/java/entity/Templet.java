package entity;
/**
 * 模板文件实体
 * @author Administrator
 *
 */
public class Templet {
	
	private String path;//生成路径
	private String fileName;//模板文件名
	private String allPath;//完成路径
	
	public String getAllPath() {
		return allPath;
	}
	public void setAllPath(String allPath) {
		this.allPath = allPath;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
