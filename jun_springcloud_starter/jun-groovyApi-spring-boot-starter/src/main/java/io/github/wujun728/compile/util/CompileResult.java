package io.github.wujun728.compile.util;

import lombok.Data;

@Data
public class CompileResult {
    private Boolean isSucess;
    private String compileMsg;
    private String executeMsg;
    private Object executeResult;
    private String source;
    private String method;
}
