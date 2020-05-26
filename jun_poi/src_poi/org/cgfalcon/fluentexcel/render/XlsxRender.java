package org.cgfalcon.fluentexcel.render;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import org.cgfalcon.fluentexcel.entity.Image;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: falcon.chu
 * Date: 13-6-9
 * Time: 下午4:07
 */

/**
 * Xlsx 格式 Excel 的渲染工具
 */
public class XlsxRender extends Render {

    private final Map<String, Font> fontPool = new HashMap<String, Font>();
    private final Map<String, CellStyle> cellStylePool = new HashMap<String, CellStyle>();

    public XlsxRender() {
        this.excelFormat = ExcelFormat.xlsx;
        this.workbook = new XSSFWorkbook();
    }


    @Override
    protected CellStyle parse(Workbook wb, String cellStylePattern) {
        CellStyleBean styleBean = gson.fromJson(cellStylePattern, CellStyleBean.class);
        return parse(wb, styleBean);
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
        cellStyle = (XSSFCellStyle) wb.createCellStyle();
        DataFormat dtFormat = wb.createDataFormat();
        initCellStyle(cellStyle, cellStyleBean, dtFormat);

        /* 处理字体样式 */
        FontStyleBean fontBean = cellStyleBean.getFont();
        String fontKey = gson.toJson(fontBean);
        Font font = (XSSFFont) fontPool.get(fontKey);
        if (font != null) {
            cellStyle.setFont(font);
            return cellStyle;
        }
        font = wb.createFont();

        initFont(font, fontBean);

        cellStyle.setFont(font);

        cellStylePool.put(styleKey, cellStyle);

        return cellStyle;
    }

    /**
     * 初始化字体样式
     *
     * @param font
     * @param fontBean
     */
    private void initFont(Font font, FontStyleBean fontBean) {
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
            ColorBean cbean = fontBean.getColor();

            ((XSSFFont) font).setColor(new XSSFColor(new Color(cbean.getR(), cbean.getG(), cbean.getB())));
        }

    }

    /**
     * 初始化单元格样式
     *
     * @param cellStyle
     * @param styleBean
     */
    private void initCellStyle(CellStyle cellStyle, CellStyleBean styleBean, DataFormat dtFormat) {
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
            ColorBean cbean = styleBean.getFgColor();
            ((XSSFCellStyle) cellStyle).setFillForegroundColor(new XSSFColor(new Color(cbean.getR(), cbean.getG(), cbean.getB())));
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        }

        if (styleBean.getBgColor() != null) {
            ColorBean cbean = styleBean.getBgColor();
            ((XSSFCellStyle) cellStyle).setFillBackgroundColor(new XSSFColor(new Color(cbean.getR(), cbean.getG(), cbean.getB())));
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        }

        if (styleBean.getWrapText() != null) {
            cellStyle.setWrapText(styleBean.getWrapText());
        }


    }


    public void generateLogo(Sheet sheet, int length, Workbook wb, String basePath, Image image) {
        if (image == null) {
            return;
        }

        try {
            sheet.addMergedRegion(new CellRangeAddress(image.getRowFrom(), (short) image.getRowTo() - 1,
                    image.getColFrom(), (short) image.getColTo() - 1));
            String imageFilePath = "d:/app/img";
            InputStream is = new FileInputStream(basePath + imageFilePath);
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            is.close();
            CreationHelper helper = wb.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0,
                    image.getColFrom(), image.getRowFrom(), image.getColTo(), image.getRowTo() + 1);
            Picture pict = drawing.createPicture(anchor, pictureIdx);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Workbook getWorkBook() {
        if (workbook == null) {
            workbook = new XSSFWorkbook();
        }
        return workbook;
    }


/*  暂时保留
    @Override
    public Workbook createWorkBook() {
        return new XSSFWorkbook();
    }*/

    @Override
    public Workbook getWorkBook(String path) throws IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(OPCPackage.open(new FileInputStream(path)));
        return workbook;
    }
}
