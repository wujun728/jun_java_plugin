package tqlin.plugins;

public class TextLabel
        implements Widget {
    private String id = "anonymous";

    private String label = "nolabel";

    public TextLabel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}