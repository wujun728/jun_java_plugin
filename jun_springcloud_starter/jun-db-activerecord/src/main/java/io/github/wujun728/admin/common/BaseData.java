package io.github.wujun728.admin.common;

import lombok.Data;

import java.util.Objects;

@Data
public class BaseData {
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseData baseData = (BaseData) o;
        return Objects.equals(id, baseData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
