package org.cgfalcon.fluentexcel.style.impl;

import org.cgfalcon.fluentexcel.render.ColorBean;
import org.cgfalcon.fluentexcel.render.FontStyleBean;
import org.cgfalcon.fluentexcel.style.CellStyleBuilder;
import org.cgfalcon.fluentexcel.style.FontBuilder;

/**
 * User: falcon.chu
 * Date: 13-9-23
 * Time: 下午5:48
 */
public class BaseFontStyleBuilder implements FontBuilder {

    private CellStyleBuilder cellStyleBuilder;
    private FontStyleBean fontStyleBean = new FontStyleBean();

    public BaseFontStyleBuilder(CellStyleBuilder cellStyleBuilder) {
        this.cellStyleBuilder = cellStyleBuilder;
    }

    @Override
    public FontBuilder size(Short fontSize) {
        fontStyleBean.setSize(fontSize);
        return this;
    }

    @Override
    public FontBuilder fontName(String fontName) {
        fontStyleBean.setName(fontName);
        return this;
    }

    @Override
    public FontBuilder withUnderLine(Byte underLineType) {
        fontStyleBean.setUnderLine(underLineType);
        return this;
    }

    @Override
    public FontBuilder useColor(Short r, Short g, Short b) {
        fontStyleBean.setColor(new ColorBean(r, g, b));
        return this;
    }

    @Override
    public FontBuilder boldWeight(Short boldWeight) {
        fontStyleBean.setBoldWeight(boldWeight);
        return this;
    }

    @Override
    public FontBuilder italic() {
        fontStyleBean.setItalic(true);
        return this;
    }

    @Override
    public CellStyleBuilder fontOver() {
        return cellStyleBuilder.useFont(fontStyleBean);
    }
}
