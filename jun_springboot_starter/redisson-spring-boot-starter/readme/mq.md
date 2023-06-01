
### 生产者

```java
import com.zengtengpeng.annotation.MQPublish;
import com.zengtengpeng.test.bean.User;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class MQController {

    @Resource
    private RedissonClient redissonClient;


    /**
     * 发送方式一: 使用代码发送消息
     */
    @RequestMapping("testMq")
    @ResponseBody
    public void testMq(){
        RTopic testMq = redissonClient.getTopic("testMq");
        User message = new User();
        message.setAge("12");
        message.setName("的身份为");
        testMq.publish(message);
    }

    /**
     * 发送方式二:使用注解发送消息, 返回值就是需要发送的消息
     * @return
     */
    @RequestMapping("testMq1")
    @ResponseBody
    @MQPublish(name = "test")
    public User testMq1(){
        User user=new User();
        user.setName("garegarg");
        user.setAge("123");
        return user;
    }
    
}
```

### 消费者
```java
package com.zengtengpeng.test.listener;

import com.zengtengpeng.annotation.MQListener;
import com.zengtengpeng.enums.MQModel;
import com.zengtengpeng.test.bean.User;
import org.springframework.stereotype.Component;

//注入到spring容器中
@Component
public class MQListeners {

    /**
     * 接受消息方式一:PRECISE精准的匹配 如:name="myTopic" 那么发送者的topic name也一定要等于myTopic (如果消息类型不明确,请使用Object 接收消息)
     * @param charSequence
     * @param o
     */
    @MQListener(name = "testMq")
    public void test1(CharSequence charSequence,User o,Object object){
        System.out.println("charSequence="+charSequence);
        System.out.println("收到消息2"+o);
    }

    /**
     * 
     * 接收消息方式二: PATTERN模糊匹配 如: name="myTopic.*" 那么发送者的topic name 可以是 myTopic.name1 myTopic.name2.尾缀不限定
     * @param patten
     * @param charSequence
     * @param o
     */
    @MQListener(name = "test*",model = MQModel.PATTERN)
    public void test1(CharSequence patten,CharSequence charSequence,User o){
        System.out.println("patten="+patten);
        System.out.println("charSequence="+charSequence);
        System.out.println("test*="+o);
    }
}

```