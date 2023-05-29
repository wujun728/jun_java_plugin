package vo;

import com.sanri.excel.poi.annotation.ExcelColumn;
import com.sanri.excel.poi.annotation.ExcelExport;
import com.sanri.excel.poi.annotation.ExcelImport;

import java.util.Date;

/**
 * 测试继承类是否能够正确导出
 */
@ExcelExport
@ExcelImport(startRow = 1)
public class ExtendSimple extends Simple{
    @ExcelColumn(value = "身份证号",order = 8)
    private String idcard;

    public ExtendSimple() {
    }

    public ExtendSimple(int age, Integer level, String name, Date birthday, long id, boolean success, double money, float comm, String idcard) {
        super(age, level, name, birthday, id, success, money, comm);
        this.idcard = idcard;
    }

    public ExtendSimple(Simple simple) {
        this(simple.getAge(),simple.getLevel(),simple.getName(),simple.getBirthday(),simple.getId(),simple.isSuccess(),simple.getMoney(),simple.getComm(),null);
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
