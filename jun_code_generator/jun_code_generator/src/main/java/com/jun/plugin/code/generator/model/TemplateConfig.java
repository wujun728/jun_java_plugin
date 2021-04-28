package com.jun.plugin.code.generator.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TemplateConfig implements Serializable {

    public static final long serialVersionUID = 66L;

    Integer id;
    String name;
    String group;
    String path;
    String description;

}
