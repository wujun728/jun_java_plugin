package org.itkk.udf.qrcode;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.itkk.udf.qrcode.domain.QrCodeRequest;
import org.itkk.udf.qrcode.option.LogoStyle;
import org.itkk.udf.qrcode.service.QrcodeService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * QrCodeServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QrcodeService.class)
public class QrCodeServiceTest {

    /**
     * qrcodeService
     */
    @Autowired
    private QrcodeService qrcodeService;

    @Test
    @Ignore
    public void testDecode() throws FormatException, ChecksumException, NotFoundException, IOException { //NOSONAR
        String img = "https://static.oschina.net/uploads/img/201707/09205944_0PzS.jpg";
        String ans = qrcodeService.decode(img);
        System.out.println(ans);
    }

    @Test
    @Ignore
    public void testParseString() throws IOException, WriterException { //NOSONAR
        String msg = "http://itkk.org:81";
        QrCodeRequest request = new QrCodeRequest();
        request.setContent(msg);
        String out = qrcodeService.parseString(request);
        System.out.println("普通二维码 : <img src=\"data:image/png;base64," + out + "\" />");

        request = new QrCodeRequest();
        request.setContent(msg);
        request.setSize(300);
        request.setPreColor("0xffff0000");
        request.setBgColor("0xffffffff");
        request.setPadding(0);
        out = qrcodeService.parseString(request);
        System.out.println("生成红色的二维码 300x300, 无边框 : <img src=\"data:image/png;base64," + out + "\" />");

        String logo = "https://static.oschina.net/uploads/user/283/566591_100.jpeg";
        request = new QrCodeRequest();
        request.setContent(msg);
        request.setSize(300);
        request.setPreColor("0xffff0000");
        request.setBgColor("0xffffffff");
        request.setPadding(0);
        request.setLogo(logo);
        request.setLogoBorderColor("0xff808080");
        request.setLogoBorder(true);
        out = qrcodeService.parseString(request);
        System.out.println("生成带logo的二维码 : <img src=\"data:image/png;base64," + out + "\" />");

        logo = "https://static.oschina.net/uploads/user/283/566591_100.jpeg";
        request = new QrCodeRequest();
        request.setContent(msg);
        request.setSize(300);
        request.setPreColor("0xff0000ff");
        request.setBgColor("0xffffffff");
        request.setDetectOutColor("0xff00FF00");
        request.setDetectInColor("0xffff0000");
        request.setPadding(1);
        request.setLogo(logo);
        request.setLogoRate(15);
        request.setLogoStyle(LogoStyle.ROUND.name());
        request.setLogoBorderColor("0xff808080");
        request.setLogoBorder(true);
        request.setBgImg(logo);
        request.setBgOpacity(0.8f);
        out = qrcodeService.parseString(request);
        System.out.println("无边框， 重新着色位置探测图像， 设置背景 : <img src=\"data:image/png;base64," + out + "\" />");

    }

}
