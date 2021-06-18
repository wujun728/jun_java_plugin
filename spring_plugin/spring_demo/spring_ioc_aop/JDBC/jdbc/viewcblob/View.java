package jdbc.viewcblob;

import java.io.File;


public class View {
	
       private int id;
       private String description;
       private File   imgFile;
	
    //constructor
    public View(){}
	
    public View(int id,String des,File img){
		this.description=des;
		this.id=id;
		this.imgFile=img;
	}
    //Setter getter   
    public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public File getImgFile() { return imgFile; }
	public void setImgFile(File imgFile) { this.imgFile = imgFile; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description;}
              
}
