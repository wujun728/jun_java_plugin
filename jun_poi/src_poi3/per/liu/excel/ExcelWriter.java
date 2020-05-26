package per.liu.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cdliujian1 on 2015/7/28.
 */
public abstract class ExcelWriter<T> {

    private Row row;
    /**
     * 字段与cell在row中的位置的map
     */
    private Map<String, Integer> idxMap = new HashMap<String, Integer>();
    /**
     * excel每行的列位置是从0开始的
     */
    private int idx = 0;

    /**
     * 动态添加一列到excel，同时确定这一列数据在row的位置idx
     *
     * @param header
     */
    private void addHeaders(String header) {
        if (!idxMap.containsKey(header)) {
            idxMap.put(header, this.idx);
            this.idx++;
        }
    }

    /**
     * 把一个对象写到一行数据里面
     *
     * @param obj
     */
    public abstract void writeRow(T obj);

    /**
     * @param header
     * @param value  调用value.toString()来作为值
     */
    public void addCell(String header, Object value) {
        try {
            header = header.trim();
            addHeaders(header);
            if (value == null || value.toString().equals("")) {
                return;
            }
            Cell cell = row.createCell(idxMap.get(header));
            String cellValue = value.toString().trim();
            cell.setCellValue(cellValue);
            if (cellValue.length() > 8) {
                //设置column宽度
                row.getSheet().setColumnWidth(idxMap.get(header), (value.toString().trim().getBytes().length) * 256);
            }
        } catch (Exception e) {
            //no op
        }
    }


    public void setRow(Row row) {
        this.row = row;
    }

    public Map<String, Integer> getIdxMap() {
        return idxMap;
    }
}
