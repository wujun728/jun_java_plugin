package com.feri.fyw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 17:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {
    private int code;//成功0
    private String msg;
    private long count;
    private Object data;
}
