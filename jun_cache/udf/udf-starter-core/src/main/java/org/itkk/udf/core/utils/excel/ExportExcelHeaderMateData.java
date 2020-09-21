package org.itkk.udf.core.utils.excel;

import lombok.Data;

/**
 * Excel导出表头定义
 *
 * @author Administrator
 */
@Data
public class ExportExcelHeaderMateData {

    /**
     * 表头名称
     */
    private String headerName;

    /**
     * 表头列宽(算法:最大字数)
     */
    private int headerWidth;

    /**
     * 构造函数
     *
     * @param headerName  表头名称
     * @param headerWidth 表头列宽(算法:最大字数)
     */
    public ExportExcelHeaderMateData(String headerName, int headerWidth) {
        this.headerName = headerName;
        this.headerWidth = headerWidth;
    }

}
