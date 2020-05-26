package org.cgfalcon.fluentexcel.render;

/**
 * Author: falcon.chu
 * Date: 13-6-14
 * Time: 上午11:19
 */
public class ColorBean {

    private Short r = 0;
    private Short g = 0;
    private Short b = 0;

    public ColorBean() {

    }

    public ColorBean(Short r, Short g, Short b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColorBean colorBean = (ColorBean) o;

        if (b != null ? !b.equals(colorBean.b) : colorBean.b != null) return false;
        if (g != null ? !g.equals(colorBean.g) : colorBean.g != null) return false;
        if (r != null ? !r.equals(colorBean.r) : colorBean.r != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = r != null ? r.hashCode() : 0;
        result = 31 * result + (g != null ? g.hashCode() : 0);
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ColorBean{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }

    public Short getR() {
        return r;
    }

    public void setR(Short r) {
        this.r = r;
    }

    public Short getG() {
        return g;
    }

    public void setG(Short g) {
        this.g = g;
    }

    public Short getB() {
        return b;
    }

    public void setB(Short b) {
        this.b = b;
    }
}
