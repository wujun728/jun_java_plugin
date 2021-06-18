package com.ibeetl.admin.core.gen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.ibeetl.admin.core.gen.model.Entity;

public class MavenProjectTarget extends BaseTarget {
	Entity entity;
	String basePackage;
	String basePackagePath = null;
	String targetPath = null;
	
	public MavenProjectTarget(Entity entity,String basePackage) {
		this.entity = entity;
		this.basePackage = basePackage;
		this.basePackagePath = basePackage.replace('.', '/');
	}

	@Override
	public void flush(AutoGen gen, String content) {
		String name = gen.getName();
		String target  = null;
		if (gen instanceof JSDelGen) {
			 target = getResourcePath()+"/static/js/"+this.urlBase+"/"+entity.getCode()+"/"+name;
		}else if (gen instanceof JSEditGen) {
			 target = getResourcePath()+"/static/js/"+this.urlBase+"/"+entity.getCode()+"/"+name;
		}else if (gen instanceof JSAddGen) {
			 target = getResourcePath()+"/static/js/"+this.urlBase+"/"+entity.getCode()+"/"+name;
		}else if (gen instanceof JSApiGen) {
			 target = getResourcePath()+"/static/js/"+this.urlBase+"/"+entity.getCode()+"/"+name;
		}else if (gen instanceof JSIndexGen) {
			 target = getResourcePath()+"/static/js/"+this.urlBase+"/"+entity.getCode()+"/"+name;
		}else if (gen instanceof HtmlIndexGen) {
			 target = getResourcePath()+"/templates/"+this.urlBase+"/"+entity.getCode()+"/"+name;
		}else if (gen instanceof HtmlEditGen) {
			 target = getResourcePath()+"/templates/"+this.urlBase+"/"+entity.getCode()+"/"+name;
		}else if (gen instanceof HtmlAddGen) {
			 target = getResourcePath()+"/templates/"+this.urlBase+"/"+entity.getCode()+"/"+name;
		}else if (gen instanceof MdGen) {
			 target = getResourcePath()+"/sql/"+entity.getSystem()+"/"+name;
		}else if (gen instanceof JavaEntityGen) {
			 target = getSrcPath()+"/"+basePackagePath+"/entity/"+name;
		}else if (gen instanceof JavaDaoGen) {
			 target = getSrcPath()+"/"+basePackagePath+"/dao/"+name;
		}else if (gen instanceof JavaQueryGen) {
			 target = getSrcPath()+"/"+basePackagePath+"/web/query/"+name;
		}else if (gen instanceof JavaServiceGen) {
			 target = getSrcPath()+"/"+basePackagePath+"/service/"+name;
		}else if (gen instanceof JavaControllerGen) {
			 target = getSrcPath()+"/"+basePackagePath+"/web/"+name;
		}
		
		if(target==null) {
			return ;
		}
		flush(target,content);

	}

	protected void flush(String path, String content) {
		
		FileWriter fw;
		try {
			File file = new File(path);
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fw = new FileWriter(new File(path));
			fw.write(content);
			fw.close();
			System.out.println(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    private  String getSrcPath() {
       
		return getRootPath() + File.separator + "src/main/java";
	}

	private  String getResourcePath() {

		return getRootPath() + File.separator + "src/main/resources";
	}

	public  String getRootPath() {
	    if(targetPath!=null) {
	        return targetPath;
	    }else {
	        return detectRootPath();
	    }
		
	}
	
	public static String detectRootPath() {
	    String srcPath;
        String userDir = System.getProperty("user.dir");
        if (userDir == null) {
            throw new NullPointerException("用户目录未找到");
        }

        return userDir;
	}

}
