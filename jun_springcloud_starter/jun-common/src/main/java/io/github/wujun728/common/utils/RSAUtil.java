package io.github.wujun728.common.utils;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class RSAUtil {

    /**
     * generate  secret key
     *
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * get the Base64 encoded publicKey
     *
     * @param keyPair
     * @return
     */
    public static String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    /**
     * get the Base64 encoded privateKey
     *
     * @param keyPair
     * @return
     */
    public static String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    /**
     * generate the PublicKey object based on the publicKey string
     *
     * @param pubStr
     * @return
     * @throws Exception
     */
    private static PublicKey string2PublicKey(String pubStr) throws Exception {
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * generate the PrivateKey object based on the PrivateKey string
     *
     * @param priStr
     * @return
     * @throws Exception
     */
    private static PrivateKey string2PrivateKey(String priStr) throws Exception {
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * use publicKey to encrypt
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    private static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    /**
     * use private decrypt
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    private static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    private static String byte2Base64(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

    private static byte[] base642Byte(String base64Key) throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64Key);
    }

    /**
     * use the publicKeyStr to encrypt the message
     *
     * @param message
     * @param publicKeyStr
     * @return
     */
    public static String encrypt(String message, String publicKeyStr) throws Exception {
        PublicKey publicKey = string2PublicKey(publicKeyStr);
        byte[] publicEncrypt = publicEncrypt(message.getBytes(), publicKey);
        String byte2Base64 = byte2Base64(publicEncrypt);
        return byte2Base64;
    }

    /**
     * use private to decrypt get the origin message
     *
     * @param encrypt
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String decrypt(String encrypt, String privateKeyStr) throws Exception {
        PrivateKey privateKey = string2PrivateKey(privateKeyStr);
        byte[] base642Byte = base642Byte(encrypt);
        byte[] privateDecrypt = privateDecrypt(base642Byte, privateKey);
        return new String(privateDecrypt);
    }


    private static final String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3jg5eFRbBNeIg20xM6Ukn8Mu4WmvqxdSbmjg74T4o52zfUP/RZfIwx+7yt3aaEuxqkgTagaV1oR91uR6IQqmk++05gHzgigIK8G+eX9fGqnZWDaugH5HUteTyY8SjvCRY9gxuaP6jNp9r+Ngk9EC4m8qlZY4cRx3XySlEPjgJinvtzWTOvszG84N87r3RBKl/989itTFr/lLXdERawme6C5/wTQXpvYB8WHY8hnK/IkZ2ynS1eCFSCHoLyouUr/dCT+wPEFotGtK7mjle6U1MxVzehZqsT119M3diSDXNoOxF18GNfYS68RnvDhDuIUFFtkSGjHqoxKFjpbArjNykQIDAQAB";

    private static final String privateKeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDeODl4VFsE14iDbTEzpSSfwy7haa+rF1JuaODvhPijnbN9Q/9Fl8jDH7vK3dpoS7GqSBNqBpXWhH3W5HohCqaT77TmAfOCKAgrwb55f18aqdlYNq6AfkdS15PJjxKO8JFj2DG5o/qM2n2v42CT0QLibyqVljhxHHdfJKUQ+OAmKe+3NZM6+zMbzg3zuvdEEqX/3z2K1MWv+Utd0RFrCZ7oLn/BNBem9gHxYdjyGcr8iRnbKdLV4IVIIegvKi5Sv90JP7A8QWi0a0ruaOV7pTUzFXN6FmqxPXX0zd2JINc2g7EXXwY19hLrxGe8OEO4hQUW2RIaMeqjEoWOlsCuM3KRAgMBAAECggEAZ1Dwt1dQ8hFbcO9lfsN7IaRXuYmvmPZA5wBwBCxohXmep9DjcHG/b6jjYNCA6Ri4Q5k7HYJuS8xRzYVwmsge5oz+KCokExGVqvJrIoXrq7PoTzEtNKSBeKyuE3ongLmsfyVXCR6KMM6svQgUMOVuV+SjgeTT3RMFFQEulZOAc30R752aJSc6g4uqQUf1goqlfpMpJ8W3iOWHJ/T4elI0xmlbDffWr1mV8DJSKrWD/Ynnd1EYhllaKcNV7UUWtu1hPgbOKz0pCtOFYeRqHG45xkm0vaoFg+neV2pS//tqjah5tvcPoldNVTXRkwvRz78jmhLob5ndffNHx+kZC4a4OQKBgQD470aPAe5jytdNUsasmDWMPDdOzBt6AEDGky0PpyoT1u2XEMFlk9S/tM2ywYWIbGacm4Qjt5zYSKXNI9ypf04ol9ZvaXXVRNoE6ad/QBUxCmI0pvdMYwebfMnJYCXcQrcmZIKCnWbHwubwpKM8dexFkYPNyzZMYjZFPgW10p8x+wKBgQDkhtdTYJxMBlJVLgRh+TVeYRCac4Vt6Okha6ZfXbgyfc1wvEDZ59eQoFruNX0IJT/E56jpYVyGEmjeS3MgAV356c6ks9lV5t3yG3VlwUxQRi1/lyCAzsL7rankLwr9S7j85j1dJ526/7QwfmP41GRTsy9wJPR7/iTx7v+ia1WT4wKBgGGu2zxA4MAOF8CC6+1MaS7XbkigdWPBd6m0lSkgSrWwUFlIPOvUP9beZx1vLSwkhXuM1ySA565Di7RdDzj/+LabJo1fj9qZgwgMfCrGBcrRrUnF/yLddV3BcRlxfknZcC9Dn6cLhwp6Y88oe9m82HNQL61wwaTloTA4r1rzBH9DAoGBAJvuXhKoQz2kup0gjyEC2KWTea7J0GuUly6Us6sy2bKqIHoiGeYVygMHR8RufugF8qM6pwjHxEg/W4sC1IUsKaGTJctAZLW2mA7RKaPK53WGzRjYV/dMHcN5Vgk7CDaZapo1x/0+QOQOAjdspm5tDiAGjgHpVWEdorvqJkf/ER+dAoGAFrumnpZtR20UlcNeQ6Rt0NZHhpBFtBqO0xpm+L3ZqneW0WLr7wTpWfEZu45XMdBmX6ftIF8gNtub+Po5/dHn8cML/+n8asl99E6kxrr+hZ6+Pge3X382x2BK6Et3nJIEjd2wGut+Ifu+gOhWYWAClX6mASfN6BRnywo5vM/BGdA=";

    public static void main(String[] ar) throws Exception {
        //generate RSA publicKey and privateKey and Based64 encode it.
     /*   KeyPair keyPair = RSAUtil.getKeyPair();
        String publicKeyStr = RSAUtil.getPublicKey(keyPair);
        String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
        System.out.println("publicKey:" + publicKeyStr);
        System.out.println("privateKey:" + privateKeyStr);*/

        //=================client encrypt=================
        String message = "account_id";
        String encrypt = RSAUtil.encrypt(message, publicKeyStr);
        System.out.println("use publicKey encrypt result:" + encrypt);

        //===================server decrypt================
        String res = RSAUtil.decrypt(encrypt, privateKeyStr);
        System.out.println("use the privateKey decrypt result:" + res);
    }
}