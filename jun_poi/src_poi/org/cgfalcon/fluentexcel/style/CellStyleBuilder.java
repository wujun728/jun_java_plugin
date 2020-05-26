package org.cgfalcon.fluentexcel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.cgfalcon.fluentexcel.render.FontStyleBean;
import org.cgfalcon.fluentexcel.render.Render;


/**
 * User: falcon.chu
 * Date: 13-9-23
 * Time: 下午5:15
 */
public interface CellStyleBuilder {
    CellStyleBuilder withRender(Render render);
    CellStyleBuilder verticalAlign(Short verticalAlign);
    CellStyleBuilder align(Short alignType);
    CellStyleBuilder useFgColor(Short r, Short g, Short b);
    CellStyleBuilder useBgColor(Short r, Short g, Short b);
    CellStyleBuilder formatDataWith(String dataFormat);
    CellStyleBuilder wrapText();
    CellStyleBuilder useFont(FontStyleBean fontStyleBean);

    FontBuilder createFont();

    CellStyle parse(String jsonStyle);
    CellStyle cellStyleOver();
}
