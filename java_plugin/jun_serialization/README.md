# 序列化和反序列化实现

* [JDK方式](https://github.com/lgjlife/serialization/blob/master/src%2Fmain%2Fjava%2Fcom%2Futils%2Fserialization%2FJdkSerializeUtil.java)
* [Fastjson方式](https://github.com/lgjlife/serialization/blob/master/src%2Fmain%2Fjava%2Fcom%2Futils%2Fserialization%2FFastjsonSerializeUtil.java)
```XML
<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.56</version>
</dependency>
```
* [Protostuff方式](https://github.com/lgjlife/serialization/blob/master/src%2Fmain%2Fjava%2Fcom%2Futils%2Fserialization%2FProtostuffSerializeUtil.java)
```XML
<!-- https://mvnrepository.com/artifact/io.protostuff/protostuff-core -->
<dependency>
    <groupId>io.protostuff</groupId>
    <artifactId>protostuff-core</artifactId>
    <version>1.6.0</version>
    <scope>test</scope>
</dependency>



<!-- https://mvnrepository.com/artifact/io.protostuff/protostuff-runtime -->
<dependency>
    <groupId>io.protostuff</groupId>
    <artifactId>protostuff-runtime</artifactId>
    <version>1.6.0</version>
</dependency>
```
* [Hessian方式](https://github.com/lgjlife/serialization/blob/master/src%2Fmain%2Fjava%2Fcom%2Futils%2Fserialization%2HessianSerializeUtil.java)
```XML
<!-- https://mvnrepository.com/artifact/com.caucho/hessian -->
<dependency>
    <groupId>com.caucho</groupId>
    <artifactId>hessian</artifactId>
    <version>4.0.60</version>
</dependency>
```
# 使用事例

* 定义pojo
如果使用JDK方式，还需要实现Serializable接口
```java

@Data
@Builder
public class User implements Serializable {

    private  String  name;
    private  int age;
}

```

```java
User user = User.builder().name("用户"+i).age(i).build();
private AbstractSerialize  serialize = new ProtostuffSerializeUtil();
byte[] body = serialize.serialize(msg);
User user1 = serialize.deserialize(body,User.class);



```
## 测试
[测试类](https://github.com/lgjlife/serialization/blob/master/src%2Fmain%2Fjava%2Fcom%2Futils%2Ftest%2FSerializeTest.java)

综合来看,hessian 的效率是比较高的。fastjson效率相对偏低。
```java
序列化对象类：java.util.ArrayList  序列化类：com.utils.serialization.JdkSerializeUtil  序列化花费时间：369 字节长度 =  788948
序列化对象类：java.util.ArrayList  序列化类：com.utils.serialization.FastjsonSerializeUtil  序列化花费时间：417 字节长度 =  788891
序列化对象类：java.util.ArrayList  序列化类：com.utils.serialization.HessianSerializeUtil  序列化花费时间：242 字节长度 =  788897

序列化对象类：java.util.HashMap  序列化类：com.utils.serialization.JdkSerializeUtil  序列化花费时间：284 字节长度 =  1577862
序列化对象类：java.util.HashMap  序列化类：com.utils.serialization.FastjsonSerializeUtil  序列化花费时间：393 字节长度 =  1577781
序列化对象类：java.util.HashMap  序列化类：com.utils.serialization.HessianSerializeUtil  序列化花费时间：184 字节长度 =  1577785

序列化对象类：com.utils.pojo.TestPojo  序列化类：com.utils.serialization.JdkSerializeUtil  序列化花费时间：21 字节长度 =  977
序列化对象类：com.utils.pojo.TestPojo  序列化类：com.utils.serialization.FastjsonSerializeUtil  序列化花费时间：58 字节长度 =  1192
序列化对象类：com.utils.pojo.TestPojo  序列化类：com.utils.serialization.HessianSerializeUtil  序列化花费时间：4 字节长度 =  1319
序列化对象类：com.utils.pojo.TestPojo  序列化类：com.utils.serialization.ProtostuffSerializeUtil  序列化花费时间：67 字节长度 =  825

```