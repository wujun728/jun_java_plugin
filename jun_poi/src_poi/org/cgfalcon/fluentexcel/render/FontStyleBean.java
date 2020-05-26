package org.cgfalcon.fluentexcel.render;

/**
 * Author: falcon.chu
 * Date: 13-6-13
 * Time: 下午5:21
 */
public class FontStyleBean {
    private Short size = 8;
    private String name = "Calibri";
    private Byte underLine;
    private ColorBean color;
    private Short boldWeight;
    private Boolean italic = false;

    @Override
    public String toString() {
        return "FontStyleBean{" +
                "size=" + size +
                ", name='" + name + '\'' +
                ", underLine=" + underLine +
                ", color=" + color +
                ", boldWeight=" + boldWeight +
                ", italic=" + italic +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FontStyleBean that = (FontStyleBean) o;

        if (boldWeight != null ? !boldWeight.equals(that.boldWeight) : that.boldWeight != null) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (italic != null ? !italic.equals(that.italic) : that.italic != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (underLine != null ? !underLine.equals(that.underLine) : that.underLine != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = size != null ? size.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (underLine != null ? underLine.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (boldWeight != null ? boldWeight.hashCode() : 0);
        result = 31 * result + (italic != null ? italic.hashCode() : 0);
        return result;
    }

    public ColorBean getColor() {
        return color;
    }

    public void setColor(ColorBean color) {
        this.color = color;
    }

    public Short getSize() {
        return size;
    }

    public void setSize(Short size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getUnderLine() {
        return underLine;
    }

    public void setUnderLine(Byte underLine) {
        this.underLine = underLine;
    }



    public Short getBoldWeight() {
        return boldWeight;
    }

    public void setBoldWeight(Short boldWeight) {
        this.boldWeight = boldWeight;
    }

    public Boolean getItalic() {
        return italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }
}
