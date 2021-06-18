package org.ws.httphelper.request;

import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ResponseResult;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.handler.RequestPreHandler;
import org.ws.httphelper.request.handler.ResponseProHandler;

/**
 * WSHttpRequest请求接口
 * Created by gz on 15-12-13.
 */
public interface WSHttpRequest extends Help {
    /**
     * 初始化。设置默认值。
     *
     * @param context 请求上下文。
     * @throws WSException
     */
    public void init(WSRequestContext context) throws WSException;

    /**
     * 执行请求。
     *
     * @return 响应结果。
     * @throws WSException
     */
    public ResponseResult execute() throws WSException;

    /**
     * 异步请求
     *
     * @throws WSException
     */
    public void asyncExecute() throws WSException;

    /**
     * 添加请求前处理器。
     *
     * @param handler
     */
    public void addRequestPreHandler(RequestPreHandler handler);

    /**
     * 添加请求后处理器，
     *
     * @param handler
     */
    public void addResponseProHandler(ResponseProHandler handler);

    /**
     * 添加参数<br/>
     *
     * @param name  参数名称。
     * @param value 参数值。支持：String:普通参数;List:数组参数;File:文件参数，上传文件。
     */
    public void addParameter(String name, Object value);

    /**
     * 添加请求头部。
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value);

    /**
     * 添加Cookie。
     *
     * @param name
     * @param value
     */
    public void addCookie(String name, String value);

    /**
     * 获取请求上下文。
     *
     * @return
     */
    public WSRequestContext getContext() throws WSException;
}
