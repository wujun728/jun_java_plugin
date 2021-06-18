package cn.ipanel.apps.portalBackOffice.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import cn.ipanel.apps.portalBackOffice.domain.ResourcesDate;
import cn.ipanel.apps.portalBackOffice.service.impl.PoserServiceImpl;

/**
 * 编辑相关常用类
 * @author tianwl
 * @datetime 2010-8-26 上午08:56:49
 */
public class PorserUtil {

	private static Logger logger = Logger.getLogger(PoserServiceImpl.class);
	
	/**
	 * 得到图片位置，一级菜单的左右
	 * @author tianwl
	 * @param data
	 * @return
	 */
	
	public static String getPosition(ResourcesDate data){
		String name = data.getData();
		name = new String(Base64Fiend.decode(name));
		if((data.getType() == "文字") || (data.getType().equalsIgnoreCase("文字"))){
			if(name.endsWith("-1")){
				return "left";
			}
			else if(name.endsWith("-2")){
				return "right";
			};
		}else{
			logger.info("the type is : " + data.getType() +" , not is 文字 ！");
			
		}
		return "";
	}
	/**
	 * 根据图片的名字判断左右位置
	 * @author tianwl
	 * @param name
	 * @return
	 */
	public static String getPosition(String name){
		String[] firstName = name.split("\\.");
		String str = firstName[0];
		String[] poss = str.split("_");//leigq 修改
		String pos = poss[poss.length-1];
		return "haibao" + (Integer.parseInt(pos)-1);
	}
	/**
	 * 更新版本号
	 * @author tianwl
	 * @param version
	 * @return
	 */
	public static String getNewVersion(String version){
		String newVersion = "";
		String[] ver = version.split("\\.");
		int i = Integer.parseInt(ver[1]);
		int j = i+1 ;
		newVersion = ver[0] +"." + j ;
		return newVersion;
	}
	
	public static String getPoserWidthAndHeight(byte[] data){
		String root = PropertyManager.getConfigProperty("jpgFile");//"c:";
		String name = CommonsFiend.getUniqueId(10);
		String fileName = root+FileFiend.FILE_SEPARATOR+name+".jpg";
		logger.info("fileName :  " + fileName + ", name" + name+ " , root : " + root);
		File file = new File(fileName);
		String wh = null;
		if(file.exists()){
			file.mkdir();
		}
		OutputStream os;
		try {
			os = new FileOutputStream(file);
			os.write(data);
			os.close();
			InputStream is = new FileInputStream(file);
			BufferedImage buff = ImageIO.read(file);
			wh = buff.getWidth()+","+buff.getHeight();
			is.close();
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return wh;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(PorserUtil.getNewVersion("v1.2"));
		System.out.println(PorserUtil.getPosition("8_2.jpg"));
	}
}
