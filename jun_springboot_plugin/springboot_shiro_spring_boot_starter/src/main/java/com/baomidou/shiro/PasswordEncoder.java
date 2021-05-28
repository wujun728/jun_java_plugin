package com.baomidou.shiro;

import lombok.Setter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 密码加密器
 *
 * @author Wujun
 */
public class PasswordEncoder {

  @Setter
  private HashedCredentialsMatcher hashedCredentialsMatcher;

  public PasswordDetail encode(String password, String salt) {
    String hashAlgorithmName = hashedCredentialsMatcher.getHashAlgorithmName();
    int hashIterations = hashedCredentialsMatcher.getHashIterations();
    SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
    String hashedPassword =
        hashedCredentialsMatcher.isStoredCredentialsHexEncoded() ? simpleHash.toHex()
            : simpleHash.toBase64();
    return new PasswordDetail(password, salt, hashedPassword);
  }

  public PasswordDetail encode(String password) {
    return encode(password, new SecureRandomNumberGenerator().nextBytes().toHex());
  }

}