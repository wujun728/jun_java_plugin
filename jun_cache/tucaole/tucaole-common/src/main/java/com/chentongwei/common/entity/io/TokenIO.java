package com.chentongwei.common.entity.io;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TongWei.Chen 2017-12-13 17:18:01
 * @Project tucaole
 * @Description: Token+userId
 */
@Data
public class TokenIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** userId */
    private Long userId;
    /** token */
    private String token;
}
