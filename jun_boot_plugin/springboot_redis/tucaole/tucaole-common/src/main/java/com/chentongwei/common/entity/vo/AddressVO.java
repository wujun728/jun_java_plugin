package com.chentongwei.common.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 省市区外层VO
 */
@Data
public class AddressVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 省 */
    private LocationVO province;
    /** 市 */
    private LocationVO city;
    /** 区 */
    private LocationVO district;
}
