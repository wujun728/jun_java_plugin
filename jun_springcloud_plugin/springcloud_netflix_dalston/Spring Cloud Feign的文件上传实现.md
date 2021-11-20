# Spring Cloud Feign的文件上传实现

**原创**

 [2018-03-21](https://blog.didispace.com/spring-cloud-starter-dalston-2-4/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

在Spring Cloud封装的Feign中并不直接支持传文件，但可以通过引入Feign的扩展包来实现，本来就来具体说说如何实现。

### 服务提供方（接收文件）

服务提供方的实现比较简单，就按Spring MVC的正常实现方式即可，比如：

```
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    @RestController
    public class UploadController {

        @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public String handleFileUpload(@RequestPart(value = "file") MultipartFile file) {
            return file.getName();
        }

    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}
```

### 服务消费方（发送文件）

在服务消费方由于会使用Feign客户端，所以在这里需要在引入feign对表单提交的依赖，具体如下：

```
<dependency>
   <groupId>io.github.openfeign.form</groupId>
   <artifactId>feign-form</artifactId>
   <version>3.0.3</version>
</dependency>
<dependency>
   <groupId>io.github.openfeign.form</groupId>
   <artifactId>feign-form-spring</artifactId>
   <version>3.0.3</version>
</dependency>
<dependency>
   <groupId>commons-fileupload</groupId>
   <artifactId>commons-fileupload</artifactId>
   <version>1.3.3</version>
</dependency>
```

定义文件上传方的应用主类和FeignClient，假设服务提供方的服务名为`eureka-feign-upload-server`

```
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}

@FeignClient(value = "upload-server", configuration = UploadService.MultipartSupportConfig.class)
public interface UploadService {
 
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String handleFileUpload(@RequestPart(value = "file") MultipartFile file);
 
    @Configuration
    class MultipartSupportConfig {
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }
 
}
```

在启动了服务提供方之后，尝试在服务消费方编写测试用例来通过上面定义的Feign客户端来传文件，比如：

```
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UploadTester {

    @Autowired
    private UploadService uploadService;

    @Test
    @SneakyThrows
    public void testHandleFileUpload() {

        File file = new File("upload.txt");
        DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("file",
                MediaType.TEXT_PLAIN_VALUE, true, file.getName());

        try (InputStream input = new FileInputStream(file); OutputStream os = fileItem.getOutputStream()) {
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        MultipartFile multi = new CommonsMultipartFile(fileItem);

        log.info(uploadService.handleFileUpload(multi));
    }

}
```

### 完整示例：

读者可以根据喜好选择下面的两个仓库中查看`eureka-feign-upload-server`和`eureka-feign-upload-client`两个项目：

- [Github：https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/2-Dalston版教程示例/)
- [Gitee：https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/2-Dalston版教程示例)