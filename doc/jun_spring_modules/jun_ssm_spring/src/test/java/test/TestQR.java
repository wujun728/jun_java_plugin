package test;

import java.io.File;

import org.junit.Test;
import org.springrain.frame.util.QrCodeUtils;

import com.google.zxing.Result;

public class TestQR {

	@Test
	public void read() throws Exception {
		
		String filepath="C:/Users/caomei/Documents/3.jpg";
		
		File qrFile=new File(filepath);
		
		Result qr=QrCodeUtils.decodeQrCode(qrFile);
		System.out.println(qr.getText());
		
		
	}
}
