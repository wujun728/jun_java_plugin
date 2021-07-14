package com.jun.plugin.zxing;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
 * 生成二维码图片工具类
 * 创建者小柒2012
 */
public class QrcodeUtil {
	 /**
	  * 生成二维码
	  * @Author  小柒2012
	  * @param width
	  * @param height
	  * @param content 
	  * @param format (图片格式)
	  * @param filePath  void
	  * @throws IOException 
	  * @throws WriterException 
	  *
	  */
	 public static void createQrcode(int width,int height,String content,String format,String filePath){
		    try {
		    	Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>(); 
		        //指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）  
		        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
		        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); //内容所使用字符集编码
		        hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数  
		        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, 
		        		 BarcodeFormat.QR_CODE,
		        		 width,  //条形码的宽度  
			             height, //条形码的高度  
			             hints); //生成条形码时的一些配置,此项可选  
		        File outputFile = new File(filePath);
		        if(!outputFile.exists()){
		            outputFile.mkdirs();
		        }
		        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
	 public static void main(String[] args) {
		 createQrcode(200,200,"https://www.baidu.com","png","D:\\createQrcode.png");
	}
}
