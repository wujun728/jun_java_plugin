package com.sanri.excel.poi.handler;

import com.sanri.excel.poi.ColumnConfig;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.Method;

public interface ErrorRowHandler<T> {
    /**
     * 处理错误行数据，返回是否需要继续处理
     * true: 继续导入 ，false: 停止处理
     * @param cell
     * @param writeMethod
     * @param columnConfig
     * @return
     */
    boolean handlerRow(Cell cell, Method writeMethod, ColumnConfig columnConfig);
}
