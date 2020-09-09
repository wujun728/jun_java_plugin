package org.iherus.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.iherus.codegen.qrcode.QrcodeGenerator;
import org.iherus.codegen.qrcode.SimpleQrcodeGenerator;
import org.iherus.codegen.utils.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestQrGen {

	private static final String content = "https://baike.baidu.com/item/%E5%97%B7%E5%A4%A7%E5%96%B5/19817560?fr=aladdin";

	private QrcodeGenerator generator = new SimpleQrcodeGenerator();

	private String localLogoPath;

	@Before
	public void init() {
		URL url = this.getClass().getClassLoader().getResource("mates/AodaCat-1.png");
		this.localLogoPath = url.getFile();
	}

	@Test
	public void testDefault() throws IOException {
		Assert.assertTrue(generator.generate(content).toFile("F:\\AodaCat_default.png"));
		testLocalLogo();
		testRemoteLogo();
		testCustomConfig();
	}

	/**
	 * 添加本地logo
	 * @throws IOException
	 */
	@Test
	public void testLocalLogo() throws IOException {
		boolean success = generator.setLogo(this.localLogoPath).generate(content).toFile("F:\\AodaCat_local_logo.png");
		Assert.assertTrue(success);
	}

	/**
	 * 添加在线logo
	 * @throws IOException
	 */
	@Test
	public void testRemoteLogo() throws IOException {
		generator.setRemoteLogo("http://www.demlution.com/site_media/media/photos/2014/11/06/3JmYoueyyxS4q4FcxcavgJ.jpg");
		Assert.assertTrue(generator.generate("https://www.apple.com/cn/").toFile("F:\\Apple_remote_logo.png"));
	}

	/**
	 * 自定义二维码配置
	 * @throws IOException
	 */
	@Test
	public void testCustomConfig() throws IOException {
		generator.getQrcodeConfig()
			.setBorderSize(2)
			.setPadding(10)
			.setMasterColor("#00BFFF")
			.setLogoBorderColor("#B0C4DE");
		Assert.assertTrue(generator.setLogo(this.localLogoPath).generate(content).toFile("F:\\AodaCat_custom.png"));
	}
	
	/**
	 * 写入输出流
	 * @throws IOException
	 */
	@Test
	public void testWriteToStream() throws IOException {
		OutputStream ous = null;
		try {
			ous = new FileOutputStream("F:\\Qrcode_out.png");
			Assert.assertTrue(generator.generate(content).toStream(ous));
		} finally {
			IOUtils.closeQuietly(ous);
		}
	}
	
	@Test
	public void testGetImage() throws IOException {
		BufferedImage image = generator.generate(content).getImage();
		ImageIO.write(image, "png", new File("F:\\Qrcode_out.png"));
	}

}
