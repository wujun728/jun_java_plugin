package org.cgfalcon.fluentexcel.render;

/**
 * Author: falcon.chu
 * Date: 13-6-13
 * Time: 下午5:18
 */
public class CellStyleBean {

    private FontStyleBean font;
    private Short verticalAlign;
    private Short align;
    private ColorBean fgColor;
    private ColorBean bgColor;
    private String dataFormat;
    private Boolean wrapText = false;

    @Override
    public String toString() {
        return "CellStyleBean{" +
                "font=" + font +
                ", verticalAlign=" + verticalAlign +
                ", align=" + align +
                ", fgColor=" + fgColor +
                ", bgColor=" + bgColor +
                ", dataFormat='" + dataFormat + '\'' +
                ", wrapText=" + wrapText +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellStyleBean that = (CellStyleBean) o;

        if (align != null ? !align.equals(that.align) : that.align != null) return false;
        if (bgColor != null ? !bgColor.equals(that.bgColor) : that.bgColor != null) return false;
        if (dataFormat != null ? !dataFormat.equals(that.dataFormat) : that.dataFormat != null) return false;
        if (fgColor != null ? !fgColor.equals(that.fgColor) : that.fgColor != null) return false;
        if (font != null ? !font.equals(that.font) : that.font != null) return false;
        if (verticalAlign != null ? !verticalAlign.equals(that.verticalAlign) : that.verticalAlign != null)
            return false;
        if (wrapText != null ? !wrapText.equals(that.wrapText) : that.wrapText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = font != null ? font.hashCode() : 0;
        result = 31 * result + (verticalAlign != null ? verticalAlign.hashCode() : 0);
        result = 31 * result + (align != null ? align.hashCode() : 0);
        result = 31 * result + (fgColor != null ? fgColor.hashCode() : 0);
        result = 31 * result + (bgColor != null ? bgColor.hashCode() : 0);
        result = 31 * result + (dataFormat != null ? dataFormat.hashCode() : 0);
        result = 31 * result + (wrapText != null ? wrapText.hashCode() : 0);
        return result;
    }

    public ColorBean getFgColor() {
        return fgColor;
    }

    public void setFgColor(ColorBean fgColor) {
        this.fgColor = fgColor;
    }

    public ColorBean getBgColor() {
        return bgColor;
    }

    public void setBgColor(ColorBean bgColor) {
        this.bgColor = bgColor;
    }

    public Boolean getWrapText() {
        return wrapText;
    }

    public void setWrapText(Boolean wrapText) {
        this.wrapText = wrapText;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public FontStyleBean getFont() {
        return font;
    }

    public void setFont(FontStyleBean font) {
        this.font = font;
    }

    public Short getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(Short verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public Short getAlign() {
        return align;
    }

    public void setAlign(Short align) {
        this.align = align;
    }


}
