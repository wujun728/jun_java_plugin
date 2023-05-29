package net.jueb.util4j.beta.tools.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ViewImageUtil {
	/**
	 * 根据资源文件名字获取图片输入流
	 * @param imageName
	 * @return
	 */
	public static InputStream getImageInputStreamByName(String imageName)
	{
		InputStream in=null;
		in=ViewImageUtil.class.getClassLoader()
				.getResourceAsStream("images/"+imageName);
		return in;
	}
	/**
	 * 根据资源文件名字获取图片操作对象
	 * @param imageName
	 * @return
	 */
	public static BufferedImage getImageByName(String imageName)
	{
		BufferedImage bfi=null;
		try {
			bfi=ImageIO.read(ViewImageUtil.class.getClassLoader()
					.getResourceAsStream("images/"+imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bfi;
	}
}
