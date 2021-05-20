package com.baomidou.shiro.model;

import lombok.Getter;

/**
 * shiro内置算法枚举
 *
 * @author Wujun
 */
public enum Algorithm {
  /**
   * MD5
   */
  MD5("MD5"),
  /**
   * SHA_1
   */
  SHA_1("SHA-1"),
  /**
   * SHA_256
   */
  SHA_256("SHA-256"),
  /**
   * SHA_384
   */
  SHA_384("SHA-384"),
  /**
   * SHA_512
   */
  SHA_512("SHA-512");

  @Getter
  private String name;

  Algorithm(String name) {
    this.name = name;
  }

}
