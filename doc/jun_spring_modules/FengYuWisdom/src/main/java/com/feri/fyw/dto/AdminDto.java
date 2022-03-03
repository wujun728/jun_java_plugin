package com.feri.fyw.dto;

import lombok.Data;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:40
 */
@Data
public class AdminDto {
    private String account;
    private String pass;
    private String captcha;
}
