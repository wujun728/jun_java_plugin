package org.typroject.tyboot.core.foundation.enumeration;

/**
 * <pre>
 *  tyboot
 *  File: UserType.java
 *
 *  Tyrest, Inc.
 *  Copyright (C): 2019
 *
 *  Description:用户类型
 *
 *  2016年11月1日		magintrursh		Initial.
 * </pre>
 */
public enum UserType {

    ANONYMOUS(0, "匿名用户"),

    PUBLIC(100, "开放授权用户"),

    CUSTOMER(200, "普通用户级别"),

    AGENCY(300, "机构用户"),

    SUPER_ADMIN(400, "超级用户");


    private int level;
    private String label;


    UserType(int level, String label) {
        this.level = level;
        this.label = label;
    }


    public static UserType getUserTypeByLevel(int level) {
        UserType userType = null;
        for (UserType ut : UserType.values()) {
            if (ut.level == level) {
                userType = ut;
                break;
            }
        }
        return userType;
    }


    public int getLevel() {
        return level;
    }

    public String getLabel() {
        return label;
    }
}

/*
 * $Log: av-env.bat,v $
 */