package org.ws.httphelper.http;

import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ResponseResult;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.handler.ResponseProHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 15-12-28.
 */
public class WSHttpAsyncClient extends WSHttpAbstractClient implements Runnable {

    private Map<Integer, List<ResponseProHandler>> responseProHandlerListMap = null;

    public WSHttpAsyncClient(WSRequestContext context) {
        super(context);
    }

    public WSHttpAsyncClient(WSRequestContext context, Map<Integer, List<ResponseProHandler>> responseProHandlerListMap) {
        super(context);
        this.responseProHandlerListMap = responseProHandlerListMap;
    }

    @Override
    public void run() {
        try {
            ResponseResult result = super.doRequest();
            if (responseProHandlerListMap != null) {
                // 执行后处理
                Set<Integer> proKeySet = responseProHandlerListMap.keySet();
                // 从小到大以此执行
                for (Integer key : proKeySet) {
                    List<ResponseProHandler> list = responseProHandlerListMap.get(key);
                    if (list != null) {
                        for (ResponseProHandler handler : list) {
                            handler.handler(context, result);
                        }
                    }
                }
            }
            this.context.clear();
            responseProHandlerListMap.clear();
        } catch (WSException e) {
            log.debug(e.getMessage(), e);
        }
    }
}
