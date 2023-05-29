package mybatis.mate.encrypt.config;

import mybatis.mate.annotation.Algorithm;
import mybatis.mate.encrypt.IEncryptor;
import org.apache.ibatis.reflection.MetaObject;

// 自定义加密算法，这里为开启使用默认加密库
//@Component
public class CustomEncryptor implements IEncryptor {

    /**
     * 加密
     *
     * @param algorithm  算法
     * @param password   密码（对称加密算法密钥）
     * @param plaintext  明文
     * @param publicKey  非对称加密算法（公钥）
     * @param metaObject {@link org.apache.ibatis.reflection.MetaObject}
     * @return
     */
    @Override
    public String encrypt(Algorithm algorithm, String password, String publicKey,
                          String plaintext, Object metaObject) {
        if (metaObject instanceof MetaObject) {
            MetaObject _metaObject = ((MetaObject) metaObject);
            // 获取待加密对象 name 属性值，注意 src 是注解属性值框架层已经取出来了，这里是查询行对象任意值获取
            _metaObject.getValue("name");
        }
        return "加密返回";
    }

    /**
     * 解密
     *
     * @param algorithm  算法
     * @param password   密码（对称加密算法密钥）
     * @param encrypt    密文
     * @param privateKey 非对称加密算法（私钥）
     * @param metaObject {@link org.apache.ibatis.reflection.MetaObject}
     * @return
     */
    @Override
    public String decrypt(Algorithm algorithm, String password, String privateKey,
                          String encrypt, Object metaObject) {
        if (metaObject instanceof MetaObject) {
            MetaObject _metaObject = ((MetaObject) metaObject);
            // 获取待解密对象 name 属性值，注意 encrypt 是注解属性值框架层已经取出来了，这里是查询行对象任意值获取
            _metaObject.getValue("name");
        }
        return "解密返回";
    }
}
