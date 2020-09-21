package org.itkk.udf.qrcode.web;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.itkk.udf.core.ApplicationConfig;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.qrcode.domain.QrCodeRequest;
import org.itkk.udf.qrcode.option.QrCodeOptions;
import org.itkk.udf.qrcode.service.QrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * QrcodeController
 */
@RestController
public class QrcodeController implements IQrcodeController {

    /**
     * 描述 : applicationConfig
     */
    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * qrcodeService
     */
    @Autowired
    private QrcodeService qrcodeService;

    @Override
    public RestResponse<String> decode(MultipartFile file) throws IOException, FormatException, ChecksumException, NotFoundException {
        return new RestResponse<>(this.qrcodeService.decode(ImageIO.read(file.getInputStream())));
    }

    @Override
    public RestResponse<String> decode(String path) throws FormatException, ChecksumException, NotFoundException, IOException {
        return new RestResponse<>(this.qrcodeService.decode(path));
    }

    @Override
    public RestResponse<String> parse(@RequestBody QrCodeRequest qrCodeRequest) throws IOException, WriterException {
        return new RestResponse<>(this.qrcodeService.parseString(qrCodeRequest));
    }

    @Override
    public void download(@RequestBody QrCodeRequest qrCodeRequest, HttpServletResponse response) throws IOException, WriterException {
        //常量
        final String contentType = "image/png";
        //输出流
        OutputStream os = null;
        try {
            //设置response
            response.setCharacterEncoding(applicationConfig.getEncoding());
            response.setContentType(contentType);
            //生成二维码
            BufferedImage bufferedImage = this.qrcodeService.parseBufferedImage(qrCodeRequest);
            //输出
            os = response.getOutputStream();
            ImageIO.write(bufferedImage, QrCodeOptions.DEF_PIC_TYPE, os);
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
}
