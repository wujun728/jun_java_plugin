import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sam.demo.rabbitmq.Application;
import com.sam.demo.rabbitmq.sender.Sender;

@RunWith(value=SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitTests {
	
	@Autowired
    private Sender sender;
	
    @Test
    public void sendTest() throws Exception {
    	while(true){
    		String msg = UUID.randomUUID().toString();
    		sender.send(msg);
    		Thread.sleep(1000);
    	}
    }
}
