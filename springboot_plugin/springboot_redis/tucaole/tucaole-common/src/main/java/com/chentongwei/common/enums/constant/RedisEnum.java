package com.chentongwei.common.enums.constant;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Redis的前缀常量
 */
public enum RedisEnum {

    /**
     * 公用模块
     */
    //省市区
    LOCATION("location"),

    /**
     * 系统模块
     */
    //每天举报次数
    REPORT_COUNT("report_count"),
    //吐槽了举报类型
    REPORT_TYPE("report_type"),

    /**
     * 用户模块
     */
    //已登录的用户
    LOGINED_USER("logined"),
    //注册时，将token和userid set到redis，激活用户需要验证用
    ACTIVE_USER("active_user"),
    //忘记密码
    FORGET_PASSWORD("forget_password"),
    //忘记密码发邮件次数，每个邮箱大于5次就不让发了。
    FORGET_PWD_EMAIL_COUNT("forget_pwd_email_count"),
    //发送源邮箱
    SEND_SOURCE_EMAIL("source_mail"),
    //发送源邮箱次数
    SEND_SOURCE_EMAIL_COUNT("source_mail_count"),
    //发送目标邮箱
    SEND_TARGET_EMAIL("target_mail"),
    //收藏夹每天创建最大量
    FAVORITE_COUNT_MAX("favorite"),
    //每天最多收藏文章个数
    SAVE_TO_FAVORITE_COUNT_MAX("save_to_favorite"),
    //好友关注的人数
    USER_FOLLOW_COUNT("follow_count"),

    /**
     * 吐槽模块
     */
    //分类key
    TUCAO_CATALOG("tc_catalog"),
    //点赞（支持）key（Set集合）
    TUCAO_SUPPORT("tc_support"),
    //反对key（Set集合）
    TUCAO_OPPOSE("tc_oppose"),
    //点赞反对key（Hash）
    TUCAO_HASH_ARTICLE_SUPPORT("tc_support_to_db"),
    //文章评论总数
    TUCAO_ARTICLE_COMMENT_COUNT("tc_comment_count"),
    //15s只能评论一次
    CHECK_COMMENT_COUNT("check_comment_count")
    ;

    /**
     * redis的key
     */
    private String prefixKey;

    RedisEnum(String prefixKey) {
        this.prefixKey = prefixKey;
    }

    private String getPrefixKey() {
        return prefixKey;
    }

    /////////////////////公用模块///////////////////////////////
    /**
     * 获取省市区的key
     *
     * @return
     */
    public static final String getLocationKey() {
        return LOCATION.getPrefixKey();
    }

    /////////////////////系统模块///////////////////////////////
    /**
     * 获取每天举报次数的key
     * @return
     */
    public static final String getReportCountKey(String suffixKey) {
        return REPORT_COUNT.getPrefixKey() + suffixKey;
    }

    /**
     * 获取吐槽了举报类型的key
     * @return
     */
    public static final String getReportTypeKey() {
        return REPORT_TYPE.getPrefixKey();
    }

    /////////////////////用户模块///////////////////////////////
    /**
     * 获取已登录的用户的key
     *
     * @param suffixKey：key
     * @return
     */
    public static final String getLoginedUserKey(String suffixKey) {
        return LOGINED_USER.getPrefixKey() + suffixKey;
    }

    /**
     * 获取忘记密码的key
     *
     * @param suffixKey：key
     * @return
     */
    public static final String getForgetPasswordKey(String suffixKey) {
        return FORGET_PASSWORD.getPrefixKey() + suffixKey;
    }

    /**
     * 获取忘记密码发邮件次数的key
     *
     * @param suffixKey：key
     * @return
     */
    public static final String getForgetPwdEmailCountKey(String suffixKey) {
        return FORGET_PWD_EMAIL_COUNT.getPrefixKey() + suffixKey;
    }

    /**
     * 获取更改邮箱--发送源邮箱验证码的key
     *
     * @param suffixKey：key
     * @return
     */
    public static final String getSendSourceEmailKey(String suffixKey) {
        return SEND_SOURCE_EMAIL.getPrefixKey() + suffixKey;
    }

    /**
     * 获取更改邮箱--发送源邮箱次数的key
     *
     * @param suffixKey：key
     * @return
     */
    public static final String getSendSourceEmailCountKey(String suffixKey) {
        return SEND_SOURCE_EMAIL_COUNT.getPrefixKey() + suffixKey;
    }

    /**
     * 获取更改邮箱--发送目标邮箱验证码的key
     *
     * @param suffixKey：key
     * @return
     */
    public static final String getSendTargetEmailKey(String suffixKey) {
        return SEND_TARGET_EMAIL.getPrefixKey() + suffixKey;
    }

    /**
     * 获取激活用户的key
     *
     * @param suffixKey：key
     * @return
     */
    public static final String getActiveUserKey(String suffixKey) {
        return ACTIVE_USER.getPrefixKey() + suffixKey;
    }

    /**
     * 获取每日最多创建收藏夹个数key
     *
     * @param suffixKey：key
     * @return
     */
    public static final String getFavoriteCountMax(String suffixKey) {
        return FAVORITE_COUNT_MAX.getPrefixKey() + suffixKey;
    }

    /**
     * 获取每日最多收藏文章个数key
     *
     * @param suffixKey：key
     * @return
     */
    public static final String getSaveToFavoriteCountMax(String suffixKey) {
        return SAVE_TO_FAVORITE_COUNT_MAX.getPrefixKey() + suffixKey;
    }

    /**
     * 获取好友关注数key
     *
     * @param suffixKey：key
     * @return
     */
    public static final String getUserFollowCount(String suffixKey) {
        return USER_FOLLOW_COUNT.getPrefixKey() + suffixKey;
    }

    /////////////////////吐槽模块///////////////////////////////
    /**
     * 获取吐槽分类的key
     *
     * @return
     */
    public static final String getTucaoCatalogKey() {
        return TUCAO_CATALOG.getPrefixKey();
    }
    /**
     * 获取吐槽点赞（支持）的key
     *
     * @return
     */
    public static final String getTucaoSupportKey(String suffixKey) {
        return TUCAO_SUPPORT.getPrefixKey() + suffixKey;
    }
    /**
     * 获取吐槽反对的key
     *
     * @return
     */
    public static final String getTucaoOpposeKey(String suffixKey) {
        return TUCAO_OPPOSE.getPrefixKey() + suffixKey;
    }
    /**
     * 获取吐槽点赞反对的key（Hash结构）
     *
     * @return
     */
    public static final String getTucaoHashArticleSupportKey(String suffixKey) {
        return TUCAO_HASH_ARTICLE_SUPPORT.getPrefixKey() + suffixKey;
    }

    /**
     * 获取吐槽文章评论总数的key
     *
     * @return
     */
    public static final String getTucaoArticleCommentCount(String suffixKey) {
        return TUCAO_ARTICLE_COMMENT_COUNT.getPrefixKey() + suffixKey;
    }

    /**
     * 15s只能评论一次
     *
     * @return
     */
    public static final String getCheckCommentCount(String suffixKey) {
        return CHECK_COMMENT_COUNT.getPrefixKey() + suffixKey;
    }

}
