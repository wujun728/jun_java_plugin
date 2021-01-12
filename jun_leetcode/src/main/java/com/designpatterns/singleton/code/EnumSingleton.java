package com.designpatterns.singleton.code;

/**
 * 枚举模式单例
 * 不会被反射攻击，序列化也不会有问题
 * @author BaoZhou
 * @date 2018/12/27
 */
public enum EnumSingleton {
    /**
     * 单例
     */
    INSTANCE;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Object data;

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
