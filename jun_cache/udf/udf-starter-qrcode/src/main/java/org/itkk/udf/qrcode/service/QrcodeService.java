package org.itkk.udf.qrcode.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;
import org.itkk.udf.core.exception.SystemRuntimeException;
import org.itkk.udf.qrcode.domain.QrCodeRequest;
import org.itkk.udf.qrcode.option.*;
import org.itkk.udf.qrcode.utils.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * QrcodeService
 */
@Service
public class QrcodeService {

    /**
     * 读取二维码中的内容, 并返回
     *
     * @param image image
     * @return 内容
     * @throws FormatException   FormatException
     * @throws ChecksumException ChecksumException
     * @throws NotFoundException NotFoundException
     */
    public String decode(BufferedImage image) throws FormatException, ChecksumException, NotFoundException {
        //判空
        if (image == null) {
            throw new SystemRuntimeException("can not load qrCode!");
        }
        //开始读取
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader qrCodeReader = new QRCodeReader();
        Result result = qrCodeReader.decode(bitmap);
        return result.getText();
    }

    /**
     * 读取二维码中的内容, 并返回
     *
     * @param path 地址
     * @return 内容
     * @throws IOException       IOException
     * @throws FormatException   FormatException
     * @throws ChecksumException ChecksumException
     * @throws NotFoundException NotFoundException
     */
    public String decode(String path) throws IOException, FormatException, ChecksumException, NotFoundException {
        return decode(ImageUtil.getByPath(path));
    }

    /**
     * 生成二维码
     *
     * @param request 参数
     * @return 结果
     * @throws IOException     IOException
     * @throws WriterException WriterException
     */
    public String parseString(QrCodeRequest request) throws IOException, WriterException {
        return this.asString(this.build(request));
    }

    /**
     * 生成二维码
     *
     * @param request 参数
     * @return 结果
     * @throws IOException     IOException
     * @throws WriterException WriterException
     */
    public BufferedImage parseBufferedImage(QrCodeRequest request) throws IOException, WriterException {
        return this.asBufferedImage(this.build(request));
    }

    /**
     * 生成二维码
     *
     * @param request     request
     * @param absFileName absFileName
     * @throws WriterException WriterException
     * @throws IOException     IOException
     */
    public void parseFile(QrCodeRequest request, String absFileName) throws WriterException, IOException {
        File file = new File(absFileName);
        file.mkdirs();
        BufferedImage bufferedImage = this.asBufferedImage(this.build(request));
        ImageIO.write(bufferedImage, QrCodeOptions.DEF_PIC_TYPE, file);
    }

    /**
     * 将二维码转化为string
     *
     * @param qrCodeOptions qrCodeOptions
     * @return String
     * @throws IOException     IOException
     * @throws WriterException WriterException
     */
    private String asString(QrCodeOptions qrCodeOptions) throws IOException, WriterException {
        BufferedImage bufferedImage = asBufferedImage(qrCodeOptions);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, QrCodeOptions.DEF_PIC_TYPE, outputStream);
        return Base64Util.encode(outputStream);
    }

    /**
     * 生成二维码
     *
     * @param qrCodeConfig qrCodeConfig
     * @return BufferedImage
     * @throws WriterException WriterException
     */
    private BufferedImage asBufferedImage(QrCodeOptions qrCodeConfig) throws WriterException {
        BitMatrixEx bitMatrix = QrCodeUtil.encode(qrCodeConfig);
        return QrCodeUtil.toBufferedImage(qrCodeConfig, bitMatrix);
    }

    /**
     * 构建生成二维码的参数
     *
     * @param request 参数
     * @return 结果
     * @throws IOException IOException
     */
    private QrCodeOptions build(QrCodeRequest request) throws IOException {  //NOSONAR
        //常量
        final int num3 = 3;
        final int num4 = 4;
        //处理参数
        QrCodeOptions op = new QrCodeOptions();
        op.setMsg(request.getContent());
        op.setW(request.getSize() == null ? (op.getH() == null ? QrCodeOptions.DEF_SIZE : op.getH()) : request.getSize());  //NOSONAR
        op.setH(request.getSize() == null ? (op.getW() == null ? QrCodeOptions.DEF_SIZE : op.getW()) : request.getSize()); //NOSONAR
        // 设置精度参数
        Map<EncodeHintType, Object> hints = new HashMap<>(num3); //NOSONAR
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, request.getCharset());
        hints.put(EncodeHintType.MARGIN, request.getPadding() == null ? 1 : request.getPadding() < 0 ? 0 : request.getPadding() > num4 ? num4 : request.getPadding()); //NOSONAR
        op.setHints(hints);
        //绘制二维码信息的样式
        DrawOptions drawOptions = new DrawOptions();
        drawOptions.setDrawStyle(StringUtils.isBlank(request.getDrawStyle()) ? DrawStyle.RECT : DrawStyle.getDrawStyle(request.getDrawStyle()));
        drawOptions.setBgColor(Color.WHITE);
        drawOptions.setPreColor(Color.BLACK);
        drawOptions.setEnableScale(false);
        if (drawOptions.getDrawStyle() == DrawStyle.IMAGE) {
            drawOptions.setImg(StringUtils.isBlank(request.getDrawImg()) ? null : ImageUtil.getByPath(request.getDrawImg()));
            if (!StringUtils.isBlank(request.getDrawSize4Img())) {
                drawOptions.setSize4Img(ImageUtil.getByPath(request.getDrawSize4Img()));
                drawOptions.setEnableScale(true);
            }
            if (!StringUtils.isBlank(request.getDrawRow2Img())) {
                drawOptions.setRow2Img(ImageUtil.getByPath(request.getDrawRow2Img()));
                drawOptions.setEnableScale(true);
            }
            if (!StringUtils.isBlank(request.getDrawCol2Img())) {
                drawOptions.setCol2img(ImageUtil.getByPath(request.getDrawCol2Img()));
                drawOptions.setEnableScale(true);
            }
        } else {
            final int a = 0xFFFFFFFF;
            final int b = 0xFF000000;
            drawOptions.setBgColor(ColorUtil.int2color(NumUtil.decode2int(request.getBgColor(), a)));
            drawOptions.setPreColor(ColorUtil.int2color(NumUtil.decode2int(request.getPreColor(), b)));
        }
        op.setDrawOptions(drawOptions);
        //logo 相关
        final int num12 = 12;
        LogoOptions logoOptions = new LogoOptions();
        logoOptions.setLogoStyle(LogoStyle.NORMAL);
        logoOptions.setBorder(false);
        logoOptions.setRate(num12);
        if (StringUtils.isNotBlank(request.getLogo())) {
            logoOptions.setLogo(ImageUtil.getByPath(request.getLogo()));
            logoOptions.setRate(request.getLogoRate() == null ? num12 : request.getLogoRate());
            logoOptions.setBorder(request.isLogoBorder());
            logoOptions.setLogoStyle(StringUtils.isBlank(request.getLogoStyle()) ? LogoStyle.NORMAL : LogoStyle.getStyle(request.getLogoStyle())); //NOSONAR
            if (StringUtils.isNotBlank(request.getLogoBorderColor())) {
                logoOptions.setBorder(true);
                logoOptions.setBorderColor(ColorUtil.int2color(NumUtil.decode2int(request.getLogoBorderColor(), null)));
            }
            op.setLogoOptions(logoOptions);
        }
        //探测图形
        DetectOptions detectOptions = new DetectOptions();
        if (StringUtils.isNotBlank(request.getDetectImg())) {
            detectOptions.setDetectImg(ImageUtil.getByPath(request.getDetectImg()));
            op.setDetectOptions(detectOptions);
        } else {
            if (StringUtils.isNotBlank(request.getDetectInColor())) {
                detectOptions.setInColor(ColorUtil.int2color(NumUtil.decode2int(request.getDetectInColor(), null)));
            }
            if (StringUtils.isNotBlank(request.getDetectOutColor())) {
                detectOptions.setOutColor(ColorUtil.int2color(NumUtil.decode2int(request.getDetectOutColor(), null)));
            }
        }
        if (detectOptions.getOutColor() == null && detectOptions.getInColor() == null) {
            detectOptions.setInColor(drawOptions.getPreColor());
            detectOptions.setOutColor(drawOptions.getPreColor());
        } else if (detectOptions.getOutColor() == null) {
            detectOptions.setOutColor(detectOptions.getOutColor());
        } else if (detectOptions.getInColor() == null) {
            detectOptions.setInColor(detectOptions.getInColor());
        }
        op.setDetectOptions(detectOptions);
        // 背景相关
        BgImgOptions bgImgOptions = new BgImgOptions();
        bgImgOptions.setBgImgStyle(BgImgStyle.OVERRIDE);
        bgImgOptions.setOpacity(0.85f);
        if (StringUtils.isNotBlank(request.getBgImg())) {
            bgImgOptions.setBgImg(ImageUtil.getByPath(request.getBgImg()));
            bgImgOptions.setBgw(request.getBgw() == null ? 0 : request.getBgw());
            bgImgOptions.setBgh(request.getBgh() == null ? 0 : request.getBgh());
            BgImgStyle style = BgImgStyle.getStyle(request.getBgStyle());
            if (style == BgImgStyle.FILL) {
                bgImgOptions.setStarty(request.getBgy() == null ? 0 : request.getBgy());
                bgImgOptions.setStartx(request.getBgx() == null ? 0 : request.getBgx());
            } else {
                final float a = 0.85f;
                bgImgOptions.setOpacity(request.getBgOpacity() == null ? a : request.getBgOpacity());
            }
            op.setBgImgOptions(bgImgOptions);
        }
        //返回
        return op;
    }
}
