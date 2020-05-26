package org.cgfalcon.fluentexcel.style.impl;

import org.apache.poi.ss.usermodel.CellStyle;
import org.cgfalcon.fluentexcel.render.CellStyleBean;
import org.cgfalcon.fluentexcel.render.ColorBean;
import org.cgfalcon.fluentexcel.render.FontStyleBean;
import org.cgfalcon.fluentexcel.render.Render;
import org.cgfalcon.fluentexcel.style.CellStyleBuilder;
import org.cgfalcon.fluentexcel.style.FontBuilder;

/**
 * User: falcon.chu
 * Date: 13-9-23
 * Time: 下午5:44
 */
public class BaseCellStyleBuilder implements CellStyleBuilder {

    private Render render;
    private CellStyleBean cellStyleBean = new CellStyleBean();

    public BaseCellStyleBuilder() {

    }

    @Override
    public CellStyleBuilder withRender(Render render) {
        this.render = render;
        return this;
    }

    @Override
    public CellStyleBuilder verticalAlign(Short verticalAlign) {
        cellStyleBean.setVerticalAlign(verticalAlign);
        return this;
    }

    @Override
    public CellStyleBuilder align(Short alignType) {
        cellStyleBean.setAlign(alignType);
        return this;
    }

    @Override
    public CellStyleBuilder useFgColor(Short r, Short g, Short b) {
        cellStyleBean.setFgColor(new ColorBean(r, g, b));
        return this;
    }

    @Override
    public CellStyleBuilder useBgColor(Short r, Short g, Short b) {
        cellStyleBean.setBgColor(new ColorBean(r, g, b));
        return this;
    }

    @Override
    public CellStyleBuilder formatDataWith(String dataFormat) {
        cellStyleBean.setDataFormat(dataFormat);
        return this;
    }

    @Override
    public CellStyleBuilder wrapText() {
        cellStyleBean.setWrapText(true);
        return this;
    }

    @Override
    public CellStyleBuilder useFont(FontStyleBean fontStyleBean) {
        cellStyleBean.setFont(fontStyleBean);
        return this;
    }

    @Override
    public FontBuilder createFont() {
        return new BaseFontStyleBuilder(this);
    }

    @Override
    public CellStyle parse(String jsonStyle) {
        if (render == null) {
                throw new IllegalStateException("Null Render Object. Please verify render has been set correctly!");
        }
        return render.parse(jsonStyle);
    }

    @Override
    public CellStyle cellStyleOver() {
        if (render == null) {
            throw new IllegalStateException("Null Render Object. Please verify render has been set correctly!");
        }
        return render.parse(this.cellStyleBean);
    }
}
