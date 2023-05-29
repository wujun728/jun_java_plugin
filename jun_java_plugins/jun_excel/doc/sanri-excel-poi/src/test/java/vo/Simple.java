package vo;

import com.sanri.excel.poi.annotation.ExcelColumn;
import com.sanri.excel.poi.annotation.ExcelExport;
import com.sanri.excel.poi.annotation.ExcelImport;

import java.util.Date;

/**
 * 用于测试各种类型是否正常展示
 */
@ExcelExport
@ExcelImport(startRow = 1)
public class Simple {
    @ExcelColumn(value = "年龄",order = 2)
    private int age;
    @ExcelColumn(value = "级别",order = 1)
    private Integer level;
    @ExcelColumn(value = "姓名",order = 0,chineseWidth = true)
    private String name;
    @ExcelColumn(value = "生日",order = 3)
    private Date birthday;
    @ExcelColumn(value = "序号",order = 4,hidden = true)
    private long id;
    @ExcelColumn(value = "是否成功",order = 5)
    private boolean success;
    @ExcelColumn(value = "薪水",order = 6,precision = 2)
    private double money;
    @ExcelColumn(value = "奖金",order = 7,precision = 2)
    private float comm;

    public Simple() {
    }

    public Simple(int age, Integer level, String name, Date birthday, long id, boolean success, double money, float comm) {
        this.age = age;
        this.level = level;
        this.name = name;
        this.birthday = birthday;
        this.id = id;
        this.success = success;
        this.money = money;
        this.comm = comm;
    }

    public int getAge() {
        return age;
    }

    public Integer getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public long getId() {
        return id;
    }

    public boolean isSuccess() {
        return success;
    }

    public double getMoney() {
        return money;
    }

    public float getComm() {
        return comm;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setComm(float comm) {
        this.comm = comm;
    }
}
