/*   
 * Project: OSMP
 * FileName: ProcedureList.java
 * version: V1.0
 */
package com.osmp.jdbc.define;

import java.util.List;

/**
 * 存储过程调用结果
 * @author heyu
 *
 */
public class ProcedureList<T> {
    private Object[] outputs;
    private List<T> result;
    public Object[] getOutputs() {
        return outputs;
    }
    public void setOutputs(Object[] outputs) {
        this.outputs = outputs;
    }
    public List<T> getResult() {
        return result;
    }
    public void setResult(List<T> result) {
        this.result = result;
    }
}
