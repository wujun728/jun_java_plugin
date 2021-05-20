package com.chentongwei.test.es;

import com.alibaba.fastjson.JSON;
import com.chentongwei.core.common.dao.IRegionDAO;
import com.chentongwei.core.common.entity.vo.RegionVO;
import com.chentongwei.es.template.ESCreateTemplate;
import com.chentongwei.test.BaseSpringBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 测试ES创建doc
 */
public class TestESCreateTemplate extends BaseSpringBootTest {

    @Autowired
    private ESCreateTemplate esCreateTemplate;
    @Autowired
    private IRegionDAO regionDAO;

    @Test
    public void testBulkInsert() {
        List<RegionVO> regionList = regionDAO.listByPid(0);
        System.out.println(JSON.toJSONString(regionList));
    }
}
