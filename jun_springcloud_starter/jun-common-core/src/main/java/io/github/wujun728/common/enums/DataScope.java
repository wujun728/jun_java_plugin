package io.github.wujun728.common.enums;

import lombok.Getter;

/**
 * 枚举类型
 *
 */
@Getter
public enum DataScope implements ZltEnum{
    ALL(0, "全部权限"), CREATOR(1, "创建者权限");

    DataScope(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    private Integer id;
    private String content;
}
