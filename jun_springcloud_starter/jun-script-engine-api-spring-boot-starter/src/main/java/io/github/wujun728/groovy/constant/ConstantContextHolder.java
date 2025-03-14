//package io.github.wujun728.groovy.constant;
//
//import cn.hutool.core.convert.Convert;
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.RandomUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.log.Log;
//import io.github.wujun728.groovy.listener.ConstantContext;
//
//
///**
// * 系统参数配置获取
// */
//public class ConstantContextHolder {
//
//    private static final Log log = Log.get();
//
//    /**
//     * 获取租户功能是否开启
//     *
//     * @date 2020/9/3
//     */
//    public static Boolean getTenantOpenFlag() {
//        return getSysConfigWithDefault("SNOWY_TENANT_OPEN", Boolean.class, false);
//    }
//
//    /**
//     * 获取验证码功能是否开启
//     *
//     * @date 2020/9/3
//     */
//    public static Boolean getCaptchaFlag() {
//        return getSysConfigWithDefault("SNOWY_CAPTCHA_OPEN", Boolean.class, false);
//    }
//
//    /**
//     * 获取放开xss过滤的接口
//     *
//     * @date 2020/6/20 22:13
//     */
//
//    /**
//     * 获取演示环境开关是否开启，默认为false
//     *
//     * @date 2020/6/20 22:13
//     */
//    public static Boolean getDemoEnvFlag() {
//        return getSysConfigWithDefault("SNOWY_DEMO_ENV_FLAG", Boolean.class, false);
//    }
//
//    /**
//     * 邮件的配置
//     *
//     * @date 2020/6/19 18:08
//     */
////    public static EmailConfigs getEmailConfigs() {
////        String host = getSysConfig("SNOWY_EMAIL_HOST", String.class, true);
////        String username = getSysConfig("SNOWY_EMAIL_USERNAME", String.class, true);
////        String password = getSysConfig("SNOWY_EMAIL_PASSWORD", String.class, true);
////        Integer port = getSysConfig("SNOWY_EMAIL_PORT", Integer.class, true);
////        String from = getSysConfig("SNOWY_EMAIL_FROM", String.class, true);
////        Boolean ssl = getSysConfig("SNOWY_EMAIL_SSL", Boolean.class, true);
////
////        EmailConfigs emailConfigs = new EmailConfigs();
////        emailConfigs.setHost(host);
////        emailConfigs.setUser(username);
////        emailConfigs.setPass(password);
////        emailConfigs.setPort(port);
////        emailConfigs.setFrom(from);
////        emailConfigs.setSslEnable(ssl);
////        return emailConfigs;
////    }
//
//    /**
//     * 获取腾讯云短信的配置
//     *
//     * @date 2020/6/19 18:08
//     */
////    public static TencentSmsConfigs getTencentSmsConfigs() {
////        String snowyTencentSmsSecretId = getSysConfig("SNOWY_TENCENT_SMS_SECRET_ID", String.class, true);
////        String snowyTencentSmsSecretKey = getSysConfig("SNOWY_TENCENT_SMS_SECRET_KEY", String.class, true);
////        String snowyTencentSmsSdkAppId = getSysConfig("SNOWY_TENCENT_SMS_SDK_APP_ID", String.class, true);
////        String snowyTencentSmsSign = getSysConfig("SNOWY_TENCENT_SMS_SIGN", String.class, true);
////
////        TencentSmsConfigs tencentSmsConfigs = new TencentSmsConfigs();
////        tencentSmsConfigs.setSecretId(snowyTencentSmsSecretId);
////        tencentSmsConfigs.setSecretKey(snowyTencentSmsSecretKey);
////        tencentSmsConfigs.setSdkAppId(snowyTencentSmsSdkAppId);
////        tencentSmsConfigs.setSign(snowyTencentSmsSign);
////        return tencentSmsConfigs;
////    }
//
//    /**
//     * 获取Druid默认用户名密码
//     *
//     * @date 2022/5/16
//     */
////    public static DruidProperties getDruidLoginConfigs() {
////        String snowyDruidLoginUsername = getSysConfigWithDefault("SNOWY_DRUID_LOGIN_USERNAME", String.class, RandomUtil.randomString(10));
////        String snowyDruidLoginPassword = getSysConfigWithDefault("SNOWY_DRUID_LOGIN_PASSWORD", String.class, RandomUtil.randomString(10));
////
////        DruidProperties druidProperties = new DruidProperties();
////        druidProperties.setLoginUsername(snowyDruidLoginUsername);
////        druidProperties.setLoginPassword(snowyDruidLoginPassword);
////        return druidProperties;
////    }
//
//    /**
//     * 获取阿里云短信的配置
//     *
//     * @date 2020/6/19 18:08
//     */
////    public static AliyunSmsConfigs getAliyunSmsConfigs() {
////            String snowySmsAccesskeyId = getSysConfig("SNOWY_ALIYUN_SMS_ACCESSKEY_ID", String.class, true);
////        String snowySmsAccesskeySecret = getSysConfig("SNOWY_ALIYUN_SMS_ACCESSKEY_SECRET", String.class, true);
////        String snowySmsSignName = getSysConfig("SNOWY_ALIYUN_SMS_SIGN_NAME", String.class, true);
////        String snowySmsLoginTemplateCode = getSysConfig("SNOWY_ALIYUN_SMS_LOGIN_TEMPLATE_CODE", String.class, true);
////        String snowySmsInvalidateMinutes = getSysConfig("SNOWY_ALIYUN_SMS_INVALIDATE_MINUTES", String.class, true);
////
////        AliyunSmsConfigs aliyunSmsProperties = new AliyunSmsConfigs();
////        aliyunSmsProperties.setAccessKeyId(snowySmsAccesskeyId);
////        aliyunSmsProperties.setAccessKeySecret(snowySmsAccesskeySecret);
////        aliyunSmsProperties.setSignName(snowySmsSignName);
////        aliyunSmsProperties.setLoginTemplateCode(snowySmsLoginTemplateCode);
////        aliyunSmsProperties.setInvalidateMinutes(Convert.toInt(snowySmsInvalidateMinutes));
////        return aliyunSmsProperties;
////    }
//
//    /**
//     * 获取jwt密钥，默认是32位随机字符串
//     *
//     * @date 2020/6/19 18:08
//     */
//    public static String getJwtSecret() {
//        return getSysConfigWithDefault("SNOWY_JWT_SECRET", String.class, RandomUtil.randomString(32));
//    }
//
//    /**
//     * 获取默认密码
//     *
//     * @date 2020/6/19 18:08
//     */
////    public static String getDefaultPassWord() {
////        return getSysConfigWithDefault("SNOWY_DEFAULT_PASSWORD", String.class, CommonConstant.DEFAULT_PASSWORD);
////    }
//
//    /**
//     * 获取会话过期时间，默认2小时
//     *
//     * @date 2020/7/9 16:18
//     */
//    public static Long getSessionTokenExpireSec() {
//        return getSysConfigWithDefault("SNOWY_SESSION_EXPIRE", Long.class, 2 * 60 * 60L);
//    }
//
//    /**
//     * 获取token过期时间（单位：秒）
//     * <p>
//     * 默认时间1天
//     *
//     * @date 2020/6/19 18:08
//     */
//    public static Long getTokenExpireSec() {
//        return getSysConfigWithDefault("SNOWY_TOKEN_EXPIRE", Long.class, 86400L);
//    }
//
//    /**
//     * 获取自定义的windows环境本地文件上传路径
//     *
//     * @date 2020/6/19 18:09
//     */
//    public static String getDefaultFileUploadPathForWindows() {
//        return getSysConfigWithDefault("SNOWY_FILE_UPLOAD_PATH_FOR_WINDOWS", String.class, "");
//    }
//
//    /**
//     * 获取自定义的linux环境本地文件上传路径
//     *
//     * @date 2020/6/19 18:09
//     */
//    public static String getDefaultFileUploadPathForLinux() {
//        return getSysConfigWithDefault("SNOWY_FILE_UPLOAD_PATH_FOR_LINUX", String.class, "");
//    }
//
//    /**
//     * 获取是否允许单用户登陆的开关， 默认为false
//     *
//     * @date 2020/6/19 18:09
//     */
//    public static Boolean getEnableSingleLogin() {
//        return getSysConfigWithDefault("SNOWY_ENABLE_SINGLE_LOGIN", Boolean.class, false);
//    }
//
//    /**
//     * 获取阿里云定位接口
//     *
//     * @date 2020/7/20 9:20
//     **/
//    public static String getIpGeoApi() {
//        return getSysConfig("SNOWY_IP_GEO_API", String.class, false);
//    }
//
//    /**
//     * 获取阿里云定位appCode
//     *
//     * @date 2020/7/20 10:33
//     **/
//    public static String getIpGeoAppCode() {
//        return getSysConfig("SNOWY_IP_GEO_APP_CODE", String.class, false);
//    }
//
//    /**
//     * 获取Oauth码云第三方登录的配置
//     *
//     * @date 2020/7/28 17:16
//     **/
////    public static OauthConfigs getGiteeOauthConfigs() {
////        String snowyClientId = getSysConfig("SNOWY_OAUTH_GITEE_CLIENT_ID", String.class, true);
////        String snowyClientSecret = getSysConfig("SNOWY_OAUTH_GITEE_CLIENT_SECRET", String.class, true);
////        String snowyRedirectUri = getSysConfig("SNOWY_OAUTH_GITEE_REDIRECT_URI", String.class, true);
////
////        OauthConfigs oauthConfigs = new OauthConfigs();
////        oauthConfigs.setClientId(snowyClientId);
////        oauthConfigs.setClientSecret(snowyClientSecret);
////        oauthConfigs.setRedirectUri(snowyRedirectUri);
////        return oauthConfigs;
////    }
//
//    /**
//     * 获取OauthGithub第三方登录的配置
//     *
//     * @date 2020/7/28 17:16
//     **/
////    public static OauthConfigs getGithubOauthConfigs() {
////        String snowyClientId = getSysConfig("SNOWY_OAUTH_GITHUB_CLIENT_ID", String.class, true);
////        String snowyClientSecret = getSysConfig("SNOWY_OAUTH_GITHUB_CLIENT_SECRET", String.class, true);
////        String snowyRedirectUri = getSysConfig("SNOWY_OAUTH_GITHUB_REDIRECT_URI", String.class, true);
////
////        OauthConfigs oauthConfigs = new OauthConfigs();
////        oauthConfigs.setClientId(snowyClientId);
////        oauthConfigs.setClientSecret(snowyClientSecret);
////        oauthConfigs.setRedirectUri(snowyRedirectUri);
////        return oauthConfigs;
////    }
//
//    /**
//     * 获取是否允许Oauth用户登陆的开关， 默认为false
//     *
//     * @date 2020/7/28 16:37
//     **/
//    public static Boolean getEnableOauthLogin() {
//        return getSysConfigWithDefault("SNOWY_ENABLE_OAUTH_LOGIN", Boolean.class, false);
//    }
//
//    /**
//     * 获取前端项目的地址
//     *
//     * @date 2020/7/29 14:08
//     **/
//    public static String getWebUrl() {
//        return getSysConfig("SNOWY_WEB_URL", String.class, true);
//    }
//
//    /**
//     * 获取支付宝支付成功转发地址
//     *
//     * @date 2020/7/29 14:08
//     **/
//    public static String getAlipayReturnUrl() {
//        return getSysConfig("SNOWY_ALIPAY_RETURN_URL", String.class, true);
//    }
//
//
//    /**
//     * 获取OnlyOffice地址
//     *
//     * @date 2020/7/29 14:08
//     **/
//    public static String getOnlyOfficeUrl() {
//        return getSysConfig("SNOWY_ONLY_OFFICE_SERVICE_URL", String.class, true);
//    }
//
//    /**
//     * 获取config表中的配置，如果为空返回默认值
//     *
//     * @param configCode   变量名称，对应sys_config表中的code
//     * @param clazz        返回变量值的类型
//     * @param defaultValue 如果结果为空返回此默认值
//     * @date 2020/6/20 22:03
//     */
//    public static <T> T getSysConfigWithDefault(String configCode, Class<T> clazz, T defaultValue) {
//        String configValue = ConstantContext.me().getStr(configCode);
//        if (ObjectUtil.isEmpty(configValue)) {
//            // 将默认值加入到缓存常量
//            log.warn(">>> 系统配置sys_config表中存在空项，configCode为：{}，系统采用默认值：{}", configCode, defaultValue);
//            ConstantContext.me().put(configCode, defaultValue);
//            return defaultValue;
//        } else {
//            try {
//                return Convert.convert(clazz, configValue);
//            } catch (Exception e) {
//                return defaultValue;
//            }
//        }
//    }
//
//    /**
//     * 获取config表中的配置，如果为空，是否抛出异常
//     *
//     * @param configCode   变量名称，对应sys_config表中的code
//     * @param clazz        返回变量值的类型
//     * @param nullThrowExp 若为空是否抛出异常
//     * @date 2020/6/20 22:03
//     */
//    public static <T> T getSysConfig(String configCode, Class<T> clazz, Boolean nullThrowExp) {
//        String configValue = ConstantContext.me().getStr(configCode);
//        if (ObjectUtil.isEmpty(configValue)) {
//            if (nullThrowExp) {
//                String format = StrUtil.format(">>> 系统配置sys_config表中存在空项，configCode为：{}", configCode);
//                log.error(format);
//            } else {
//                return null;
//            }
//        } else {
//            try {
//                return Convert.convert(clazz, configValue);
//            } catch (Exception e) {
//                if (nullThrowExp) {
//                    String format = StrUtil.format(">>> 系统配置sys_config表中存在格式错误的值，configCode={}，configValue={}", configCode, configValue);
//                    log.error(format);
//                } else {
//                    return null;
//                }
//            }
//        }
//        return null;
//    }
//}
