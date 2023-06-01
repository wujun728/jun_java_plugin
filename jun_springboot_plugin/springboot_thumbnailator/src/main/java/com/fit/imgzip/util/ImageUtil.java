package com.fit.imgzip.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.fit.imgzip.config.ImgZipConfig;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.web.multipart.MultipartFile;


/**
 * 图片压缩工具类
 *
 * @author zlzhaoe
 * 2020-03-30 15:31
 **/
public class ImageUtil {
	public static String imgUploadCompress(MultipartFile multipartFile,String path,Double scale) throws IOException {
		if(!checkImage(multipartFile)){
			return "-1";//图片非法--非图片格式
		}
		if(path == null || path.trim().equals("")){
			path = ImgZipConfig.name;// 如果没有传默认项目名称，走默认配置
		}
		if(scale == null){
			scale = ImgZipConfig.defaultScale;// 如果没有传默认项目名称，走默认配置
		}
		if(scale>1||scale<=0){
			scale = ImgZipConfig.defaultScale;// 如果压缩比例非法，走默认配置
		}
		//String origFilename = multipartFile.getOriginalFilename(); // 名称,服务器的原因，有时候会导致乱码
		String origFilename = UUID.randomUUID().toString()+getFileExtension(multipartFile);//图片名称改为UUID
		String imagesPath = createFileBySysTime(path+"/");
		String newPath = imagesPath+"/"+origFilename;
		File dest = new File(ImgZipConfig.imagePath+newPath); //图片地址
		try {
			// 压缩并处理图片
			Thumbnails.of(multipartFile.getInputStream()).scale(scale).toFile(dest);
			return newPath;//移除无用拼接
		} catch (IOException e) {
			try {
				// 上传失败，利用springMVC自带上传方式
				multipartFile.transferTo(dest);
				return newPath;
			} catch (IOException e1) {
				e1.printStackTrace();
				return "0";
			}
		}
	}
	 /**
	  * 根据时间生成文件夹
	  * @param path
	  * @return
	 * @throws IOException 
	  */
    private static String createFileBySysTime(String path) throws IOException {
        // 1. 读取系统时间
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        // 2. 格式化系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = format.format(time); //获取系统当前时间并将其转换为string类型,fileName即文件名
        // 3. 创建文件夹
        String newPath = path +fileName;
        File file = new File(ImgZipConfig.imagePath+newPath);
        //如果文件目录不存在则创建目录
        if (!file.exists()) {
            if (!file.mkdir()) {
                System.out.println("当前路径不存在，创建创建路径");
                file.mkdirs();
            }
        }
        System.out.println("创建成功" + file.toString());
        return newPath;
    }
    /**
     * 获取文件后缀
     * @param cFile
     * @return
     */
	private static String getFileExtension(MultipartFile cFile) {
		String originalFileName = cFile.getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	/**
	 * 校验图片是否为图片格式
	 * @param multipartFile
	 */
	private static boolean checkImage(MultipartFile multipartFile){
		try {
			BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
			if(bi == null){ 
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}