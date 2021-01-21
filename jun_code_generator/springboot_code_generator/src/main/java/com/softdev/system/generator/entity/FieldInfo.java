package com.softdev.system.generator.entity;

import lombok.Data;

/**
 * field info
 *
 * @author Wujun
 */
@Data
public class FieldInfo {

    private String columnName;
    private String fieldName;
    private String fieldClass;
    private String fieldComment;

}
