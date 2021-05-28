package com.ibeetl.admin.console.web.dto;

import com.ibeetl.admin.core.entity.CoreDict;

/**
 * 字典数据导入,参考 dict_mapping.xml
 * @author Wujun
 *
 */
public class DictExcelImportData extends CoreDict {
	private Integer excelId;
	private Integer parentExcelId;

    public Integer getExcelId() {
        return excelId;
    }

    public void setExcelId(Integer excelId) {
        this.excelId = excelId;
    }

    public Integer getParentExcelId() {
        return parentExcelId;
    }

    public void setParentExcelId(Integer parentExcelId) {
        this.parentExcelId = parentExcelId;
    }
    
	
}
