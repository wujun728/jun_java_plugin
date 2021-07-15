package com.cddang.violation.ocr;

import cn.easyproject.easyocr.EasyOCR;
import cn.easyproject.easyocr.ImageClean;
import cn.easyproject.easyocr.Type;
import com.cddang.violation.common.ViolationConstant;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
@Slf4j
public class CaptchaOCR {
    public String matchCaptcha = "A-Za-z0-9";
    private Integer captchaLen = 4;

    public CaptchaOCR() {
    }

    public CaptchaOCR(String matchCaptcha) {
        this.matchCaptcha = matchCaptcha;
    }

    public CaptchaOCR(String matchCaptcha, Integer captchaLen) {
        this.matchCaptcha = matchCaptcha;
        this.captchaLen = captchaLen;
    }

    private boolean inputStreamToFile(InputStream fis, String file) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] e = new byte[8192];
            boolean len = true;

            int len1;
            while ((len1 = bis.read(e)) != -1) {
                bos.write(e, 0, len1);
            }

            boolean var7 = true;
            return var7;
        } catch (FileNotFoundException var24) {
            var24.printStackTrace();
        } catch (IOException var25) {
            var25.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException var23) {
                var23.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException var22) {
                var22.printStackTrace();
            }
        }
        return false;
    }

    public String discernAndAutoCleanImage(String fromImage, Type imageType) {
        EasyOCR ocr = new EasyOCR();
        ocr.setTesseractOptions("-l eng --psm 7");
        return discernAndAutoCleanImage(ocr, fromImage, imageType, 1, 1, true);
    }

    public String discernAndAutoCleanImage(EasyOCR ocr, String fromImage, Type imageType, double autoCleanImageWidthRatio, double autoCleanImageHeightRatio, boolean flag) {
        String res = "";
        boolean urlFlag = false;
        String ex = "";
        int lastDot = fromImage.lastIndexOf(".");
        if (lastDot != -1) {
            ex = fromImage.substring(lastDot);
        }
        try {
            try {
                if (fromImage.startsWith("http://") || fromImage.startsWith("ftp://")) {
                    urlFlag = true;
                    URL e = new URL(fromImage);
                    InputStream imageClean = e.openStream();
                    fromImage = ViolationConstant.tempDir + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + ex;
                    if (!inputStreamToFile(imageClean, fromImage)) {
                        imageClean.close();
                        (new File(fromImage)).delete();
                        String var14 = res;
                        return var14;
                    }
                }
                String e1 = ViolationConstant.tempDir + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + ex;
                ImageClean imageClean1 = new ImageClean(imageType, autoCleanImageWidthRatio, autoCleanImageHeightRatio, 0);
//                log.info(fromImage);
                imageClean1.cleanImage(fromImage, e1);
                if (flag) {
                    List<String> codeList = new ArrayList<String>();
                    for (double imageWidthRatio = 0.8; imageWidthRatio <= 2; imageWidthRatio += 0.3) {
                        for (double imageHeightRatio = 0.8; imageHeightRatio <= 2.8; imageHeightRatio += 0.3) {
                            String ocrStr = discernAndAutoCleanImage(ocr, fromImage, imageType, imageWidthRatio, imageHeightRatio, false);
//                            log.info(ocrStr);
                            codeList.add(ocrStr);
                        }
                    }
//                    log.info(codeList.size());
                    res = electMaxCaptcha(codeList.toArray());
                    urlFlag = true;
                } else {
                    res = ocr.discern(e1);
                }
                (new File(e1)).delete();
            } catch (MalformedURLException var19) {
                var19.printStackTrace();
            } catch (IOException var20) {
                var20.printStackTrace();
            }
            return res;
        } finally {
            if (urlFlag) {
                if((new File(fromImage)).exists()){
                    (new File(fromImage)).delete();
                }
            }
        }
    }

    public String electMaxCaptcha(Object[] array) {
        //设置中间数用来比较
        String m = "";
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].toString().replaceAll("[^" + matchCaptcha + "]", "");
        }
        //排序，升序
        Arrays.sort(array);
        //保存结果map，n的元素为key，出现次数为value
        Map nums = new HashMap();
        //循环，当某个数已经在map里时，将次数加一
        for (int i = 0; i < array.length; i++) {
            String tstr = array[i].toString();
            if (tstr.length() != captchaLen) {
                continue;
            }
            if (m.equals(tstr)) {
                int v = (Integer) nums.get(m);
                v++;
                nums.put(m, v);
            } else {
                nums.put(tstr, 1);
            }
            m = tstr;
        }
        //遍历得到最多次数的数
        Iterator iterator = nums.keySet().iterator();
        int maxNumber = 0;
        String maxValue = "";
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            int value = (Integer) nums.get(key);
            if (value > maxNumber) {
                maxNumber = value;
                maxValue = key;
            }
        }
        log.info("出现次数最多的数为：" + maxValue + ",出现次数为：" + maxNumber);
        return maxValue;
    }

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     *
     * @param iconPath   水印图片路径
     * @param targerPath 目标图片路径
     * @param degree     水印图片旋转角度
     * @param clarity    透明度(小于1的数)越接近0越透明
     */
    public void waterMarkImageByIcon(String iconPath, String targerPath, Integer degree, float clarity) {
        OutputStream os = null;
        try {
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            int swidth = imgIcon.getIconHeight();
            int sheight = imgIcon.getIconHeight();
            if (imgIcon.getIconWidth() > imgIcon.getIconHeight()) {
                swidth = imgIcon.getIconWidth();
                sheight = imgIcon.getIconWidth();
            }
            swidth = sheight = sheight * 2;

//            Image srcImg = ImageIO.read(new File(srcImgPath));
//            log.info("width:" + srcImg.getWidth(null));
//            log.info("height:" + srcImg.getHeight(null));

            BufferedImage srcImg = new BufferedImage(swidth, sheight, BufferedImage.TYPE_INT_RGB);
            Graphics2D dg = (Graphics2D) srcImg.createGraphics();
            dg.fillRect(0, 0, swidth, sheight);//填充整个屏幕

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }

            float alpha = clarity; // 透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 得到Image对象。
            Image img = imgIcon.getImage();
            // 表示水印图片的位置
            g.drawImage(img, swidth / 2 - imgIcon.getIconWidth() / 2, sheight / 2 - imgIcon.getIconHeight() / 2, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            os = new FileOutputStream(targerPath);
            // 生成图片
            ImageIO.write(buffImg, "JPG", os);
//            log.info("添加水印图片完成!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 灰色干扰背景-验证码清洗
     */
    public static void captchaClear(BufferedImage bi, String vcodeImg) throws Exception {
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                Color color = new Color(bi.getRGB(x, y));
                if (color.getRed() == color.getGreen() && color.getGreen() == color.getBlue()
                        ) {
//                    System.out.print(0);
                    bi.setRGB(x, y, new Color(255, 255, 255).getRGB());
                } else {
//                    System.out.print(1);
                    bi.setRGB(x, y, new Color(0, 0, 0).getRGB());
                }
            }
//            System.out.println();
        }
        ImageIO.write(bi, "png", new File(vcodeImg));
    }

    public static void main(String[] args) throws Exception {
//        EasyOCR ocr = new EasyOCR();
//        ocr.setTesseractOptions("-l eng --psm 7");
//        log.info(new CaptchaOCR("0-9").discernAndAutoCleanImage("D:/python/124.png", ImageType.CAPTCHA_NORMAL));
//        String iconPath = "d:\\123.gif";
//        String targerPath = "D:/python/do-captcha.png";
////
////        BufferedImage image = new BufferedImage(400, 200, BufferedImage.TYPE_INT_RGB);
////        Graphics2D dg = (Graphics2D) image.createGraphics();
////        dg.fillRect(0, 0, 400, 400);//填充整个屏幕
////
////        ImageIO.write(image, "png", new File(srcImgPath));
//
//        for (int i = -15; i < 15; i += 3) {
//            String targetPath = "D:/python/vcode/" + UUID.randomUUID().toString().replaceAll("-", "") + ".png";
//            // 验证码角度变换
//            waterMarkImageByIcon(iconPath, targetPath, i, 1);
//            log.info(discernAndAutoCleanImage(targetPath, ImageType.CAPTCHA_INTERFERENCE_LINE));
//        }
//        String s = "A-Za-z0-9";
//        log.info(".7 953".replaceAll("[^" + s + "]", ""));
//        log.info("1#info#2".replaceAll("#info#", "哈哈"));
        System.out.println(System.getProperty("java.io.tmpdir"));
    }
}
