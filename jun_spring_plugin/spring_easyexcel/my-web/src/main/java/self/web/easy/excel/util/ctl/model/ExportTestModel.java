package self.web.easy.excel.util.ctl.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import xiong.utils.SheetName;

@SheetName("1")
@Data
public class ExportTestModel {
    @ExcelProperty(index = 0 ,value = "标题")
    private String title;

    public ExportTestModel(Integer id) {
        this.title = "标题"+id;
    }

    /**
     * 无参构造函数是导入的必要条件
     */
    public ExportTestModel() {}
}
