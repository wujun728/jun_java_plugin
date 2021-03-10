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
package com.baomidou.kaptcha;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_DATE;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaRenderException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

/**
 * 谷歌默认验证码组件
 *
 * @author Wujun
 */
@Slf4j
public class GoogleKaptcha implements Kaptcha {

  private DefaultKaptcha kaptcha;

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private HttpServletResponse response;

  public GoogleKaptcha(DefaultKaptcha kaptcha) {
    this.kaptcha = kaptcha;
  }

  @Override
  public String render() {
    response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate");
    response.addHeader(HttpHeaders.CACHE_CONTROL, "post-check=0, pre-check=0");
    response.setHeader(HttpHeaders.PRAGMA, "no-cache");
    response.setContentType("image/jpeg");
    String sessionCode = kaptcha.createText();
    try (ServletOutputStream out = response.getOutputStream()) {
      request.getSession().setAttribute(KAPTCHA_SESSION_KEY, sessionCode);
      request.getSession().setAttribute(KAPTCHA_SESSION_DATE, System.currentTimeMillis());
      ImageIO.write(kaptcha.createImage(sessionCode), "jpg", out);
      return sessionCode;
    } catch (IOException e) {
      throw new KaptchaRenderException(e);
    }
  }

  @Override
  public boolean validate(String code) {
    return validate(code, 900);
  }

  @Override
  public boolean validate(@NonNull String code, long second) {
    HttpSession httpSession = request.getSession(false);
    String sessionCode;
    if (httpSession != null && (sessionCode = (String) httpSession.getAttribute(KAPTCHA_SESSION_KEY)) != null) {
      if (sessionCode.equalsIgnoreCase(code)) {
        long sessionTime = (long) httpSession.getAttribute(KAPTCHA_SESSION_DATE);
        long duration = (System.currentTimeMillis() - sessionTime) / 1000;
        if (duration < second) {
          httpSession.removeAttribute(KAPTCHA_SESSION_KEY);
          httpSession.removeAttribute(KAPTCHA_SESSION_DATE);
          return true;
        } else {
          throw new KaptchaTimeoutException();
        }
      } else {
        throw new KaptchaIncorrectException();
      }
    } else {
      throw new KaptchaNotFoundException();
    }
  }

}

