package org.wf.jwtp.perm;

import org.wf.jwtp.annotation.Logical;

/**
 * url自动对应权限接口的返回结果封装
 */
public class UrlPermResult {
    private String[] values;
    private Logical logical;

    public UrlPermResult() {
    }

    public UrlPermResult(String[] values, Logical logical) {
        setValues(values);
        setLogical(logical);
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public Logical getLogical() {
        return logical;
    }

    public void setLogical(Logical logical) {
        this.logical = logical;
    }

}
