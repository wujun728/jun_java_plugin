package com.demo.weixin.enums;


import org.apache.commons.lang3.StringUtils;

/**
 * @author Wujun
 * @description 项目类型
 * @date 2017/7/31
 * @since 1.0
 */
public enum ProjectType {
    PROJECT_ONE(0),
    PROJECT_TWO(1);

    private final int value;

    private ProjectType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ProjectType findByLowerCaseValue(String lowerCaseValue){
        if (StringUtils.isBlank(lowerCaseValue)) {
            return null;
        }
        switch (lowerCaseValue) {
            case "p1":
                return ProjectType.PROJECT_ONE;
            case "p2":
                return ProjectType.PROJECT_TWO;
            default:
                return null;
        }
    }
}
