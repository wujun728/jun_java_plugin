package com.jujung.validator.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class User {

    @NotNull(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    private Long id;

    @NotEmpty(groups = OnCreate.class)
    private String userName;

    @NotEmpty(groups = OnCreate.class)
    private String mobile;

    // 仅仅作为一个标记接口
    public interface OnUpdate{}

    public interface OnCreate{}
}

