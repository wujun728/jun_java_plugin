package vo;

import com.sanri.excel.poi.converter.ExcelConverter;

public class GenderConverter implements ExcelConverter<Integer,String> {
    @Override
    public String convert(Integer source) {
        return source == 1 ? "男":"女";
    }
}
