import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;

import java.time.LocalDateTime;
import java.util.Date;

public class TestIsDate {
    public static void main(String[] args) {
        removeTimeTStr();

        checkIsDate();
    }

    private static void removeTimeTStr() {
        CopyOptions copyOptions = CopyOptions.create().setIgnoreNullValue(true);
        copyOptions.setFieldValueEditor((fieldName,fieldValue) -> {
            if(fieldName.equals("updateAt") ){
                if(ObjectUtil.isNotEmpty(fieldValue)){
                    DateTime dataTime = DateUtil.parse(fieldValue.toString());
                    return dataTime.toString();
                }
            }else{
                return fieldValue;
            }
            return fieldValue;
        });
        //BeanUtil.copyProperties(entity,resp,copyOptions);
    }

    private static void checkIsDate() {
        String dateStr = "2023-01-01T12:00:00";
        try {
            DateTime dataTime = DateUtil.parse(dateStr); // 尝试解析字符串
            System.out.println(dateStr + " 是日期时间格式");
            System.out.println(dataTime.toString() + " 是日期时间格式");
            System.out.println(dataTime.toDateStr() + " 是日期时间格式");
            System.out.println(dataTime.toTimeStr() + " 是日期时间格式");
        } catch (Exception e) {
            System.out.println(dateStr + " 不是日期时间格式");
        }
    }
}
