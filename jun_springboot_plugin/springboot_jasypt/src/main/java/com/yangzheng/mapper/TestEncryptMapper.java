package com.yangzheng.mapper;

import com.yangzheng.entity.TestEncrypt;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangzheng
 * @description
 * @date 2022/5/30 03016:13
 */
@Mapper
public interface TestEncryptMapper {

    TestEncrypt getTestEncryptById(int id);

    void insert(TestEncrypt testEncrypt);
}
