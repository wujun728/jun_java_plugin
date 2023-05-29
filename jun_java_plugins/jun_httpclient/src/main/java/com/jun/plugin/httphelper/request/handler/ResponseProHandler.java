package com.jun.plugin.httphelper.request.handler;

import com.jun.plugin.httphelper.exception.WSException;
import com.jun.plugin.httphelper.model.ResponseResult;
import com.jun.plugin.httphelper.model.WSRequestContext;

/**
 * Created by gz on 15/12/1.
 */
public interface ResponseProHandler {
    /**
     * 响应后处理
     *
     * @param context
     * @param result
     * @return
     * @throws WSException
     */
    public ResponseResult handler(WSRequestContext context, ResponseResult result) throws WSException;

    /**
     * 执行级别：小的优先执行
     *
     * @return
     */
    public int level();
}
