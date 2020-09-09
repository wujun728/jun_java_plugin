package org.ws.httphelper.http;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.ws.httphelper.WSHttpHelperXmlConfig;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ResponseResult;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.model.config.HttpClientConfig;
import org.ws.httphelper.request.handler.ResponseProHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WSHttpTaskExecutor {
    private static final WSHttpTaskExecutor _instance = new WSHttpTaskExecutor();
    private ThreadPoolExecutor threadPool = null;
    private Map<String, FutureTask<Object>> taskMap = new HashMap<String, FutureTask<Object>>();

    private WSHttpTaskExecutor() {}

    public static WSHttpTaskExecutor getInstance()  throws WSException{
        if(_instance.threadPool==null){
            HttpClientConfig httpClientConfig = WSHttpHelperXmlConfig.getInstance().getHttpClientConfig();
            _instance.threadPool = new ThreadPoolExecutor(httpClientConfig.getCorePoolSize(),
                    httpClientConfig.getPoolMaxPoolSize(),
                    httpClientConfig.getKeepAliveSeconds(),
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(httpClientConfig.getPoolQueueCapacity()),
                    new ThreadPoolExecutor.CallerRunsPolicy());
        }
        return _instance;
    }

    /**
     * 等待执行线程完成
     */
    public void waitForFinish(Thread thread){
        while (this.threadPool.getActiveCount()>0){
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ThreadPoolExecutor getThreadPool(){
        return threadPool;
    }

    public String execute(WSRequestContext context) throws WSException {
        FutureTask<Object> task = new FutureTask<Object>(new WSHttpClient(context));
        threadPool.submit(task);
        String GUID = UUID.randomUUID().toString();
        taskMap.put(GUID, task);
        return GUID;
    }

    public void asyncExecute(WSRequestContext context, Map<Integer, List<ResponseProHandler>> responseProHandlerListMap) throws WSException {
        WSHttpAsyncClient client = new WSHttpAsyncClient(context, responseProHandlerListMap);
        threadPool.submit(client);
    }

    public ResponseResult getResult(String GUID) throws WSException {
        FutureTask<Object> task = taskMap.get(GUID);
        try {
            return (ResponseResult) task.get();
        } catch (Exception e) {
            ResponseResult result = new ResponseResult();
            result.setStatus(500);
            result.setWasteTime(-1);
            result.setBody(e.getMessage());
            return result;
        } finally {
            taskMap.remove(GUID);
        }
    }
}
