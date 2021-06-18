package net.oschina.j2cache.serializer;

import java.io.Serializable;

/**
 * @author FY
 * @since 1.0
 */
public class TestObj implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof TestObj && ((TestObj) obj).getId().equals(this.id) && ((TestObj) obj).getName().equals(this.name);
    }
}