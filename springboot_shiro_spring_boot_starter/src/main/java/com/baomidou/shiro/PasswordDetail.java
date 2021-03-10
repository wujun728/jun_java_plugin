package com.baomidou.shiro;

import lombok.Data;

/**
 * 密码详细
 *
 * @author Wujun
 */
@Data
public class PasswordDetail {

  /**
   * 原始密码
   */
  private String password;
  /**
   * 盐
   */
  private String salt;
  /**
   * 加密后的密码
   */
  private String hashedPassword;

  public PasswordDetail(String password, String salt, String hashedPassword) {
    this.password = password;
    this.salt = salt;
    this.hashedPassword = hashedPassword;
  }

}
