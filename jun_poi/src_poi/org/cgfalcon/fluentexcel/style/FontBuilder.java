package org.cgfalcon.fluentexcel.style;



/**
 * User: falcon.chu
 * Date: 13-9-23
 * Time: 下午5:38
 */
public interface FontBuilder {
    FontBuilder size(Short fongSize);
    FontBuilder fontName(String fontName);
    FontBuilder withUnderLine(Byte underLineType);
    FontBuilder useColor(Short r, Short g, Short b);
    FontBuilder boldWeight(Short boldWeight);
    FontBuilder italic();

    CellStyleBuilder fontOver();
}
