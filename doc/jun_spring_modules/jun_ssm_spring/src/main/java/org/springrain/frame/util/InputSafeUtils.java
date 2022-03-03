package org.springrain.frame.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 输入内容安全过滤工具类
 * 
 * @author caomei
 *
 */
public class InputSafeUtils {
    private static final Logger logger = LoggerFactory.getLogger(InputSafeUtils.class);

    private InputSafeUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    private final static Whitelist user_content_filter = Whitelist.relaxed();

    static {
        user_content_filter.addTags("iframe", "article", "aside", "audio", "bdi", "canvas", "embed", "footer", "header",
                "label", "object", "param", "section", "video", "source");
        user_content_filter.addAttributes("iframe", "src", "scrolling", "frameborder", "align", "width", "height");
        user_content_filter.addAttributes(":all", "style", "class", "id", "name");
        user_content_filter.addAttributes("object", "width", "height", "classid", "codebase");
        user_content_filter.addAttributes("param", "name", "value", "type");
        user_content_filter.addAttributes("video", "autoplay", "controls", "loop", "muted", "poster", "preload", "dir",
                "src", "data-setup", "class", "width", "height");
        user_content_filter.addAttributes("source", "autoplay", "controls", "loop", "muted", "poster", "preload", "src",
                "type");
        user_content_filter.addAttributes("embed", "src", "quality", "width", "height", "allowFullScreen",
                "allowScriptAccess", "flashvars", "name", "type", "pluginspage");
        user_content_filter.addProtocols("img", "src", "http", "https");
        user_content_filter.addProtocols("video", "src", "http", "https");
        user_content_filter.addProtocols("source", "src", "http", "https");
        user_content_filter.addProtocols("a", "href", "http", "https", "#");

        // 保留相对连接,默认是false
        user_content_filter.preserveRelativeLinks(true);
    }

    /**
     * 对用户输入内容进行过滤,用于普通的文本字段
     * 
     * @param html
     * @return
     */
    public static String filterTextContent(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        text = StringEscapeUtils.escapeHtml4(text);
        return text;
    }

    /**
     * 对用户输入富文本内容进行过滤
     * 
     * @param html
     * @return
     */
    public static String filterRichTextContent(String html) {
        return filterRichTextContent(html, null);
    }

    /**
     * 对用户输入富文本内容进行过滤
     * 
     * @param html
     * @param baseUrl
     * @return
     */
    public static String filterRichTextContent(String html, String baseUrl) {

        if (StringUtils.isBlank(html)) {
            return html;
        }
        if (StringUtils.isBlank(baseUrl)) {
            return Jsoup.clean(html, user_content_filter);
        }

        return Jsoup.clean(html, baseUrl, user_content_filter);

    }

    /**
     * 从uri中截取字符串
     * @param uri
     * @param key
     * @param isencode
     * @param maxLength
     * @return
     */
    public static String substringByURI(String uri, String key, boolean isencode, int maxLength) {

        if (StringUtils.isBlank(uri) || StringUtils.isBlank(key)) {
            return null;
        }

        int s_index = uri.indexOf(key);
        if (s_index < 0) {
            return null;
        }
        String substring = uri.substring(s_index + 1, uri.indexOf("/", s_index + 1));

        // 重新编码siteId,避免注入
        if (isencode) {
            try {
                substring = URLEncoder.encode(substring, GlobalStatic.defaultCharset);
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }

        // 避免注入
        if (StringUtils.isBlank(substring) || substring.length() > maxLength) {
            return null;
        }

        return substring;
    }

    /**
     * 从uri中截取字符串
     * 
     * @param uri
     * @param key
     * @return
     */
    public static String substringByURI(String uri, String key) {
        return substringByURI(uri, key, true, 50);
    }

}
