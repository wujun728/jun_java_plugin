package org.cgfalcon.fluentexcel.render;



/**
 * Author: falcon.chu
 * Date: 13-6-9
 * Time: 下午4:08
 */
public class ExcelRenderFactory {

    private ExcelRenderFactory(){

    }

    public static Render getRender(ExcelFormat type) {
        try {
            switch (type) {
                case xls:
                    return new XlsRender();
                case xlsx:
                    return new XlsxRender();
                default:
                    return new XlsxRender();
            }
        } catch (NullPointerException ne) {
            return new XlsxRender();
        }

    }
}
