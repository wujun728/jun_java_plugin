package com.baomidou.shiro.model;

import lombok.Data;

/**
 * 密码匹配器配置参数
 *
 * @author Wujun
 */
@Data
public class CredentialsMatcherDefinition {

  /**
   * 加密算法,默认SHA_1
   */
  private Algorithm algorithm = Algorithm.SHA_1;
  /**
   * 迭代次数,默认2次
   */
  private int iterations = 2;
  /**
   * 是否转换为hex,否则转为base64,默认hex
   */
  private boolean hexEncoded = true;

}