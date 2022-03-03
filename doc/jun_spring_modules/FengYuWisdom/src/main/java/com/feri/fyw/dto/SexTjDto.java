package com.feri.fyw.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 11:56
 */
@Data
public class SexTjDto {
    private List<String> sexs;
    private List<Map<String,Object>> sexvals;
}
