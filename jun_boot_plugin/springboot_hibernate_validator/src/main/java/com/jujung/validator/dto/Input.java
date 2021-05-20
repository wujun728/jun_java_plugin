package com.jujung.validator.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class Input {

    @NotBlank
    private String path;

    @Valid
    private Person person;
}

@Data
class Person{

    @NotBlank
    private String name;
    @Positive // 正数
    private Integer age;
}
