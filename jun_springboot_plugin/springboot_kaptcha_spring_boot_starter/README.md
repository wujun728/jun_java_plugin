<p align="center">
  <img src="https://s1.ax1x.com/2018/08/01/PwJZp6.png" border="0" >
</p>

<p align="center">
	<strong>简单快速集成 Google Kaptcha验证码</strong>
</p>

<p align="center">
    <a href="http://mvnrepository.com/artifact/com.baomidou/kaptcha-spring-boot-starter" target="_blank">
        <img src="https://maven-badges.herokuapp.com/maven-central/com.baomidou/kaptcha-spring-boot-starter/badge.svg" >
    </a>
    <a href="http://www.apache.org/licenses/LICENSE-2.0.html" target="_blank">
        <img src="http://img.shields.io/:license-apache-brightgreen.svg" >
    </a>
    <a>
        <img src="https://img.shields.io/badge/JDK-1.7+-green.svg" >
    </a>
    <a>
        <img src="https://img.shields.io/badge/springBoot-1.4+_1.5+_2.0+-green.svg" >
    </a>
</p>
<p align="center">
	QQ群:<a href="https://jq.qq.com/?_wv=1027&k=5tFhLhS" target="_blank">710314529</a>
</p>


## 如何使用

1. 引入 kaptcha-datasource-spring-boot-starter。

```xml
<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>kaptcha-spring-boot-starter</artifactId>
  <version>${version}</version>
</dependency>
```

2. 在Controller使用`Kaptcha`。

```java
@RestController
@RequestMapping("/kaptcha")
public class KaptchaController {

  @Autowired
  private Kaptcha kaptcha;

  @GetMapping("/render")
  public void render() {
    kaptcha.render();
  }

  @PostMapping("/valid")
  public void validDefaultTime(@RequestParam String code) {
    //default timeout 900 seconds
    kaptcha.validate(code);
  }

  @PostMapping("/validTime")
  public void validCustomTime(@RequestParam String code) {
    kaptcha.validate(code, 60);
  }

}
```

3. 发生错误会抛出异常，建议使用全局异常来处理。

```java
KaptchaException  //super Exception

KaptchaIncorrectException

KaptchaNotFoundException

KaptchaTimeoutException

KaptchaRenderException //If something is wrong then Image.write when render.
```

```java
import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = KaptchaException.class)
  public String kaptchaExceptionHandler(KaptchaException kaptchaException) {
    if (kaptchaException instanceof KaptchaIncorrectException) {
      return "验证码不正确";
    } else if (kaptchaException instanceof KaptchaNotFoundException) {
      return "验证码未找到";
    } else if (kaptchaException instanceof KaptchaTimeoutException) {
      return "验证码过期";
    } else {
      return "验证码渲染失败";
    }

  }

}
```

4. 自定义验证码参数,以下为默认配置。

```yaml
kaptcha:
  height: 50
  width: 200
  content:
    length: 4
    source: abcdefghjklmnopqrstuvwxyz23456789
    space: 2
  font:
    color: black
    name: Arial
    size: 40
  background-color:
    from: lightGray
    to: white
  border:
    enabled: true
    color: black
    thickness: 1
```
