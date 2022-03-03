package org.springrain.frame.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QrCodeUtils {
	
	
	private static final Integer defaultWidth=300;
	private static final Integer defaultHeight=300;
	private static final String defaultQrCodeFormat="png";
	
	private QrCodeUtils(){
		throw new IllegalAccessError("工具类不能实例化");
	}
	

	/**
	 * 生成二维码
	 * @param content 内容
	 * @param qrFile 需要生成的二维码文件
	 * @return
	 * @throws Exception
	 */
	
	public static File genQrCode(String content,File qrFile) throws Exception{
        return genQrCode(content, qrFile, defaultWidth, defaultHeight);
	}
	
	

	
	/**
	 * 生成二维码
	 * @param content 内容
	 * @param qrFile 需要生成的二维码文件
	 * @param width 二维码文件宽度
	 * @param height 二维码文件高度
	 * @return
	 * @throws Exception
	 */
    public static File genQrCode(String content,File qrFile,Integer width,Integer height) throws Exception{
    	if(content==null||qrFile==null){
    		return null;
    	}
    	String qrCodeFormat=FileUtils.getFileFormat(qrFile.getName());
    	if(StringUtils.isBlank(qrCodeFormat)){
    		qrCodeFormat=defaultQrCodeFormat;
    	}
    	
        HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, GlobalStatic.defaultCharset);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        MatrixToImageWriter.writeToPath(bitMatrix, qrCodeFormat, qrFile.toPath());// 输出图像
        
		return qrFile;
	}
    
    /**
     * 二维码识别
     * @param qrFile
     * @return
     * @throws Exception
     */
    public static Result decodeQrCode(File qrFile) throws Exception{
    	    BufferedImage image = ImageIO.read(qrFile);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, GlobalStatic.defaultCharset);
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
			
			return result;
			
    }    
    

}
