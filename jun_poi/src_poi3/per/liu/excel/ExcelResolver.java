package per.liu.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Map;

/**
 * Created by cdliujian1 on 2015/7/28.
 */
public abstract class ExcelResolver<T> {

    /**
     * 读取excel当前行，反射注入
     */
    private Row row;
    /**
     * header位置map ，反射注入
     */
    private Map<String, Integer> idxMap;

    /**
     * 把excel里面的一行装换成一个对象
     *
     * @param obj
     * @return true 表示这行有效，会被添加到返回的list，反之这一行会被丢弃
     */
    public abstract boolean resolve(T obj);


    /**
     * 获取一个当前行(row)对应header的那个cell的内容，返回的始终是string
     * 如果想取出非string的值，可以重写方法用#getCell()方法自己处理
     *
     * @param header
     * @return
     */
    public String getCellValue(String header) {
        Cell cell = getCell(header);
        if (cell == null) {
            return null;
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        String value = cell.getRichStringCellValue().getString();
        return value == null ? null : value.trim();
    }

    /**
     * 获取一个当前行(row)对应header的那个cell
     *
     * @param header
     * @return
     */
    public Cell getCell(String header) {
        Integer idx = idxMap.get(header);
        if (idx == null) {
            return null;
        }
        return row.getCell(idx);
    }

}
