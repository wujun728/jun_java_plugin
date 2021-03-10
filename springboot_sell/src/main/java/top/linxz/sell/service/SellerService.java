package top.linxz.sell.service;

import top.linxz.sell.dataobject.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);


}
