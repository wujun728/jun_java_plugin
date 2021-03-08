package cn.springboot.common.authority.service.xss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @Description 安全过滤配置管理类
 * @author Wujun
 * @date Mar 24, 2017 7:45:22 PM  
 */
@SuppressWarnings("all")
public class XSSSecurityManager {

    private static final Logger log = LoggerFactory.getLogger(XSSSecurityManager.class);

    /**
     * REGEX：校验正则表达式
     */
    public static String REGEX;

    /**
     * 特殊字符匹配
     */
    private static Pattern XSS_PATTERN;

    /**
     * 特殊url匹配规则map，<url,regex>
     */
    public static List<Map<String, Object>> checkUrlMatcherList = new ArrayList<Map<String, Object>>();

    /**
     * Constructor
     */
    private XSSSecurityManager() {
    }

    /**
     * 初始化
     *
     * @param config
     *            配置参数
     */
    public static void init(FilterConfig config) {

        log.debug("XSSSecurityManager init(FilterConfig config) begin");
        // 初始化过滤配置文件
        String xssPath = config.getServletContext().getRealPath("/") + config.getInitParameter("securityconfig");

        // 初始化安全过滤配置
        try {
            if (initConfig(xssPath)) {
                // 生成匹配器
                XSS_PATTERN = Pattern.compile(REGEX);

                for (Map matchMap : checkUrlMatcherList) {
                    log.debug("特殊URL过滤匹配规则" + matchMap);
                }

            } else {
                log.debug("初始化XSS配置失败!");
                throw new RuntimeException("初始化XSS配置失败!");
            }
        } catch (DocumentException e) {
            log.debug("安全过滤配置文件xss_security_config.xml加载异常");
            e.printStackTrace();
        }
        log.debug("XSSSecurityManager init(FilterConfig config) end");
    }

    /**
     * 读取安全审核配置文件xss_security_config.xml 设置XSSSecurityConfig配置信息
     *
     * @param path
     *            过滤配置文件路径
     * @return ture or false
     * @throws DocumentException
     */
    public static boolean initConfig(String path) throws DocumentException {

        log.debug("XSSSecurityManager.initConfig(String path) begin");
        Element superElement = new SAXReader().read(path).getRootElement();
        XSSSecurityConfig.IS_CHECK_HEADER = new Boolean(getEleValue(superElement, XSSSecurityConstants.IS_CHECK_HEADER));
        XSSSecurityConfig.IS_CHECK_PARAMETER = new Boolean(getEleValue(superElement, XSSSecurityConstants.IS_CHECK_PARAMETER));
        XSSSecurityConfig.IS_CHECK_URL = new Boolean(getEleValue(superElement, XSSSecurityConstants.IS_CHECK_URL));
        XSSSecurityConfig.IS_LOG = new Boolean(getEleValue(superElement, XSSSecurityConstants.IS_LOG));
        XSSSecurityConfig.IS_CHAIN = new Boolean(getEleValue(superElement, XSSSecurityConstants.IS_CHAIN));
        XSSSecurityConfig.REPLACE = new Boolean(getEleValue(superElement, XSSSecurityConstants.REPLACE));

        Element regexEle = superElement.element(XSSSecurityConstants.REGEX_LIST);
        Element checkUrlEles = superElement.element(XSSSecurityConstants.CHECK_URL_LIST);

        // 加载特殊url过滤配置
        if (checkUrlEles != null) {
            Iterator<Element> checkUrls = checkUrlEles.elementIterator();

            while (checkUrls.hasNext()) {
                Element checkUrlItem = checkUrls.next();

                Iterator<Element> urlItem = checkUrlItem.elementIterator();
                Map<String, Object> checkUrlMatch = new HashMap<String, Object>();
                String tempCheckUrlKey = "";
                StringBuffer tempCheckUrlRegValue = new StringBuffer("^");

                while (urlItem.hasNext()) {
                    Element element = urlItem.next();
                    if (XSSSecurityConstants.CHECK_URL_URL.equals(element.getName())) {
                        tempCheckUrlKey = element.getText();
                    } else if (XSSSecurityConstants.CHECK_URL_REGEX.equals(element.getName())) {
                        String tmp = element.getText();
                        tmp = tmp.replaceAll("\\\\\\\\", "\\\\");
                        tempCheckUrlRegValue.append(tmp);
                        tempCheckUrlRegValue.append("|");
                    }
                }

                String tempReg = tempCheckUrlRegValue.toString().substring(0, tempCheckUrlRegValue.length() - 1) + "$";
                if (!"".equals(tempCheckUrlKey)) {
                    if (!tempReg.startsWith("^")) {
                        tempReg = "^" + tempReg;
                    }
                    checkUrlMatch.put(tempCheckUrlKey, tempReg);
                    checkUrlMatcherList.add(checkUrlMatch);
                } else {
                    log.debug("安全过滤配置文件加载失败:特殊url正则表达式异常,url匹配为 " + tempCheckUrlKey);
                    return false;
                }
            }

        } else {
            log.debug("安全过滤配置文件中没有 " + XSSSecurityConstants.CHECK_URL_LIST + " 属性");
            return false;
        }

        // 加载通用url过滤配置
        if (regexEle != null) {
            Iterator<Element> regexIt = regexEle.elementIterator();
            StringBuffer tempStr = new StringBuffer("^");
            // xml的cdata标签传输数据时，会默认在\前加\，需要将\\替换为\
            while (regexIt.hasNext()) {
                Element regex = (Element) regexIt.next();
                String tmp = regex.getText();
                tmp = tmp.replaceAll("\\\\\\\\", "\\\\");
                tempStr.append(tmp);
                tempStr.append("|");
            }
            if (tempStr.charAt(tempStr.length() - 1) == '|') {
                REGEX = tempStr.substring(0, tempStr.length() - 1) + "$";
                log.debug("安全匹配规则" + REGEX);
            } else {
                // 必须配置regex属性
                log.debug("安全过滤配置文件加载失败:正则表达式异常 " + tempStr.toString());
                return false;
            }
        } else {
            log.debug("安全过滤配置文件中没有 " + XSSSecurityConstants.REGEX_LIST + " 属性");
            return false;
        }

        log.debug("XSSSecurityManager.initConfig(String path) end");
        return true;

    }

    /**
     * 从目标element中获取指定标签信息，若找不到该标签，记录错误日志
     * 
     * @param element
     *            目标节点
     * @param tagName
     *            制定标签
     * @return
     */
    private static String getEleValue(Element element, String tagName) {
        if (isNullStr(element.elementText(tagName))) {
            log.debug("安全过滤配置文件中没有 " + XSSSecurityConstants.REGEX_LIST + " 属性");
        }
        return element.elementText(tagName);
    }

    /**
     * 对非法字符进行替换-会替换掉符合正则的整个字符串
     *
     * @param text
     * @return
     */
    public static String securityReplace(String text) {
        if (isNullStr(text)) {
            return text;
        } else {
            return text.replaceAll(REGEX, XSSSecurityConstants.REPLACEMENT);
        }
    }

    /**
     * 匹配字符是否含特殊字符
     * 
     * @param text
     * @return
     */
    public static boolean matches(String text) {
        if (text == null) {
            return false;
        }
        return XSS_PATTERN.matcher(text).matches();
    }

    /**
     * 释放关键信息
     */
    public static void destroy() {
        log.debug("XSSSecurityManager.destroy() begin");
        XSS_PATTERN = null;
        REGEX = null;
        log.debug("XSSSecurityManager.destroy() end");
    }

    /**
     * 判断是否为空串，建议放到某个工具类中
     * 
     * @param value
     * @return
     */
    public static boolean isNullStr(String value) {
        return value == null || value.trim().equals("");
    }
}
