package io.github.wujun728.db.rowmap;

public enum RowType {

	STRING(0),
    INTEGER(1),
    DOUBLE(2),
    LONG(3),
    DATE(4),
    BOOLEAN(5);

    private final int value;

    RowType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}