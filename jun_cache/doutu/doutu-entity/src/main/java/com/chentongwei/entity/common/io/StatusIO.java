package com.chentongwei.entity.common.io;

import com.chentongwei.common.annotation.StatusVerify;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改状态用的IO
 *
 * @author TongWei.Chen 2017-06-22 17:21:35
 */
public class StatusIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    @NotNull
    private Long id;
    /** 2:通过 3:拒绝 */
    @StatusVerify
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
