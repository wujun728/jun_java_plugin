package org.cgfalcon.fluentexcel.render;

/**
 * Author: falcon.chu
 * Date: 13-6-9
 * Time: 下午4:09
 */

/**
 * Excel 样式类型
 */
public enum ExcelFormat {

    xls("xls"), xlsx("xlsx");

    private String name;

    private ExcelFormat(String simpleName) {
        this.name = simpleName;
    }

    public String getName() {
        return name;
    }

    public static ExcelFormat lookup(String lang) {
        if (lang == null) {
            return ExcelFormat.xlsx;
        }

        if (lang.equals("xls")) {
            return ExcelFormat.xls;
        } else {
            return ExcelFormat.xlsx;
        }
    }
}
