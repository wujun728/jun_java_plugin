package org.ws.httphelper;

import org.apache.commons.lang.StringUtils;
import org.ws.httphelper.annotation.WSRequest;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ResponseResult;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.WSHttpRequest;
import org.ws.httphelper.request.handler.CallbackHandler;
import org.ws.httphelper.request.handler.ResponseProHandler;
import org.ws.httphelper.request.impl.DefaultGetRequest;
import org.ws.httphelper.request.impl.DefaultPostRequest;

import java.util.Map;
import java.util.Set;

public class WSHttpHelper {
    public static String doGetHtml(String url) throws WSException {
        return doGetHtml(url, null, null, null, null);
    }

    public static String doGetHtml(String url, Map<String, Object> parameters) throws WSException {
        return doGetHtml(url, parameters, null, null, null);
    }

    public static String doGetHtml(String url, Map<String, Object> parameters, String charset) throws WSException {
        return doGetHtml(url, parameters, charset, null, null);
    }

    public static String doGetHtml(String url, Map<String, Object> parameters, String charset, Map<String, String> headers) throws WSException {
        return doGetHtml(url, parameters, charset, headers, null);
    }

    public static String doGetHtml(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies) throws WSException {
        DefaultGetRequest request = new DefaultGetRequest();
        return doExecute(request, url, parameters, charset, headers, cookies, WSRequest.ResponseType.HTML).toString();
    }

    public static void doAsyncGetHtml(String url, final CallbackHandler callback) throws WSException {
        doAsyncGetHtml(url, null, null, null, null, callback);
    }

    public static void doAsyncGetHtml(String url, Map<String, Object> parameters, final CallbackHandler callback) throws WSException {
        doAsyncGetHtml(url, parameters, null, null, null, callback);
    }

    public static void doAsyncGetHtml(String url, Map<String, Object> parameters, String charset, final CallbackHandler callback) throws WSException {
        doAsyncGetHtml(url, parameters, charset, null, null, callback);
    }

    public static void doAsyncGetHtml(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, final CallbackHandler callback) throws WSException {
        doAsyncGetHtml(url, parameters, charset, headers, null, callback);
    }

    public static void doAsyncGetHtml(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, final CallbackHandler callback) throws WSException {
        DefaultGetRequest request = new DefaultGetRequest();
        doAsyncExecute(request, url, parameters, charset, headers, cookies, callback, WSRequest.ResponseType.HTML);
    }

    public static byte[] doGetByteArray(String url) throws WSException {
        return doGetByteArray(url, null, null, null, null);
    }

    public static byte[] doGetByteArray(String url, Map<String, Object> parameters) throws WSException {
        return doGetByteArray(url, parameters, null, null, null);
    }

    public static byte[] doGetByteArray(String url, Map<String, Object> parameters, String charset) throws WSException {
        return doGetByteArray(url, parameters, charset, null, null);
    }

    public static byte[] doGetByteArray(String url, Map<String, Object> parameters, String charset, Map<String, String> headers) throws WSException {
        return doGetByteArray(url, parameters, charset, headers, null);
    }

    public static byte[] doGetByteArray(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies) throws WSException {
        DefaultGetRequest request = new DefaultGetRequest();
        return (byte[]) doExecute(request, url, parameters, charset, headers, cookies, WSRequest.ResponseType.BYTE_ARRAY);
    }

    public static void doAsyncGetByteArray(String url, final CallbackHandler callback) throws WSException {
        doAsyncGetByteArray(url, null, null, null, null, callback);
    }

    public static void doAsyncGetByteArray(String url, Map<String, Object> parameters, final CallbackHandler callback) throws WSException {
        doAsyncGetByteArray(url, parameters, null, null, null, callback);
    }

    public static void doAsyncGetByteArray(String url, Map<String, Object> parameters, String charset, final CallbackHandler callback) throws WSException {
        doAsyncGetByteArray(url, parameters, charset, null, null, callback);
    }

    public static void doAsyncGetByteArray(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, final CallbackHandler callback) throws WSException {
        doAsyncGetByteArray(url, parameters, charset, headers, null, callback);
    }

    public static void doAsyncGetByteArray(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, final CallbackHandler callback) throws WSException {
        DefaultGetRequest request = new DefaultGetRequest();
        doAsyncExecute(request, url, parameters, charset, headers, cookies, callback, WSRequest.ResponseType.BYTE_ARRAY);
    }

    public static Map<?, ?> doGetMap(String url) throws WSException {
        return doGetMap(url, null, null, null, null);
    }

    public static Map<?, ?> doGetMap(String url, Map<String, Object> parameters) throws WSException {
        return doGetMap(url, parameters, null, null, null);
    }

    public static Map<?, ?> doGetMap(String url, Map<String, Object> parameters, String charset) throws WSException {
        return doGetMap(url, parameters, charset, null, null);
    }

    public static Map<?, ?> doGetMap(String url, Map<String, Object> parameters, String charset, Map<String, String> headers) throws WSException {
        return doGetMap(url, parameters, charset, headers, null);
    }

    public static Map<?, ?> doGetMap(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies) throws WSException {
        return doGetJson(url, parameters, charset, headers, cookies, Map.class);
    }

    public static <T> T doGetJson(String url, Class<T> resultClazz) throws WSException {
        return doGetJson(url, null, null, null, null, resultClazz);
    }

    public static <T> T doGetJson(String url, Map<String, Object> parameters, Class<T> resultClazz) throws WSException {
        return doGetJson(url, parameters, null, null, null, resultClazz);
    }

    public static <T> T doGetJson(String url, Map<String, Object> parameters, String charset, Class<T> resultClazz) throws WSException {
        return doGetJson(url, parameters, charset, null, null, resultClazz);
    }

    public static <T> T doGetJson(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Class<T> resultClazz) throws WSException {
        return doGetJson(url, parameters, charset, headers, null, resultClazz);
    }

    public static <T> T doGetJson(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, Class<T> resultClazz) throws WSException {
        DefaultGetRequest request = new DefaultGetRequest();
        return doExecute(request, url, parameters, charset, headers, cookies, resultClazz);
    }

    public static String doPostHtml(String url) throws WSException {
        return doPostHtml(url, null, null, null, null);
    }

    public static String doPostHtml(String url, Map<String, Object> parameters) throws WSException {
        return doPostHtml(url, parameters, null, null, null);
    }

    public static String doPostHtml(String url, Map<String, Object> parameters, String charset) throws WSException {
        return doPostHtml(url, parameters, charset, null, null);
    }

    public static String doPostHtml(String url, Map<String, Object> parameters, String charset, Map<String, String> headers) throws WSException {
        return doPostHtml(url, parameters, charset, headers, null);
    }

    public static String doPostHtml(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies) throws WSException {
        DefaultPostRequest request = new DefaultPostRequest();
        return doExecute(request, url, parameters, charset, headers, cookies, WSRequest.ResponseType.HTML).toString();
    }

    public static void doAsyncPostHtml(String url, final CallbackHandler callback) throws WSException {
        doAsyncPostHtml(url, null, null, null, null, callback);
    }

    public static void doAsyncPostHtml(String url, Map<String, Object> parameters, final CallbackHandler callback) throws WSException {
        doAsyncPostHtml(url, parameters, null, null, null, callback);
    }

    public static void doAsyncPostHtml(String url, Map<String, Object> parameters, String charset, final CallbackHandler callback) throws WSException {
        doAsyncPostHtml(url, parameters, charset, null, null, callback);
    }

    public static void doAsyncPostHtml(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, final CallbackHandler callback) throws WSException {
        doAsyncPostHtml(url, parameters, charset, headers, null, callback);
    }

    public static void doAsyncPostHtml(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, final CallbackHandler callback) throws WSException {
        DefaultPostRequest request = new DefaultPostRequest();
        doAsyncExecute(request, url, parameters, charset, headers, cookies, callback, WSRequest.ResponseType.HTML);
    }

    public static byte[] doPostByteArray(String url) throws WSException {
        return doPostByteArray(url, null, null, null, null);
    }

    public static byte[] doPostByteArray(String url, Map<String, Object> parameters) throws WSException {
        return doPostByteArray(url, parameters, null, null, null);
    }

    public static byte[] doPostByteArray(String url, Map<String, Object> parameters, String charset) throws WSException {
        return doPostByteArray(url, parameters, charset, null, null);
    }

    public static byte[] doPostByteArray(String url, Map<String, Object> parameters, String charset, Map<String, String> headers) throws WSException {
        return doPostByteArray(url, parameters, charset, headers, null);
    }

    public static byte[] doPostByteArray(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies) throws WSException {
        DefaultPostRequest request = new DefaultPostRequest();
        return (byte[]) doExecute(request, url, parameters, charset, headers, cookies, WSRequest.ResponseType.BYTE_ARRAY);
    }

    public static void doAsyncPostByteArray(String url, final CallbackHandler callback) throws WSException {
        doAsyncPostByteArray(url, null, null, null, null, callback);
    }

    public static void doAsyncPostByteArray(String url, Map<String, Object> parameters, final CallbackHandler callback) throws WSException {
        doAsyncPostByteArray(url, parameters, null, null, null, callback);
    }

    public static void doAsyncPostByteArray(String url, Map<String, Object> parameters, String charset, final CallbackHandler callback) throws WSException {
        doAsyncPostByteArray(url, parameters, charset, null, null, callback);
    }

    public static void doAsyncPostByteArray(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, final CallbackHandler callback) throws WSException {
        doAsyncPostByteArray(url, parameters, charset, headers, null, callback);
    }

    public static void doAsyncPostByteArray(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, final CallbackHandler callback) throws WSException {
        DefaultPostRequest request = new DefaultPostRequest();
        doAsyncExecute(request, url, parameters, charset, headers, cookies, callback, WSRequest.ResponseType.BYTE_ARRAY);
    }

    public static Map<?, ?> doPostMap(String url) throws WSException {
        return doPostMap(url, null, null, null, null);
    }

    public static Map<?, ?> doPostMap(String url, Map<String, Object> parameters) throws WSException {
        return doPostMap(url, parameters, null, null, null);
    }

    public static Map<?, ?> doPostMap(String url, Map<String, Object> parameters, String charset) throws WSException {
        return doPostMap(url, parameters, charset, null, null);
    }

    public static Map<?, ?> doPostMap(String url, Map<String, Object> parameters, String charset, Map<String, String> headers) throws WSException {
        return doPostMap(url, parameters, charset, headers, null);
    }

    public static Map<?, ?> doPostMap(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies) throws WSException {
        return doPostJson(url, parameters, charset, headers, cookies, Map.class);
    }

    public static <T> T doPostJson(String url, Class<T> resultClazz) throws WSException {
        return doPostJson(url, null, null, null, null, resultClazz);
    }

    public static <T> T doPostJson(String url, Map<String, Object> parameters, Class<T> resultClazz) throws WSException {
        return doPostJson(url, parameters, null, null, null, resultClazz);
    }

    public static <T> T doPostJson(String url, Map<String, Object> parameters, String charset, Class<T> resultClazz) throws WSException {
        return doPostJson(url, parameters, charset, null, null, resultClazz);
    }

    public static <T> T doPostJson(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Class<T> resultClazz) throws WSException {
        return doPostJson(url, parameters, charset, headers, null, resultClazz);
    }

    public static <T> T doPostJson(String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, Class<T> resultClazz) throws WSException {
        DefaultPostRequest request = new DefaultPostRequest();
        return doExecute(request, url, parameters, charset, headers, cookies, resultClazz);
    }

    /**
     * 执行自动解析的请求
     *
     * @param request
     * @param url
     * @param parameters
     * @param headers
     * @param cookies
     * @param resultClazz
     * @param <T>
     * @return
     * @throws WSException
     */
    public static <T> T doExecute(WSHttpRequest request, String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, Class<T> resultClazz) throws WSException {
        if (resultClazz == null) {
            throw new WSException("该接口必须传入resultClazz！");
        }
        buildRequest(request, url, parameters, charset, headers, cookies, null);
        request.getContext().setResultClass(resultClazz);
        request.getContext().setResponseType(WSRequest.ResponseType.JSON);
        return request.execute().getBody(resultClazz);
    }

    /**
     * 执行请求
     *
     * @param request
     * @param url
     * @param parameters
     * @param headers
     * @param cookies
     * @param responseType
     * @return
     * @throws WSException
     */
    public static Object doExecute(WSHttpRequest request, String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, WSRequest.ResponseType responseType) throws WSException {
        buildRequest(request, url, parameters, charset, headers, cookies, null);
        request.getContext().setResponseType(responseType);
        return request.execute().getBody();
    }

    public static void doAsyncExecute(WSHttpRequest request, String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, final CallbackHandler callback, WSRequest.ResponseType responseType) throws WSException {
        buildRequest(request, url, parameters, charset, headers, cookies, callback);
        request.getContext().setResponseType(responseType);
        request.asyncExecute();
    }

    private static void buildRequest(WSHttpRequest request, String url, Map<String, Object> parameters, String charset, Map<String, String> headers, Map<String, String> cookies, final CallbackHandler callback) throws WSException {
        request.getContext().setUrl(url);
        if (!StringUtils.isEmpty(charset)) {
            request.getContext().setCharset(charset);
        }
        if (parameters != null) {
            Set<String> keySet = parameters.keySet();
            for (String key : keySet) {
                request.addParameter(key, parameters.get(key));
            }
        }
        if (headers != null) {
            Set<String> keySet = headers.keySet();
            boolean isUserAgent = false;
            for (String key : keySet) {
                request.addHeader(key, headers.get(key));
                if ("user-agent".equals(key.toLowerCase())) {
                    isUserAgent = true;
                }
            }
            if (!isUserAgent) {
                request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
            }
        } else {
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
        }
        if (cookies != null) {
            Set<String> keySet = cookies.keySet();
            for (String key : keySet) {
                request.addCookie(key, cookies.get(key));
            }
        }
        if (callback != null) {
            request.addResponseProHandler(new ResponseProHandler() {
                @Override
                public ResponseResult handler(WSRequestContext context, ResponseResult result) throws WSException {
                    return callback.execute(result);
                }

                @Override
                public int level() {
                    return WSHttpHelperConstant.PRO_HANDLER_USER;
                }
            });
        }
    }
}
