//package com.jun.plugin.common.utils;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 密码加密
// *
// * @author wujun
// * @version V1.0
// * @date 2020年3月18日
// */
//public class PasswordEncoderAES {
//
//    private static Logger logger = LoggerFactory.getLogger(PasswordEncoderAES.class);
//
//
//    private final static String AES = "AES";
//
//    private Object salt;
//    private String algorithm;
//
//    public PasswordEncoderAES(Object salt) {
//        this(salt, AES);
//    }
//
//    public PasswordEncoderAES(Object salt, String algorithm) {
//        this.salt = salt;
//        this.algorithm = algorithm;
//    }
//
//    /**
//     * 密码加密
//     *
//     * @param rawPass 密码
//     * @return 加密后
//     */
//    public String encode(String rawPass) {
//        String result = null;
//        try {
//            // 加密后的字符串
//        	result = EncryptUtils.aesEncrypt(rawPass, EncryptUtils.KEY);
//        } catch (Exception e) {
//            logger.error(e.toString(), e);
//        }
//        return result;
//    }
//
//    /**
//     * 密码匹配验证
//     *
//     * @param encPass 密文
//     * @param rawPass 明文
//     * @return 是否匹配
//     */
//    public boolean matches(String encPass, String rawPass) {
//        String pass1 = "" + encPass;
//        String pass2 = encode(rawPass);
//
//        return pass1.equals(pass2);
//    }
//
//
//}