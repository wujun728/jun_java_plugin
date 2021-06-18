package com.chentongwei.core.common.biz;

import com.chentongwei.common.enums.msg.ResponseEnum;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.core.common.dao.ICommonDAO;
import com.chentongwei.core.common.entity.io.ExistsOneSelfIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 通用业务处理
 **/
@Service
public class CommonBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private ICommonDAO commonDAO;

    /**
     * 判断是否是自己的数据
     *
     * @param id：主键id
     * @param userId：用户id
     * @param tableName：表名称
     */
    public void existsOneSelf(Long id, Long userId, String tableName) {
        //判断是否是本人数据
        Integer flag = commonDAO.existsOneSelf(
                new ExistsOneSelfIO()
                        .id(id)
                        .creatorId(userId)
                        .tableName(tableName));
        CommonExceptionUtil.nullCheck(flag, ResponseEnum.NOT_ONE_SELF_DATA);
    }

}
