package mybatis.mate.datascope.config;

import mybatis.mate.license.IGrantSupplier;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 这是二次授权逻辑，加密原来 grant 混入自己的授权逻辑，比如可以验证 Mac 地址
 */
@Component
public class MyGrantSupplier implements IGrantSupplier {

    @Override
    public String get(String grant) {
        /**
         * 获取到自定义加密逻辑字符串，然后验证 Mac 地址什么的认证客户机唯一性，案例配置的 grant 是下面代码加密的
         *
         * <pre>
         * Base64.getEncoder().encodeToString("thisIsTestLicense#张三".getBytes(StandardCharsets.UTF_8));
         * </pre>
         */
        String myGrant = new String(Base64.getDecoder().decode(grant.getBytes(StandardCharsets.UTF_8)));
        System.err.println("这是我的二次授权证书：" + myGrant);

        // 把混淆解密的原证书在内存中返回给顶层授权逻辑
        return myGrant.split("#")[0];
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
