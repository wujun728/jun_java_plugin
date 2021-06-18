package org.ws.httphelper.model;

import org.ws.httphelper.annotation.Parameter;

/**
 * 参数定义
 * Created by gz on 15/11/29.
 */
public class ParameterDefine {
    private String name;
    private String description;
    private String defaultValue;
    private Parameter.Type type;
    private boolean required;
    private String example;
    private String validateRegex;

    public ParameterDefine() {
    }

    public ParameterDefine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Parameter.Type getType() {
        return type;
    }

    public void setType(Parameter.Type type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getValidateRegex() {
        return validateRegex;
    }

    public void setValidateRegex(String validateRegex) {
        this.validateRegex = validateRegex;
    }
}
