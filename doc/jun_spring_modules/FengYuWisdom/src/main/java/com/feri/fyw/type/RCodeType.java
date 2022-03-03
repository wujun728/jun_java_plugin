package com.feri.fyw.type;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 16:46
 */
public enum RCodeType {
    成功(10000),失败(10001);
    private int code;

    public int getCode() {
        return code;
    }
    private RCodeType(int c){
        code=c;
    }
}
