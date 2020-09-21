package com.chentongwei.service.common;

import com.chentongwei.common.entity.Page;
import com.chentongwei.entity.common.io.UpdateLogSaveIO;
import com.chentongwei.entity.common.vo.UpdateLogListVO;

import java.util.List;

/**
 * 项目更新日志业务接口
 *
 * @author TongWei.Chen 2017-07-11 14:23:14
 */
public interface IUpdateLogService {

    /**
     * 项目更新日志列表
     *
     * @param page:分页信息
     * @return
     */
    List<UpdateLogListVO> list(Page page);

    /**
     * 新增更新日志
     *
     * @param updateLogSaveIO：内容
     * @return
     */
    boolean save(UpdateLogSaveIO updateLogSaveIO);
}
