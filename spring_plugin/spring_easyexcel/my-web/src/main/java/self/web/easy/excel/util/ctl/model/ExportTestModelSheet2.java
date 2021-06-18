package self.web.easy.excel.util.ctl.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import xiong.utils.SheetName;

@SheetName("2")
@Data
public class ExportTestModelSheet2 {
    @ExcelProperty(index = 0 ,value = "标题")
    private String title;
    @ExcelProperty(index = 1 ,value = "描述")
    private String desc;

    public ExportTestModelSheet2(Integer id) {
        this.title = "标题"+id;
        this.desc = "描述"+id;
    }

    /**
     * 无参构造函数是导入的必要条件
     */
    public ExportTestModelSheet2() {}
}
