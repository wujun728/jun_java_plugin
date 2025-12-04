package com.item.util.encrypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import com.alibaba.fastjson.JSONObject;

/**
 * <p> RSA公钥/私钥/签名工具包 </p>
 * <p> 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman） </p>
 * <p>
 *  字符串格式的密钥在未在特殊说明情况下都为16进制编码格式<br/>
 *  由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 *  非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * 
 * @author BianPneg
 * @date 2019-5-8
 * @version 1.0
 */ 
public class RSA {
 
    public static final String KEY_ALGORITHM = "RSA";
    public static final String split = "$";//分隔符
    public static final int max = 117;//加密分段长度//不可超过117
 
    private static RSA me;
    public RSA(){}//单例
    public static RSA create(){
        if (me==null) {
            me = new RSA();
        }
        //生成公钥、私钥
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            kpg.initialize(1024);
            KeyPair kp = kpg.generateKeyPair();
            me.publicKey = (RSAPublicKey) kp.getPublic();
            me.privateKey = (RSAPrivateCrtKey) kp.getPrivate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return me;
    }
 
    private RSAPublicKey publicKey;
    private RSAPrivateCrtKey privateKey;
 
    /**获取公钥*/
    public String getPublicKey(){
        return parseByte2HexStr(publicKey.getEncoded());
    }
 
    /**获取私钥*/
    public String getPrivateKey(){
        return parseByte2HexStr(privateKey.getEncoded());
    }
 
    /**加密-公钥*/
    public String encodeByPublicKey(String res,String key){
        byte[] resBytes = res.getBytes();
        byte[] keyBytes = parseHexStr2Byte(key);//先把公钥转为2进制
        StringBuffer result = new StringBuffer();//结果
        //如果超过了100个字节就分段
        if (keyBytes.length<=max) {//不超过直接返回即可
            return encodePub(resBytes, keyBytes);
        }else {
            int size = resBytes.length/max + (resBytes.length%max>0?1:0);
            for (int i = 0; i < size; i++) {
                int len = i==size-1?resBytes.length%max:max;
                byte[] bs = new byte[len];//临时数组
                System.arraycopy(resBytes, i*max, bs, 0, len);
                result.append(encodePub(bs, keyBytes));
                if(i!=size-1)result.append(split);
            }
            return result.toString();
        }
    }
    /**加密-私钥*/
    public String encodeByPrivateKey(String res,String key){
        byte[] resBytes = res.getBytes();
        byte[] keyBytes = parseHexStr2Byte(key);
        StringBuffer result = new StringBuffer();
        //如果超过了100个字节就分段
        if (keyBytes.length<=max) {//不超过直接返回即可
            return encodePri(resBytes, keyBytes);
        }else {
            int size = resBytes.length/max + (resBytes.length%max>0?1:0);
            for (int i = 0; i < size; i++) {
                int len = i==size-1?resBytes.length%max:max;
                byte[] bs = new byte[len];//临时数组
                System.arraycopy(resBytes, i*max, bs, 0, len);
                result.append(encodePri(bs, keyBytes));
                if(i!=size-1)result.append(split);
            }
            return result.toString();
        }
    }
    /**解密-公钥*/
    public String decodeByPublicKey(String res,String key){
        byte[] keyBytes = parseHexStr2Byte(key);
        //先分段
        String[] rs = res.split("\\"+split);
        //分段解密
        if(rs!=null){
            int len = 0;
            //组合byte[]
            byte[] result = new byte[rs.length*max];
            for (int i = 0; i < rs.length; i++) {
                byte[] bs = decodePub(parseHexStr2Byte(rs[i]), keyBytes);
                System.arraycopy(bs, 0, result, i*max, bs.length);
                len+=bs.length;
            }
            byte[] newResult = new byte[len];
            System.arraycopy(result, 0, newResult, 0, len);
            //还原字符串
            return new String(newResult);
        }
        return null;
    }
    /**解密-私钥*/
    public String decodeByPrivateKey(String res,String key){
        byte[] keyBytes = parseHexStr2Byte(key);
        //先分段
        String[] rs = res.split("\\"+split);
        //分段解密
        if(rs!=null){
            int len = 0;
            //组合byte[]
            byte[] result = new byte[rs.length*max];
            for (int i = 0; i < rs.length; i++) {
                byte[] bs = decodePri(parseHexStr2Byte(rs[i]), keyBytes);
                System.arraycopy(bs, 0, result, i*max, bs.length);
                len+=bs.length;
            }
            byte[] newResult = new byte[len];
            System.arraycopy(result, 0, newResult, 0, len);
            //还原字符串
            return new String(newResult);
        }
        return null;
    }
 
    /**将二进制转换成16进制 */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {
                hex = '0' + hex;  
            }
            sb.append(hex.toUpperCase());  
        }
        return sb.toString();  
    }
    /**将16进制转换为二进制*/
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
            result[i] = (byte) (high * 16 + low);  
        }
        return result;  
    }
 
    /**加密-公钥-无分段*/
    private String encodePub(byte[] res,byte[] keyBytes){
        X509EncodedKeySpec x5 = new X509EncodedKeySpec(keyBytes);//用2进制的公钥生成x509
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            Key pubKey = kf.generatePublic(x5);//用KeyFactory把x509生成公钥pubKey
            Cipher cp = Cipher.getInstance(kf.getAlgorithm());//生成相应的Cipher
            cp.init(Cipher.ENCRYPT_MODE, pubKey);//给cipher初始化为加密模式，以及传入公钥pubKey
            return parseByte2HexStr(cp.doFinal(res));//以16进制的字符串返回
        } catch (Exception e) {
            System.out.println("公钥加密失败");
            e.printStackTrace();
        }
        return null;
    }
    /**加密-私钥-无分段*/
    private String encodePri(byte[] res,byte[] keyBytes){
        PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            Key priKey = kf.generatePrivate(pk8);
            Cipher cp = Cipher.getInstance(kf.getAlgorithm());
            cp.init(Cipher.ENCRYPT_MODE, priKey);
            return parseByte2HexStr(cp.doFinal(res));
        } catch (Exception e) {
            System.out.println("私钥加密失败");
            e.printStackTrace();
        }
        return null;
    }
    /**解密-公钥-无分段*/
    private byte[] decodePub(byte[] res,byte[] keyBytes){
        X509EncodedKeySpec x5 = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            Key pubKey = kf.generatePublic(x5);
            Cipher cp = Cipher.getInstance(kf.getAlgorithm());
            cp.init(Cipher.DECRYPT_MODE, pubKey);
            return cp.doFinal(res);
        } catch (Exception e) {
            System.out.println("公钥解密失败");
            e.printStackTrace();
        }
        return null;
    }
    /**解密-私钥-无分段*/
    private byte[] decodePri(byte[] res,byte[] keyBytes){
        PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            Key priKey = kf.generatePrivate(pk8);
            Cipher cp = Cipher.getInstance(kf.getAlgorithm());
            cp.init(Cipher.DECRYPT_MODE, priKey);
            return cp.doFinal(res);
        } catch (Exception e) {
            System.out.println("私钥解密失败");
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String ...args){
    	
    	Map<String, Object> result = new HashMap<>();
    	result.put("param1", "value1");
    	result.put("param2", "value2");
    	result.put("param3", "value3");
    	result.put("param4", "value4");
    	System.out.println(JSONObject.toJSONString(result));
        RSA rsa =RSA.create();
        String pubKey = rsa.getPublicKey();
        String priKey = rsa.getPrivateKey();
        //String pubKey = "30819F300D06092A864886F70D010101050003818D00308189028181009175F0C9A7FE52D5A05FFCEF93CC7011CAB62523D73F937E5B6E40F9E8D597D71E8BE8C883B34FA64B42ACE747374A3E22BBBB02CBD5EE4A126C7F2D040C744BAA40AA3D8ED1A9A2D6CC822A7ABD61432FDD93BA11A8627C424F37E9AD1D39C30A9CFA1374F3D2FFC6F422465D3C2B39480D3864683954C7BB378CB521AE77930203010001";
        //String priKey = "30820275020100300D06092A864886F70D01010105000482025F3082025B020100028181009175F0C9A7FE52D5A05FFCEF93CC7011CAB62523D73F937E5B6E40F9E8D597D71E8BE8C883B34FA64B42ACE747374A3E22BBBB02CBD5EE4A126C7F2D040C744BAA40AA3D8ED1A9A2D6CC822A7ABD61432FDD93BA11A8627C424F37E9AD1D39C30A9CFA1374F3D2FFC6F422465D3C2B39480D3864683954C7BB378CB521AE779302030100010281802FF4780BA36CBF165AA70A96595D9EDCDEBDAA04E4E3BAD67F821BBDC83B12B2030A11167A04D58F776465E8619C22C7D55F9AC9D3359637A957479E29A5897920A1C4289CEC80AF8387030C6C83B9AC0909628911B6D7573C92C3C763131D1C157D93570D0E635292AC32F50A560AF5D40055BF4AD114688433D1935AA74231024100D5CBD998F7B6335AEC3E23F352D03F58D1C88749C03406285B8D5CEADFB6E96476C69AAEDCB4BC88443DB793E3D10FEA68EDC6C077D9239A5D047CBF9D51125D024100AE2CC4328C00F928F10AFBFF60CC30F74C38B1B27432113B0B4C5656483134DDD8FD58348E52F10395970BD2A9D19AE51994E9DC3C94CF136FA2F6788313F2AF024065B4D13254FD475704B5D712651E0E54A98FD4D43FEC3FD5A92009C8EE5E2C8F8F75919688062EFE40407FF989D225D924CBA0D664ED566393C693B5F017E63D02402A63BEC4468A9E1936C0E39FBDA134719B5D58F2039183DC08DDE54D4E789B533AD23DBB5CC016E4366DF274ACDBD484D97B0287953EEA3EE2B698A1AFD51505024015E8521DF87C62A9F211A1A465D6E95E143442699CA9BAD9F5128920583D7BD8E866A8BC43F2B8651CFAF0BFF08F44DE70572BD954A6AC49303C267D5E53A9A2";
        System.out.println("pubKey:"+pubKey);
        System.out.println("priKey:"+priKey);
        //原文
        StringBuffer res = new StringBuffer();
        res.append("[{\"adderss\":\"入账地址1\",\"amount\":10,\"coinType\":\"BTC\",\"platform\":\"bitmex\",\"remark\":\"火币平台补仓\"},{\"adderss\":\"入账地址2\",\"amount\":20,\"coinType\":\"BTC\",\"platform\":\"bitmex\",\"remark\":\"火币平台补仓\"}]");
        System.out.println("原文对比:"+res.toString());
        System.out.println("------------");
        String enStr = rsa.encodeByPublicKey(res.toString(), pubKey);
        String deStr = rsa.decodeByPrivateKey(enStr,priKey);
        System.out.println("公钥加密:"+enStr);
        System.out.println("私钥解密:"+deStr);
        System.out.println("------------");
        enStr = rsa.encodeByPrivateKey(res.toString(), priKey);
        deStr = rsa.decodeByPublicKey(enStr, pubKey);
        System.out.println("私钥加密:"+enStr);
        System.out.println("公钥解密:"+deStr);
    }
}

