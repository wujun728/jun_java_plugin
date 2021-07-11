package kr.hwb.example;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.hwb.example.util.MediaUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {
    
    @Test
    public void contextLoads() {
        
    }
    
    @Test
    public void imageMediaTypeConditionTest() {
        
        boolean condition = MediaUtils.containsImageMediaType("image/jpeg");
        
        System.out.println(condition);
        
        assertTrue(condition);
    }
}
