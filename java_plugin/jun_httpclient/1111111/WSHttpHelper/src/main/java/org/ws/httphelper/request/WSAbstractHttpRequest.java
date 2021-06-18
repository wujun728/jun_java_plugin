package org.ws.httphelper.request;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ws.httphelper.WSHttpHelperXmlConfig;
import org.ws.httphelper.common.MapKeyComparator;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.http.WSHttpTaskExecutor;
import org.ws.httphelper.model.ErrorMessage;
import org.ws.httphelper.model.ParameterDefine;
import org.ws.httphelper.model.ResponseResult;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.handler.RequestPreHandler;
import org.ws.httphelper.request.handler.ResponseProHandler;

import java.util.*;

/**
 * 请求的抽象类<br/>
 * 实现请求执行过程，执行过程如下：<br/>
 * 1.根据<b>@WSRequest</b>注解描述生成请求上下文。若没有<b>@WSRequest</b>注解则抛出异常。<br/>
 * 2.调用init(context)方法执行初始化，主要是向contex中添加数据，或者添加自定义处理器。<br/>
 * 3.添加默认处理器。请求前处理顺序：默认值初始化，验证参数，生成请求参数，生成URL。请求后处理：解析结果。<br/>
 * 3.1首选根据配置文件获取指定的默认处理器，若配置文件中没有指定，则使用org.ws.httphelper.request.handler.impl包中的默认处理器。<br/>
 * 4.执行请求前处理,按照顺序依次执行。<br/>
 * 4.1默认初始化处理：若注解描述中存在默认值，并且参数没有输入值，则为参数设置该默认值、<br/>
 * 4.2验证参数处理：根据注解描述，验证必须值，验证输入参数类型，根据正则验证。<br/>
 * 4.3生成请求参数处理：根据配置的参数或者动态添加的参数生成请求参数。自动识别普通参数，数组参数，文件参数。<br/>
 * 4.4生成URL处理：根据输入key的值自动匹配替换URL中{key}的值。<br/>
 * 5.执行请求。<br/>
 * 6.执行请求后处理，按照顺序依次执行。<br/>
 * 6.1解析结果处理：只解析JSON为指定的对象。<br/>
 * 7.清理缓存。不清理Cookie。要清除Cookie通过context.clearCookie()。<br/>
 * Created by gz on 15/12/4.
 */
public abstract class WSAbstractHttpRequest implements WSHttpRequest {
    protected static Log log = LogFactory.getLog(WSAbstractHttpRequest.class);
    protected Map<Integer, List<RequestPreHandler>> requestPreHandlerListMap = new TreeMap<Integer, List<RequestPreHandler>>(new MapKeyComparator());
    protected Map<Integer, List<ResponseProHandler>> responseProHandlerListMap = new TreeMap<Integer, List<ResponseProHandler>>(new MapKeyComparator());
    private WSRequestContext context = null;
    private Map<String,String> headerMap = new HashMap<String, String>();
    private Map<String,String> cookieMap = new HashMap<String, String>();
    private Map<String,Object> inputData = new HashMap<String, Object>();
    private List<ParameterDefine> parameterDefineList = new ArrayList<ParameterDefine>();

    public abstract void init(WSRequestContext context) throws WSException;
    protected abstract WSRequestContext builderContext() throws WSException;

    private void addUserData() throws WSException{
        if(context==null){
            context = builderContext();
        }
        if(!headerMap.isEmpty()){
            Set<String> keySet = headerMap.keySet();
            for(String key:keySet){
                context.addHeader(key,headerMap.get(key));
            }
        }
        if(!cookieMap.isEmpty()){
            Set<String> keySet = cookieMap.keySet();
            for(String key:keySet){
                context.addCookie(key, cookieMap.get(key));
            }
        }
        if(!parameterDefineList.isEmpty()){
            for(ParameterDefine parameterDefine:parameterDefineList){
                context.addParameterDefine(parameterDefine);
                String name = parameterDefine.getName();
                if(inputData.containsKey(name)){
                    context.addInputData(name,inputData.get(name));
                }
            }
        }
    }

    private boolean preRequest() throws WSException {
        // 添加用户手动添加的数据
        addUserData();
        // 初始化
        init(context);
        // 请求处理
        defaultHandlerInit();
        // 执行前处理
        Set<Integer> preKeySet = requestPreHandlerListMap.keySet();
        // 从小到大以此执行
        for (Integer key : preKeySet) {
            List<RequestPreHandler> list = requestPreHandlerListMap.get(key);
            if (list != null) {
                for (RequestPreHandler handler : list) {
                    if (!handler.handler(context)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ResponseResult execute() throws WSException {
        ResponseResult result = null;
        if (!preRequest()) {
            // 出现错误：获取错误消息并返回
            result = new ResponseResult();
            result.setStatus(999);
            List<ErrorMessage> errorMessageList = context.getErrorMessageList();
            StringBuffer error = new StringBuffer();
            for (ErrorMessage message : errorMessageList) {
                error.append(message);
            }
            result.setBody(error.toString());
            return result;
        }
        // 执行请求
        String uuid = WSHttpTaskExecutor.getInstance().execute(context);
        // 获取执行结果
        result = WSHttpTaskExecutor.getInstance().getResult(uuid);
        // 执行后处理
        Set<Integer> proKeySet = responseProHandlerListMap.keySet();
        // 从小到大以此执行
        for (Integer key : proKeySet) {
            List<ResponseProHandler> list = responseProHandlerListMap.get(key);
            if (list != null) {
                for (ResponseProHandler handler : list) {
                    result = handler.handler(context, result);
                }
            }
        }
        // 清楚缓存
        clear();
        return result;
    }

    @Override
    public void asyncExecute() throws WSException {
        if (!preRequest()) {
            // 出现错误：获取错误消息并返回
            List<ErrorMessage> errorMessageList = context.getErrorMessageList();
            StringBuffer error = new StringBuffer();
            for (ErrorMessage message : errorMessageList) {
                error.append(message);
            }
            throw new WSException(error.toString());
        }
        WSHttpTaskExecutor.getInstance().asyncExecute(context, responseProHandlerListMap);
        requestPreHandlerListMap.clear();
    }

    private void clear() {
        this.context.clear();
        requestPreHandlerListMap.clear();
        responseProHandlerListMap.clear();
    }

    public void addRequestPreHandler(RequestPreHandler handler) {
        if (handler == null) {
            return;
        }
        int level = handler.level();
        if (requestPreHandlerListMap.containsKey(level)) {
            List<RequestPreHandler> list = requestPreHandlerListMap.get(level);
            list.add(handler);
        } else {
            List<RequestPreHandler> list = new ArrayList<RequestPreHandler>();
            list.add(handler);
            requestPreHandlerListMap.put(level, list);
        }
    }

    public void addResponseProHandler(ResponseProHandler handler) {
        if (handler == null) {
            return;
        }
        int level = handler.level();
        if (responseProHandlerListMap.containsKey(level)) {
            List<ResponseProHandler> list = responseProHandlerListMap.get(level);
            list.add(handler);
        } else {
            List<ResponseProHandler> list = new ArrayList<ResponseProHandler>();
            list.add(handler);
            responseProHandlerListMap.put(level, list);
        }
    }

    /**
     * 添加请求头部
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    public void addCookie(String name, String value) {
        cookieMap.put(name,value);
    }

    /**
     * 添加请求参数
     *
     * @param name
     * @param value
     */
    public void addParameter(String name, Object value) {
        parameterDefineList.add(new ParameterDefine(name));
        inputData.put(name, value);
    }

    /**
     * 初始化默认处理器
     *
     * @throws WSException
     */
    private void defaultHandlerInit() throws WSException {
        List<RequestPreHandler> defaultPreHandlers = WSHttpHelperXmlConfig.getInstance().getDefaultPreHandlers();
        for(RequestPreHandler requestPreHandler:defaultPreHandlers){
            addRequestPreHandler(requestPreHandler);
        }
        List<ResponseProHandler> defaultProHandlers = WSHttpHelperXmlConfig.getInstance().getDefaultProHandlers();
        for(ResponseProHandler responseProHandler:defaultProHandlers){
            addResponseProHandler(responseProHandler);
        }
    }

    public String getHelp() {
        StringBuffer help = new StringBuffer();
        help.append("<h1>").append(context.getName()).append("</h1><br/>");
        help.append("<h4>请求路径：").append(context.getUrl()).append("</h4><br/>");
        help.append("");
        return help.toString();
    }

    public WSRequestContext getContext() throws WSException{
        if(context==null){
            context = builderContext();
        }
        return context;
    }
}
