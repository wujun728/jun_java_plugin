package com.chentongwei.common.entity.io;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
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
