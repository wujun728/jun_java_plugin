package org.cgfalcon.fluentexcel.render;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.cgfalcon.fluentexcel.entity.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: falcon.chu
 * Date: 13-6-9
 * Time: 下午4:07
 */

/**
 * Xls 格式Excel的渲染工具
 * */
public class XlsRender extends Render {

    private final Map<String, Font> fontPool = new HashMap<String, Font>();
    private final Map<String, CellStyle> cellStylePool = new HashMap<String, CellStyle>() ;
    /* poi xls 格式颜色的生成比较麻烦, 只能通过颜色索引指定颜色, 所以这里用个map保存已近用过的颜色
     *
     * key:  ColorBean.toString()
     * value: Color Index
      * */
    private Map<String, Short> colorPool = new HashMap<String, Short>();
    private Short colorIndex = 8;

    public XlsRender() {
        this.excelFormat = ExcelFormat.xls;
        this.workbook = new HSSFWorkbook();
    }


    @Override
    protected CellStyle parse(Workbook wb, String cellStylePattern) {
        CellStyleBean cellStyleBean = gson.fromJson(cellStylePattern, CellStyleBean.class);

        return parse(wb, cellStyleBean);
    }

    @Override
    protected CellStyle parse(Workbook wb, CellStyleBean cellStyleBean) {

        String styleKey = gson.toJson(cellStyleBean);
        /* 处理单元格样式 */
        CellStyle cellStyle = cellStylePool.get(styleKey);
        if (cellStyle != null) {
            CellStyle cloneStyle = wb.createCellStyle();
            cloneStyle.cloneStyleFrom(cellStyle);
            return cloneStyle;
        }
        cellStyle = (HSSFCellStyle)wb.createCellStyle();
        DataFormat dtFormat = wb.createDataFormat();

        HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette();
        initCellStyle(cellStyle, cellStyleBean, dtFormat, palette);

        /* 处理字体样式 */
        FontStyleBean fontStyleBean = cellStyleBean.getFont();
        if (fontStyleBean == null) {
            fontStyleBean = new FontStyleBean();
        }

        String fontKey = gson.toJson(fontStyleBean);
        Font font = (HSSFFont) fontPool.get(fontKey);
        if (font != null) {
            cellStyle.setFont(font);
            return cellStyle;
        }
        font = wb.createFont();
        initFont(font, fontStyleBean, palette);
        cellStyle.setFont(font);

        cellStylePool.put(styleKey, cellStyle);
        return cellStyle;
    }

    /**
     * 初始化字体样式
     * @param font
     * @param fontBean
     * @param palette xls 调色板
     */
    private void initFont(Font font, FontStyleBean fontBean, HSSFPalette palette) {
        font.setFontName(fontBean.getName());
        font.setFontHeightInPoints(fontBean.getSize());

        if (fontBean.getBoldWeight() != null) {
            font.setBoldweight(fontBean.getBoldWeight());
        }

        if (fontBean.getItalic() != null) {
            font.setItalic(fontBean.getItalic());
        }

        if (fontBean.getUnderLine() != null) {
            font.setUnderline(fontBean.getUnderLine());
        }

        if (fontBean.getColor() != null) {

            ((HSSFFont)font).setColor(getColor(fontBean.getColor(), palette));
        }

    }

    /**
     * 根据ColorBean 获得 颜色索引
     * @param colorBean
     * @param palette
     * @return
     */
    private Short getColor(ColorBean colorBean, HSSFPalette palette) {
        Short cIndex = colorPool.get(colorBean.toString());
        if (cIndex != null) {
            return cIndex;
        }
        colorIndex++;
        if (colorIndex > 64) {
            colorIndex = 64;
        }
        Short r = colorBean.getR();

        Short g = colorBean.getG();
        Short b = colorBean.getB();
        palette.setColorAtIndex(colorIndex, r.byteValue(), g.byteValue(),
                b.byteValue());
        cIndex = colorIndex;
        return cIndex;
    }

    /**
     * 初始化单元格样式
     * @param cellStyle
     * @param styleBean
     */
    private void initCellStyle(CellStyle cellStyle, CellStyleBean styleBean, DataFormat dtFormat, HSSFPalette palette) {
        if (styleBean.getDataFormat() != null) {
            cellStyle.setDataFormat(dtFormat.getFormat(styleBean.getDataFormat()));
        }

        if (styleBean.getAlign() != null) {
            cellStyle.setAlignment(styleBean.getAlign());
        }

        if (styleBean.getVerticalAlign() != null) {
            cellStyle.setVerticalAlignment(styleBean.getVerticalAlign());
        }

        if (styleBean.getFgColor() != null) {
            ((HSSFCellStyle)cellStyle).setFillForegroundColor(getColor(styleBean.getFgColor(), palette));
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        }

        if (styleBean.getBgColor() != null) {
            ((HSSFCellStyle)cellStyle).setFillBackgroundColor(getColor(styleBean.getBgColor(), palette));
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        }

        if (styleBean.getWrapText() != null) {
            cellStyle.setWrapText(styleBean.getWrapText());
        }


    }

    @Deprecated
    public void generateLogo(Sheet sheet, int length, Workbook wb, String basePath, Image image) {
        if (image == null) {
            return;
        }

        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        BufferedImage bufferImg;
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 220, 200,
                (short) image.getColFrom(), image.getRowFrom(), (short) (image.getColTo()), image.getRowTo());
        try {
            HSSFSheet xlsSheet = (HSSFSheet) sheet;
            String imageFilePath = "d:/app/tmp";
            bufferImg = ImageIO.read(new File(basePath + imageFilePath));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
//            xlsSheet.addMergedRegion(new Region(image.getRowFrom(), (short) image.getColFrom(), image.getRowTo(), (short) (image.getColTo() - 1)));
            xlsSheet.addMergedRegion(new CellRangeAddress(image.getRowFrom(), (short) image.getColFrom(), image.getRowTo(), (short) (image.getColTo() - 1)));
            HSSFPatriarch patriarch = xlsSheet.createDrawingPatriarch();
            patriarch.createPicture(anchor, wb.addPicture(
                    byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Workbook getWorkBook() {
        if (workbook == null) {
            workbook =  new HSSFWorkbook();
        }
        return workbook;
    }

/*  暂时保留
    @Override
    public Workbook createWorkBook() {
        return new HSSFWorkbook();
    }*/


    @Override
    public Workbook getWorkBook(String path) throws IOException {
        Workbook workbook = new HSSFWorkbook(new FileInputStream(path));
        return workbook;
    }
}
