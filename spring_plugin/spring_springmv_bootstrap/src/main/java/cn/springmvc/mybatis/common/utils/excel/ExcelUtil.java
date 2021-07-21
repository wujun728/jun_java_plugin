package cn.springmvc.mybatis.common.utils.excel;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.springmvc.mybatis.common.exception.BusinessException;

/**
 * Excel工具类
 * 
 * @author Vincent.wang
 * 
 */
public class ExcelUtil {

    public static String getStringCellValue(Row row, int index) {
        if (row != null) {
            Cell cell = row.getCell(index);
            if (cell == null)
                return null;

            return cell.getStringCellValue();
        }
        return null;
    }

    public static Date getDateCellValue(Row row, int index) {
        if (row != null) {
            Cell cell = row.getCell(index);
            if (cell == null)
                return null;

            return cell.getDateCellValue();
        }
        return null;
    }

    /**
     * 给单元格填充数据
     * 
     * @param row
     *            行
     * @param cIndex
     *            单元格的X坐标
     * @param val
     *            数据
     */
    public static void setCell(Row row, int cIndex, Object val) throws BusinessException {
        try {
            if (row != null) {
                // 若单元格不存在，则创建
                Cell cell = row.getCell(cIndex);
                if (cell == null)
                    cell = row.createCell(cIndex);

                if (val == null) {
                    // cell.setCellValue(0);
                } else {
                    // 填充数据
                    if (val instanceof BigDecimal) {
                        BigDecimal dec = (BigDecimal) val;
                        cell.setCellValue(dec.doubleValue());
                    }
                    if (val instanceof String) {
                        String str = val.toString();
                        cell.setCellValue(str);
                    }
                    if (val instanceof Long) {
                        String str = val.toString();
                        cell.setCellValue(str);
                    }
                    if (val instanceof Date) {
                        Date date = (Date) val;
                        cell.setCellValue(date);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("EXCEL_UTIL_00007", e);
        }
    }

    /**
     * 复制单元格
     * 
     * @param srcCell
     * @param distCell
     */
    public static void copyCell(Cell srcCell, Cell distCell) throws BusinessException {
        try {
            distCell.setCellStyle(srcCell.getCellStyle());
            if (srcCell.getCellComment() != null) {
                distCell.setCellComment(srcCell.getCellComment());
            }
            distCell.setCellType(Cell.CELL_TYPE_NUMERIC);// 设置数据类型
            // distCell.setCellValue(0);//默认值给0
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("EXCEL_UTIL_00001", e);
        }
    }

    /**
     * 复制行
     * 
     * @param startRowIndex
     *            起始行
     * @param endRowIndex
     *            结束行
     * @param pPosition
     *            目标起始行位置
     */
    public static void copyRows(Sheet sheet, int startRow, int endRow, int pPosition) throws BusinessException {
        try {
            int pStartRow = startRow - 1;
            int pEndRow = endRow - 1;
            int targetRowFrom;
            int targetRowTo;
            int columnCount;
            CellRangeAddress region = null;
            int i;
            int j;
            if (pStartRow == -1 || pEndRow == -1) {
                return;
            }
            for (i = 0; i < sheet.getNumMergedRegions(); i++) {
                region = sheet.getMergedRegion(i);
                if ((region.getFirstRow() >= pStartRow) && (region.getLastRow() <= pEndRow)) {
                    targetRowFrom = region.getFirstRow() - pStartRow + pPosition;
                    targetRowTo = region.getLastRow() - pStartRow + pPosition;
                    CellRangeAddress newRegion = region.copy();
                    newRegion.setFirstRow(targetRowFrom);
                    newRegion.setFirstColumn(region.getFirstColumn());
                    newRegion.setLastRow(targetRowTo);
                    newRegion.setLastColumn(region.getLastColumn());
                    sheet.addMergedRegion(newRegion);
                }
            }
            for (i = pStartRow; i <= pEndRow; i++) {
                Row sourceRow = sheet.getRow(i);
                if (sourceRow != null) {
                    columnCount = sourceRow.getLastCellNum();
                    if (sourceRow != null) {
                        Row newRow = sheet.createRow(pPosition - pStartRow + i);
                        newRow.setHeight(sourceRow.getHeight());
                        for (j = 0; j < columnCount; j++) {
                            Cell templateCell = sourceRow.getCell(j);
                            if (templateCell != null) {
                                Cell newCell = newRow.createCell(j);
                                copyCell(templateCell, newCell);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("EXCEL_UTIL_00001", e);
        }
    }

    /**
     * 复制行样式
     * 
     * @param sheet
     *            当前sheet页
     * @param startRow
     *            源行
     * @param pPosition
     *            目标行
     */
    public static void copyRowStyle(Sheet sheet, int startRow, int pPosition) throws BusinessException {
        try {
            Row row = sheet.getRow(startRow);
            Row pRow = sheet.getRow(pPosition);
            if (row != null && pRow != null) {
                int index = sheet.getRow(startRow - 1).getLastCellNum();
                for (int i = 0; i < index; i++) {
                    copyCellStyle(row, i, pRow, i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("EXCEL_UTIL_00002", e);
        }

    }

    /**
     * 复制单元格样式
     * 
     * @param row
     *            起始行
     * @param sCell
     *            起始单元格坐标
     * @param pRow
     *            目标行
     * @param pPosition
     *            目标单元格坐标
     */
    public static void copyCellStyle(Row row, int sCell, Row pRow, int pPosition) throws BusinessException {
        try {
            Cell a = row.getCell(sCell);
            if (a != null) {
                Cell ca = pRow.getCell(pPosition);
                if (ca == null) {
                    ca = pRow.createCell(pPosition);
                }
                // 若不在，则创建目标单元格
                ca.setCellStyle(a.getCellStyle());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("EXCEL_UTIL_00003", e);
        }

    }

}
