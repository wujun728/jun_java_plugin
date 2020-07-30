package tqlin.plugins;

import org.apache.commons.digester3.Digester;

public class Slider
        implements Widget {
    private String label = "nolabel";

    private int min = 0;

    private int max = 0;

    // define rules on this class
    public static void addRules(Digester digester, String pattern) {
        digester.addSetProperties(pattern);

        Class<?>[] paramtypes = {Integer.class};
        digester.addCallMethod(pattern + "/min", "setMin", 0, paramtypes);
        digester.addCallMethod(pattern + "/max", "setMax", 0, paramtypes);
    }

    // define different rules on this class
    public static void addRangeRules(Digester digester, String pattern) {
        // note: deliberately no addSetProperties rule
        Class<?>[] paramtypes = {Integer.class, Integer.class};
        digester.addCallMethod(pattern + "/range", "setRange", 2, paramtypes);
        digester.addCallParam(pattern + "/range", 0, "min");
        digester.addCallParam(pattern + "/range", 1, "max");
    }

    public Slider() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMin() {
        return min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setRange(int min, int max) {
        this.min = min;
        this.max = max;
    }
}