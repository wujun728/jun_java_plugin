package mybatis.mate.sm;

import mybatis.mate.encrypt.GMEncryptor;
import mybatis.mate.encrypt.KeyGenerator;
import mybatis.mate.sm.entity.User;
import mybatis.mate.sm.mapper.UserMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.List;

/**
 * 国密 SM2 SM3 SM4 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptTest {
    @Resource
    private UserMapper mapper;

    @Test
    public void test() {
        User user = new User(1L, "hi china", "123456", "asd@qq.com", "asd", "asd", null);
        Assertions.assertEquals(1, mapper.insert(user));
        System.err.println("加密内容：" + user);
        user = mapper.selectById(user.getId());
        System.err.println("查询数据库内容：" + user);
        Assertions.assertEquals("asd@qq.com", user.getEmail());
        Assertions.assertEquals("asd", user.getSm2());
        Assertions.assertEquals(1, mapper.insert(new User(2L, "hi mp",
                "123456", "mp@qq.com", "mp", "mp", null)));
        List<User> userList = mapper.selectList(null);
        Assertions.assertEquals(userList.size(), 2);
        for (User _user : userList) {
            Assertions.assertEquals("bind_md_" + _user.getSm3(), _user.getSm3Text());
            if (_user.getId().equals(1L)) {
                Assertions.assertEquals("asd@qq.com", _user.getEmail());
            } else if (_user.getId().equals(2L)) {
                Assertions.assertEquals("mp@qq.com", _user.getEmail());
            }
        }
    }


    @Test
    public void sm2Test() throws Exception {
        final String test = "hi 测试数据。。..";
        System.out.println("原始数据：" + test);

        // 生成公私钥对
        String[] keys = KeyGenerator.generateSM2Keys();
        System.out.println("公钥：" + keys[0]);
        System.out.println();
        PublicKey publicKey = KeyGenerator.createPublicKey(keys[0]);
        System.out.println("私钥：" + keys[1]);
        System.out.println();
        PrivateKey privateKey = KeyGenerator.createPrivateKey(keys[1]);

        // 加解密测试
        byte[] encrypt = GMEncryptor.sm2Encrypt(test.getBytes(), publicKey);
        String encryptBase64Str = Base64.getEncoder().encodeToString(encrypt);
        System.out.println("加密数据：" + encryptBase64Str);
        System.out.println("等效于这样加密数据：" + GMEncryptor.sm2Encrypt(test, keys[0]));

        byte[] decrypt = GMEncryptor.sm2Decrypt(encrypt, privateKey);
        String decryptStr = new String(decrypt);
        System.out.println("解密数据：" + decryptStr);
        Assertions.assertEquals(GMEncryptor.sm2Decrypt(encryptBase64Str, keys[1]), decryptStr);

        byte[] sign = GMEncryptor.sm2SignByPrivateKey(test.getBytes(), privateKey);
        System.out.println("数据签名：" + Base64.getEncoder().encodeToString(sign));

        boolean b = GMEncryptor.sm2VerifyByPublicKey(test.getBytes(), publicKey, sign);
        System.out.println("数据验签：" + b);
    }

    @Test
    public void sm3Test() {
        Assertions.assertEquals("96f565b02cb226202c6863dcf12bbb78b40d16e96eda1f100ddf9b33aee98488",
                GMEncryptor.sm3digest("国密 SM3 摘要算法".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void sm4Test() throws Exception {
        final String key = KeyGenerator.generateSM4Key();
        System.err.println("加密 KEY = " + key);

        final String data = "国密 SM4 分组密码算法";
        String output = GMEncryptor.sm4Encrypt(key, data);
        System.err.println("SM4 算法加密：" + output);
        Assertions.assertEquals(data, GMEncryptor.sm4Decrypt(key, output));
    }
}
