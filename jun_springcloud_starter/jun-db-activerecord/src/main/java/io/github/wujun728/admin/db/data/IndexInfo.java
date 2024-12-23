package io.github.wujun728.admin.db.data;

import lombok.Data;

import java.util.Objects;

/**
 * 索引信息
 */
@Data
public class IndexInfo {
    //key名称,用来区分新增,更新
    private String oldKeyName;
    private String keyName;
    private String columnName;
    private String indexComment;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IndexInfo indexInfo = (IndexInfo) o;
        return keyName.equals(indexInfo.keyName) && columnName.equals(indexInfo.columnName) && Objects.equals(indexComment, indexInfo.indexComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyName, columnName, indexComment);
    }
}
