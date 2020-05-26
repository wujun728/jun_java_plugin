package org.cgfalcon.fluentexcel.render;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.cgfalcon.fluentexcel.entity.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: falcon.chu
 * Date: 13-6-9
 * Time: 下午4:15
 */
public abstract class Render {

    protected Gson gson = new Gson();

    /**
     * 单元格样式定义的时候采用JSON的方式定义, 本方法
     * 将定义单元格样式的Json转化为CellStyle， 样式定义
     * {
     * 'font':{'size': short, 'name': '', 'color': Color, 'underLine': short, 'boldWight': short,
     * 'italic': boolean, 'dataformat': '' },
     * 'verticalAlign': short,
     * 'align': short,
     * 'fgColor': {'r':short, 'g':short, 'b':short},
     * 'bgColor': {'r':short, 'g':short, 'b':short}
     * <p/>
     * <p/>
     * }
     *
     * @param cellStylePattern
     * @return
     */
    protected abstract CellStyle parse(Workbook wb, String cellStylePattern);

    protected abstract CellStyle parse(Workbook wb, CellStyleBean cellStyleBean);

//    /**
//     * Create a new WorkBook instance, and overwrite 'workbook' field in Render Class
//     * @return
//     */
//    public abstract Workbook createWorkBook();

    /**
     * return workbook field in this Render Class
     */
    public abstract Workbook getWorkBook();

    /**
     * Get a Workbook from a Existing Excel File
     *
     * @param path
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public abstract Workbook getWorkBook(String path) throws IOException, InvalidFormatException;

    /* 由各子类确定 */
    protected Workbook workbook;
    protected ExcelFormat excelFormat;

    private static Logger logger = Logger.getLogger(Render.class);

    protected Map<String, String[]> digitFormatComponent = new HashMap<String, String[]>();
    protected Table<String, String, String> digitFormatTable = HashBasedTable.create();

    {
        digitFormatComponent.put("0", new String[]{"_(* #,##0", "_);_(* (#,##0", ");_(* \"-\"_)"});
        digitFormatComponent.put("1", new String[]{"_(* #,##0.0", "_);_(* (#,##0.0", ");_(* \"-\"_)"});
        digitFormatComponent.put("2", new String[]{"_(* #,##0.00", "_);_(* (#,##0.00", ");_(* \"-\"_);_(@_)"});
        digitFormatComponent.put("3", new String[]{"_(* #,##0.000", "_);_(* (#,##0.000", ");_(* \"-\"_);_(@_)"});
        digitFormatComponent.put("4", new String[]{"_(* #,##0.0000", "_);_(* (#,##0.0000", ");_(* \"-\"_);_(@_)"});
        digitFormatComponent.put("5", new String[]{"_(* #,##0.00000", "_);_(* (#,##0.00000", ");_(* \"-\"_);_(@_)"});
        digitFormatComponent.put("6", new String[]{"_(* #,##0.000000", "_);_(* (#,##0.000000", ");_(* \"-\"_);_(@_)"});
        digitFormatComponent.put("7", new String[]{"_(* #,##0.0000000", "_);_(* (#,##0.0000000", ");_(* \"-\"_);_(@_)"});
        digitFormatComponent.put("8", new String[]{"_(* #,##0.00000000", "_);_(* (#,##0.00000000", ");_(* \"-\"_);_(@_)"});
        digitFormatComponent.put("9", new String[]{"_(* #,##0.000000000", "_);_(* (#,##0.000000000", ");_(* \"-\"_);_(@_)"});

        String[] suffixs = new String[]{"", "\"x\"", "\"%\""};
        String finalStyle = "";
        for (String key : digitFormatComponent.keySet()) {

            for (String suffix : suffixs) {
                finalStyle = StringUtils.join(digitFormatComponent.get(key), suffix);
                /* use digit and suffix as key, which map only one data format*/
                digitFormatTable.put(key, suffix, finalStyle);
            }
        }

    }


    /**
     * 根据 小数位 和 小数后缀 获得数字样式
     *
     * @param digit  小数位, 0 ~ 9
     * @param suffix 小数后缀, "", "%", "x"
     * @return
     */
    public String getDataFormat(String digit, String suffix) {
        return digitFormatTable.get(digit, suffix);
    }

    /**
     * 将Jsong定义的单元格样式 cellStylePattern 转化为 CellStyle
     *
     * @param cellStylePattern
     * @return
     */
    public CellStyle parse(String cellStylePattern) {
        if (workbook == null) {
            throw new IllegalStateException("Null workbook. Please create Workbook first!");
        }
        return parse(this.workbook, cellStylePattern);
    }

    /**
     * 将对象化的样式 CellStyleBean 转化为 CellStyle
     *
     * @param cellStyleBean
     * @return
     */
    public CellStyle parse(CellStyleBean cellStyleBean) {
        if (workbook == null) {
            throw new IllegalStateException("Null workbook. Please create Workbook first!");
        }
        return parse(this.workbook, cellStyleBean);
    }

    public void render(DataDoc docModel) {
        if (this.workbook == null) {
            throw new IllegalStateException("Null workbook. Please create Workbook first!");
        }
        render(this.workbook, docModel);

    }


    /**
     * 将抽象的StyleDataDoc转化为excel
     *
     * @param wb
     * @param docModel
     */
    private void render(Workbook wb, DataDoc docModel) {
        List<DataSheet> sheets = docModel.getSheets();
        for (int i = 0, size = sheets.size(); i < size; i++) {
            DataSheet sheet = sheets.get(i);
            /* sheet存在则直接返回, 不存在则创建新的sheet */
            Sheet xlsxSheet = wb.getSheet(sheet.getSheetName()) == null ? wb.createSheet(sheet.getSheetName()) : wb
                    .getSheet(sheet.getSheetName());


            /* 设置列宽 */
            Map<Integer, Integer> widthMap = sheet.getColWidth();
            for (Integer key : widthMap.keySet()) {
                xlsxSheet.setColumnWidth(key, widthMap.get(key));
            }

            List<DataBlock> blocks = sheet.getBlocks();

            int maxRow = 8;
            int maxCol = 1;
            for (int n = 0, sizen = blocks.size(); n < sizen; n++) {
                DataBlock block = blocks.get(n);
                List<DataRow> rows = block.getRows();

                if (rows == null) {
                    continue;
                }

                /* 行偏移量 */
                int startRow = block.getStartRow();
                for (int r = 0, sizer = rows.size(); r < sizer; r++) {
                    DataRow row = rows.get(r);
                    int realRowIndex = r + startRow;
                    if (realRowIndex > maxRow) {
                        maxRow = realRowIndex;
                    }

                    /* 尝试取下标为 <realRowIndex> 的 Row, 不存在则创建新的 Row */
                    Row xlsxRow = xlsxSheet.getRow(realRowIndex) == null ? xlsxSheet.createRow(realRowIndex) :
                            xlsxSheet.getRow(realRowIndex);
                    List<DataCell> cells = row.getCells();

                    if (cells == null) {
                        continue;
                    }
                    if (row.getHeight() != null) {
                        xlsxSheet.getRow(realRowIndex).setHeight(row.getHeight());
                    }

                    /* 列偏移量 */
                    int startCol = row.getStartCol();
                    for (int c = 0, sizec = cells.size(); c < sizec; c++) {

                        DataCell cell = cells.get(c);
                        if (cell == null) {
                            continue;
                        }

                        int realColIndex = c + startCol;
                        if (realColIndex > maxCol) {
                            maxCol = realColIndex;
                        }

                        /* 尝试取列标为 <realColIndex> 的 Cell, 不存在则创建新的 Cell */
                        Cell xlsxCell = xlsxRow.getCell(realColIndex) == null ? xlsxRow.createCell(realColIndex) :
                                xlsxRow.getCell(realColIndex);

                        /* 设置单元格内容类型 */
                        if (cell.getType() != null) {
                            xlsxCell.setCellType(cell.getType());
                            if (cell.getType() == Cell.CELL_TYPE_NUMERIC) {
                                try {
                                    xlsxCell.setCellValue(Double.valueOf(cell.getContent()));
                                } catch (NumberFormatException e) {
                                    xlsxCell.setCellValue(cell.getContent());
                                }

                            } else {
                                xlsxCell.setCellValue(cell.getContent());
                            }


                        } else {
                            xlsxCell.setCellValue(cell.getContent());
                        }

                        /* 将样式赋予Cell */
                        if (cell.getCss() != null) {
                            xlsxCell.setCellStyle(cell.getCss());
                        } else {
                            /* css 为空的情况下使用 rawStyle 初始化样式*/
                            if (cell.getRawStyle() != null && !cell.getRawStyle().equals("")) {
                                CellStyle style = parse(cell.getRawStyle());
                                xlsxCell.setCellStyle(style);
                            }
                        }
                    }
                }
            }

            /*
            * 设置合并单元格
            * */
            List<MergerRegion> mergerRegions = sheet.getMergerRegions();
            for (int i_range = 0; i_range < mergerRegions.size(); i_range++) {
                xlsxSheet.addMergedRegion(buildMergedRegion(mergerRegions.get(i_range)));
            }


        }

    }

    private CellRangeAddress buildMergedRegion(MergerRegion mergerRegion) {
        return new CellRangeAddress(mergerRegion.getFromRow(), mergerRegion.getToRow(), mergerRegion.getFromCol(),
                mergerRegion.getToCol());
    }

    /**
     * Generate A excel file with giving workbook and DataDoc
     *
     * @param wb
     * @param docModel
     * @return
     */
    public static String produceExcel(Workbook wb, DataDoc docModel) {
        String root = docModel.getDocDir();
        File dirFile = new File(root);

        if (!dirFile.isDirectory()) {
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        } else {
            return "";
        }

        String fileName = docModel.getDocName() + "__" + System.currentTimeMillis() + "." + docModel
                .getDocType();
        String filePath = FilenameUtils.concat(root, fileName);
        FileOutputStream fileos = null;
        try {
            fileos = new FileOutputStream(filePath);
            wb.write(fileos);
            return filePath;
        } catch (Exception e) {
            logger.error("Error Producing Xlsx.", e);
        } finally {
            if (fileos != null) {
                try {
                    fileos.close();
                } catch (IOException e) {
                    logger.error("Error while closing FileOutputStream.", e);
                }
            }

        }
        return "";
    }

    /**
     * Generate A excel file with giving DataDoc, and use workbook field in this Object Instance as another parameter.
     * if workbook is null, will create a new one;
     *
     * @param docModel
     * @return
     */
    public String produceExcel(DataDoc docModel) {
        if (this.workbook == null) {
            throw new IllegalStateException("Null workbook. Please create Workbook first!");
        }
        return produceExcel(this.workbook, docModel);
    }

    /**
     * 按照千分位分割数字
     *
     * @param abs
     * @return
     */
    protected String processNum2SplitStyle(long abs) {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(abs);
    }

    protected String processNum2SplitStyle(double abs) {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(abs);
    }

    protected static String processDouble2FixedFloatStyle(double abs) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(abs);
    }

    protected static Double processString2Double(String source) {
        DecimalFormat df = new DecimalFormat("0.00");
        Double decimal = null;
        try {
            decimal = (Double) df.parse(source);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return decimal;
    }

    /**
     * convert first letter of each word to upcase
     *
     * @param value
     * @return
     */
    protected String convertCase(String value) {
        value = value.toLowerCase();
        String[] values = value.split(" ");

        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append((new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1))
                    .toString());
            sb.append(" ");
        }
        return sb.toString().trim();
    }

}
