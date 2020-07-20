package com.ketayao.utils.others;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * 
 * 根据文本生成图片的工具
 * 在个人空间中，如果你设置了email地址或者msn，那么oschina会将使用图片的方式来显示这个地址，
 * 以防止邮箱地址被爬虫扒到，狂发垃圾邮件。
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年9月9日 下午2:49:34
 */
public class TextImageUtils {

	private final static IndexColorModel icm = createIndexColorModel();
	
	/**
	 * 生成电子邮件图片
	 * @param email
	 * @param out
	 * @throws IOException
	 */
	public static void MakeEmailImage(String email, OutputStream out) throws IOException {
		int height = 22;
		BufferedImage bi = new BufferedImage(255,height,BufferedImage.TYPE_INT_RGB);        
	    Graphics2D g = (Graphics2D)bi.getGraphics();
	    Font mFont = new Font("Verdana", Font.PLAIN, 14);
	    g.setFont(mFont);
	    g.drawString(email, 2, 19);
		FontMetrics fm = g.getFontMetrics();
		int new_width = fm.charsWidth(email.toCharArray(), 0, email.length()) + 4;
		int new_height = fm.getHeight();
		BufferedImage nbi = new BufferedImage(new_width, new_height, 
		    BufferedImage.TYPE_BYTE_INDEXED, icm);
		Graphics2D g2 = (Graphics2D)nbi.getGraphics();
		g2.setColor(new Color(0,0,0,0));//透明
		g2.fillRect(0,0,new_width,new_height);
	    g2.setFont(mFont);
	    g2.setColor(new Color(200,0,0));
	    g2.drawString(email, 2, new_height-4);

	    ImageIO.write(nbi, "gif", out);
	}

	/**
	 * 生成电话号码图片
	 * @param phone
	 * @param out
	 * @throws IOException
	 */
	public static void MakePhoneImage(String phone, OutputStream out) throws IOException {
		int height = 22;
		BufferedImage bi = new BufferedImage(255,height,BufferedImage.TYPE_INT_RGB);        
	    Graphics2D g = (Graphics2D)bi.getGraphics();
	    Font mFont = new Font("Verdana", Font.BOLD, 20);
	    g.setFont(mFont);
	    g.drawString(phone, 2, 19);
		FontMetrics fm = g.getFontMetrics();
		int new_width = fm.charsWidth(phone.toCharArray(), 0, phone.length()) + 4;
		int new_height = fm.getHeight();
		BufferedImage nbi = new BufferedImage(new_width, new_height,
		    BufferedImage.TYPE_BYTE_INDEXED, icm);
		Graphics2D g2 = (Graphics2D)nbi.getGraphics();
		g2.setColor(new Color(0,0,0,0));//透明
		g2.fillRect(0,0,new_width,new_height);
	    g2.setFont(mFont);
	    g2.setColor(new Color(200,0,0));
	    g2.drawString(phone, 2, new_height-4);		
	    ImageIO.write(nbi, "gif", out);
	}
	/**
	 * 生成产品关键特征
	 * @param attribute
	 * @param out
	 * @throws IOException
	 */
	public static void MakeProductAttribute(String attribute, OutputStream out) throws IOException{
		int height = 22;
		BufferedImage bi = new BufferedImage(255,height,BufferedImage.TYPE_INT_RGB);        
	    Graphics2D g = (Graphics2D)bi.getGraphics();
	    Font mFont = new Font("宋体", Font.BOLD, 13);
	    g.setFont(mFont);
	    g.drawString(new String(attribute), 2, 19);
		FontMetrics fm = g.getFontMetrics();
		int new_width = fm.charsWidth(attribute.toCharArray(), 0, attribute.length()) + 4;
		int new_height = fm.getHeight();
		BufferedImage nbi = new BufferedImage(new_width, new_height,
		   BufferedImage.TYPE_BYTE_INDEXED, icm);
		Graphics2D g2 = (Graphics2D)nbi.getGraphics();
		g2.setColor(new Color(0,0,0,0));//透明
		g2.fillRect(0,0,new_width,new_height);
	    g2.setFont(mFont);
	    g2.setColor(new Color(200,0,0));
	    g2.drawString(attribute, 2, new_height-4);
	    ImageIO.write(nbi, "gif", out);
	}

    static IndexColorModel createIndexColorModel() {
        BufferedImage ex = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_INDEXED);
        IndexColorModel icm = (IndexColorModel) ex.getColorModel();
        int SIZE = 256;
        byte[] r = new byte[SIZE];
        byte[] g = new byte[SIZE];
        byte[] b = new byte[SIZE];
        byte[] a = new byte[SIZE];
        icm.getReds(r);
        icm.getGreens(g);
        icm.getBlues(b);
        java.util.Arrays.fill(a, (byte)255);
        r[0] = g[0] = b[0] = a[0] = 0; //transparent
        return new IndexColorModel(8, SIZE, r, g, b, a);
    }
    
    public static void main(String[] args) throws IOException {
		String num = "15106900000";
		FileOutputStream fos = new FileOutputStream("tmp/phone.gif");
		try{
			TextImageUtils.MakePhoneImage(num, fos);
		}finally{
			fos.close();
		}
		String email = "abc@163.com";
		FileOutputStream fos2 = new FileOutputStream("tmp/email.gif");
		try{
			TextImageUtils.MakeEmailImage(email, fos2);
		}finally{
			fos2.close();
		}
	}
}
