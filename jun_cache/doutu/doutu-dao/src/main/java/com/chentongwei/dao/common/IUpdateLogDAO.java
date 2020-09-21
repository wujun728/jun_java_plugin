package com.chentongwei.dao.common;

import com.chentongwei.entity.common.io.UpdateLogSaveIO;
import com.chentongwei.entity.common.vo.UpdateLogListVO;

import java.util.List;

/**
 * 更新日志DAO接口
 *
 * @author TongWei.Chen 2017-07-11 14:20:41
 */
public interface IUpdateLogDAO {

    /**
     * 项目更新日志列表
     *
     * @return
     */
    List<UpdateLogListVO> list();

    /**
     * 新增更新日志
     *
     * @param updateLogSaveIO：内容
     * @return
     */
    boolean save(UpdateLogSaveIO updateLogSaveIO);
}
