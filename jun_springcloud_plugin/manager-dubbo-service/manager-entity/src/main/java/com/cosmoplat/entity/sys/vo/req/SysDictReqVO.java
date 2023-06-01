package com.cosmoplat.entity.sys.vo.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author manager
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictReqVO implements Serializable {

    @NotBlank(message = "字典编码不能为空")
    private String code;

    @NotBlank(message = "字典类型不能为空")
    private String type;
}
