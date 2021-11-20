package springcloud.service.impl;

import org.springframework.stereotype.Service;
import springcloud.dao.StorageDao;
import springcloud.service.StorageService;

import javax.annotation.Resource;

@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        storageDao.decrease(productId, count);
    }

}
