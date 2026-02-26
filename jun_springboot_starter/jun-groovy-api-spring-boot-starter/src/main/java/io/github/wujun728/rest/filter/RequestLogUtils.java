//package io.github.wujun728.rest.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import net.trueland.scrm.common.context.web.WebRequestContext;
//import net.trueland.scrm.common.web.property.ScrmRequestProperties;
//import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * 请求日志打印工具
// * 李璇
// */
//@Slf4j
//public final class RequestLogUtils {
//
//    private RequestLogUtils() {
//
//    }
//
//    private static final String URL_FORMAT = "curl '%s'\\\n";
//
//    private static final String HEADER_FORMAT = "  -H '%s: %s'\\\n";
//
//    private static final String DATA_ROW_FORMAT = "  --data-raw '%s'";
//
//    public static final Set<String> HTTP_HEADERS = Set.of(
//            "application-code", "application-version", "preview-application-code",
//            "lang-code", "layout-code", "object-code", "view-code",
//            "authority", "accept", "accept-language", "content-type",
//            "origin", "referer", "user-agent",
//            "sec-ch-ua", "sec-ch-ua-mobile", "sec-ch-ua-platform", "sec-fetch-dest", "sec-fetch-mode", "sec-fetch-site",
//            "x-token", "sso-token", "x-account", "tax-rate", "root-id", "source-flag", "service-version",
//            "d-token", "p-token", "x-weboffice-token", "s-request-token", "xxl-job-access-token");
//
//
//    public static String getHttpInfo(ScrmRequestProperties scrmRequestProperties) {
//        StringBuilder msg = new StringBuilder();
//        String requestUrl = WebRequestContext.getRequestUrl();
//        if (scrmRequestProperties.getLog().isIncludeQueryString() && StringUtils.isNotBlank(WebRequestContext.getQueryString())) {
//            requestUrl = requestUrl + '?' + WebRequestContext.getQueryString();
//        }
//        msg.append(String.format(URL_FORMAT, requestUrl));
//        if (scrmRequestProperties.getLog().isIncludeHeaders()) {
//            Map<String, String> headers = WebRequestContext.getHeaders();
//            if (MapUtils.isNotEmpty(headers)) {
//                Set<Map.Entry<String, String>> entries = headers.entrySet();
//                List<Map.Entry<String, String>> ignoreHttpHeaders = new ArrayList<>();
//                for (Map.Entry<String, String> entry : entries) {
//                    String key = entry.getKey();
//                    if (HTTP_HEADERS.contains(key)) {
//                        msg.append(String.format(HEADER_FORMAT, key, entry.getValue()));
//                    } else {
//                        ignoreHttpHeaders.add(entry);
//                    }
//                }
//                log.info("getHttpInfo ignoreHttpHeaders {}", ignoreHttpHeaders);
//            }
//        }
//        if (scrmRequestProperties.getLog().isIncludePayload()) {
//            String payload = WebRequestContext.getPayload();
//            if (StringUtils.isNotBlank(payload)) {
//                int maxLength = scrmRequestProperties.getLog().getPayloadLogMaxLength();
//                String dataRaw = payload.length() > maxLength ? payload.substring(0, maxLength) + "...(" + (payload.length() - maxLength) + " chars more)" : payload;
//                msg.append(String.format(DATA_ROW_FORMAT, dataRaw));
//            }
//        }
//        return msg.toString();
//    }
//
//}
