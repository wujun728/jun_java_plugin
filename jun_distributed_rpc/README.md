# fastrpc
基于java AIO实现的RPC调用框架

###  RPC服务端初始化
```Java
public static void main(String[] args) throws Exception {
        new FastRpcServer()
                .threadSize(20)
                .register("test", new TestService())
                .bind(4567)
                .start();
    }
```
```Java
public class TestService implements ITestService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String say(String what) {
        String result = "say " + what;
        log.debug(result);
        return result;
    }

    @Override
    public String name() {
        log.debug("call name");
        return "call name";
    }

    @Override
    public void ok(String ok) {
        log.debug("call ok");
        log.debug("param:{}", ok);
    }

    @Override
    public void none() {
        log.debug("call none");
    }

    @Override
    public User doUser(User user) {
        log.debug("收到user:" + user);
        user.setAge(user.getAge() - 1);
        user.setName("hello " + user.getName());
        user.setSex(!user.isSex());
        return user;
    }
}
```
### RPC客户端初始化
```Java
public static void main(String[] args) {
        try(IClient client = new FastRpcClient()) {
            client.connect(new InetSocketAddress("127.0.0.1", 4567));
            ITestService service = client.getService("test", ITestService.class);
            String say = service.say("Hello!");
            System.out.println(say);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
```Java
public interface ITestService {

    String say(String what);

    String name();

    void ok(String ok);

    void none();

    User doUser(User user);
}
```

Maven 项目引入
==========
```xml
<dependency>
    <groupId>com.github.sd4324530</groupId>
    <artifactId>fastrpc-server</artifactId>
    <version>0.1</version>
</dependency>

<dependency>
    <groupId>com.github.sd4324530</groupId>
    <artifactId>fastrpc-client</artifactId>
    <version>0.1</version>
</dependency>
```