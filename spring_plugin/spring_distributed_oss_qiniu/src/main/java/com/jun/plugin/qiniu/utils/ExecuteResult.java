package com.jun.plugin.qiniu.utils;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kzyuan on 2017/4/5.
 * 业务类执行结果包装类
 * @param <T>
 */
public class ExecuteResult<T> implements Serializable {
    private static final long serialVersionUID = 7365417829056921958L;
    /**
     * 返回结果数据
     */
    private T result;
    /**
     * 成功提示消息
     */
    private String successMessage;
    /**
     * 普通的错误信息
     */
    private List<String> errorMessages = new ArrayList<String>();
    /**
     * 字段错误信息-以key-value的形式出现
     */
    private Map<String, String> fieldErrors = new HashMap<String, String>();
    /**
     * 警告信息
     */
    private List<String> warningMessages = new ArrayList<String>();

    public String getSuccessMessage() {
        return successMessage;
    }
    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
    /**
     * 判断当前执行结果是否正确，如果errorMessages和fieldErrors都为空，则无错
     * @return
     */
    public boolean isSuccess() {
        return errorMessages.isEmpty() && fieldErrors.isEmpty();
    }
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }
    public List<String> getErrorMessages() {
        return errorMessages;
    }
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
    public List<String> getWarningMessages() {
        return warningMessages;
    }
    public void setWarningMessages(List<String> warningMessages) {
        this.warningMessages = warningMessages;
    }
    /**
     * 添加一条错误消息到列表中
     * @param errorMessage
     */
    public void addErrorMessage(String errorMessage) {
        this.errorMessages.add(errorMessage);
    }
    /**
     * 添加一条字段错误信息到列表中
     * @param field 字段名称-key
     * @param errorMessage 该字段对应的错误信息-value
     */
    public void addFieldError(String field, String errorMessage) {
        this.fieldErrors.put(field, errorMessage);
    }
    /**
     * 添加一条警告信息到列表中
     * @param warningMessage
     */
    public void addWarningMessage(String warningMessage) {
        this.warningMessages.add(warningMessage);
    }
}
