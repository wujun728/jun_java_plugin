/**
 * Copyright © 2018 TaoYu (tracy5546@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baomidou.kaptcha.spring.boot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 验证码组件参数
 *
 * @author Wujun
 */
@ConfigurationProperties(prefix = "kaptcha")
@Data
public class KaptchaProperties {

  /**
   * 宽度
   */
  private Integer width = 200;
  /**
   * 高度
   */
  private Integer height = 50;
  /**
   * 内容
   */
  @NestedConfigurationProperty
  private Content content = new Content();
  /**
   * 背景色
   */
  @NestedConfigurationProperty
  private BackgroundColor backgroundColor = new BackgroundColor();
  /**
   * 字体
   */
  @NestedConfigurationProperty
  private Font font = new Font();
  /**
   * 边框
   */
  @NestedConfigurationProperty
  private Border border = new Border();

  @Data
  static class BackgroundColor {

    /**
     * 开始渐变色
     */
    private String from = "lightGray";
    /**
     * 结束渐变色
     */
    private String to = "white";

  }

  @Data
  static class Content {

    /**
     * 内容源
     */
    private String source = "abcdefghjklmnopqrstuvwxyz23456789";
    /**
     * 内容长度
     */
    private Integer length = 4;
    /**
     * 内容间隔
     */
    private Integer space = 2;

  }

  @Data
  static class Border {

    /**
     * 是否开启
     */
    private Boolean enabled = true;
    /**
     * 颜色
     */
    private String color = "black";
    /**
     * 厚度
     */
    private Integer thickness = 1;

  }

  @Data
  static class Font {

    /**
     * 名称
     */
    private String name = "Arial";
    /**
     * 颜色
     */
    private String color = "black";
    /**
     * 大小
     */
    private Integer size = 40;

  }

}
