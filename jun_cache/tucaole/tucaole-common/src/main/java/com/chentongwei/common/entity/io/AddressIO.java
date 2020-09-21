package com.chentongwei.common.entity.io;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TongWei.Chen 2017-11-30 13:23:39
 * @Project tucaole
 * @Description: 省市区外层VO
 */
@Data
public class AddressIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 省 */
    private LocationIO province;
    /** 市 */
    private LocationIO city;
    /** 区 */
    private LocationIO district;
}
