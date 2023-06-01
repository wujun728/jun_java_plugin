package com.cosmoplat.entity.sys.vo.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DeptRespNodeVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class DeptRespNodeVO implements Serializable {
    private String id;

    private String deptNo;

    private String title;

    private String pid;

    private Integer status;

    private String relationCode;

    private boolean spread;


    private List<?> children;
}
