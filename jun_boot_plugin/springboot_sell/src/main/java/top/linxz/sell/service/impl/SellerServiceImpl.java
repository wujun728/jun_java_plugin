package top.linxz.sell.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linxz.sell.dataobject.SellerInfo;
import top.linxz.sell.repository.SellerInfoRepository;
import top.linxz.sell.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
