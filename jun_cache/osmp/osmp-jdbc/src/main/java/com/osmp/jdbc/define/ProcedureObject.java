/*   
 * Project: OSMP
 * FileName: ProcedureObject.java
 * version: V1.0
 */
package com.osmp.jdbc.define;

/**
 * 存储过程调用结果
 * @author heyu
 *
 */
public class ProcedureObject<T> {
    private Object[] outputs;
    private T result;
    public Object[] getOutputs() {
        return outputs;
    }
    public void setOutputs(Object[] outputs) {
        this.outputs = outputs;
    }
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }
    
}
