package org.ws.httphelper.request.handler;

import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.WSRequestContext;

/**
 * Created by gz on 15/12/1.
 */
public interface RequestPreHandler {

    /**
     * 请求预处理
     *
     * @param context
     * @return 是否继续运行
     * @throws WSException
     */
    public boolean handler(WSRequestContext context) throws WSException;

    /**
     * 处理级别：小的优先执行
     *
     * @return
     */
    public int level();
}
