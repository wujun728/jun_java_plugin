package vo;

import com.sanri.excel.poi.annotation.ExcelColumn;
import com.sanri.excel.poi.annotation.ExcelExport;
import com.sanri.excel.poi.converter.DefaultBooleanStringConverter;

@ExcelExport
public class ConverterBean {
    @ExcelColumn(value = "是否成功",order = 0,converter = DefaultBooleanStringConverter.class)
    private boolean success;
    @ExcelColumn(value = "性别",order = 1,converter = GenderConverter.class)
    private int gender;

    public ConverterBean() {
    }

    public ConverterBean(boolean success) {
        this.success = success;
    }

    public ConverterBean(boolean success, int gender) {
        this.success = success;
        this.gender = gender;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
