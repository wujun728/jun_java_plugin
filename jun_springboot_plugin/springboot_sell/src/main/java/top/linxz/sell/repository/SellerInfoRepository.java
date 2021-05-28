package top.linxz.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.linxz.sell.dataobject.SellerInfo;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenid(String openid);
}
