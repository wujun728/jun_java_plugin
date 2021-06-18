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


import com.baomidou.kaptcha.GoogleKaptcha;
import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.spring.boot.KaptchaProperties.BackgroundColor;
import com.baomidou.kaptcha.spring.boot.KaptchaProperties.Border;
import com.baomidou.kaptcha.spring.boot.KaptchaProperties.Content;
import com.baomidou.kaptcha.spring.boot.KaptchaProperties.Font;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import java.util.Properties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 谷歌验证码 的springBoot快速启动器
 *
 * @author Wujun
 */
@Configuration
@ConditionalOnClass(DefaultKaptcha.class)
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaAutoConfiguration {

  private final KaptchaProperties properties;

  public KaptchaAutoConfiguration(KaptchaProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public DefaultKaptcha defaultKaptcha() {
    Properties prop = new Properties();

    prop.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, String.valueOf(properties.getWidth()));
    prop.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, String.valueOf(properties.getHeight()));

    Content content = properties.getContent();
    prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, content.getSource());
    prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, String.valueOf(content.getLength()));
    prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, String.valueOf(content.getSpace()));

    BackgroundColor backgroundColor = properties.getBackgroundColor();
    prop.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_FROM, backgroundColor.getFrom());
    prop.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_TO, backgroundColor.getTo());

    Border border = properties.getBorder();
    prop.setProperty(Constants.KAPTCHA_BORDER, border.getEnabled() ? "yes" : "no");
    prop.setProperty(Constants.KAPTCHA_BORDER_COLOR, border.getColor());
    prop.setProperty(Constants.KAPTCHA_BORDER_THICKNESS, String.valueOf(border.getThickness()));

    Font font = properties.getFont();
    prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, font.getName());
    prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, String.valueOf(font.getSize()));
    prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, font.getColor());

    DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
    defaultKaptcha.setConfig(new Config(prop));
    return defaultKaptcha;
  }

  @Bean
  @ConditionalOnMissingBean
  public Kaptcha kaptchaRender(DefaultKaptcha defaultKaptcha) {
    return new GoogleKaptcha(defaultKaptcha);
  }

}
