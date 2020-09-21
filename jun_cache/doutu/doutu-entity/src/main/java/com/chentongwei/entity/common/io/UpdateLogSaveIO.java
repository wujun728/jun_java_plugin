package com.chentongwei.entity.common.io;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 保存更新日志IO
 *
 * @author TongWei.Chen 2017-07-11 16:11:35
 */
public class UpdateLogSaveIO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @NotNull
    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
