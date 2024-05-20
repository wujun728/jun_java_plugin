package com.jun.plugin.compile.util;

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
