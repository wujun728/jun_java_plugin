package com.jujung.validator.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class RequestParam {

    @Min(value = 1,message = "最小值不能小于1")
    @Max(value = 5,message = "最大值不能大于5")
    private Integer number;

    @Email(message = "不是电子邮件格式")
    private String email;
}
