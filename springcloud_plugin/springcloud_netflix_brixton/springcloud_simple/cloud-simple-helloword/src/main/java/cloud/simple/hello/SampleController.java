package cloud.simple.hello;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.websocket.server.PathParam;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@SpringBootApplication
public class SampleController  {
	
	volatile static int n=0;
	volatile static int m=0;
	
	Executor executor = Executors.newFixedThreadPool(10); 

    @ResponseBody
    @RequestMapping(value = "/hello/{nPath}")
    String home(@PathVariable(value = "nPath") int nPath ) {   
    	
    	final int sPath=nPath;
    	
    	Runnable task = new Runnable() {  
    	    @Override  
    	    public void run() {  
    	    	System.out.println("hello当前线程："+Thread.currentThread().getName());
    	    	System.out.println("hello请求数量："+(sPath));
    	    	try {
    				Thread.sleep(500);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    	    	System.out.println("hello处理完成："+(sPath));
    	    }  
    	};  
    	executor.execute(task); 
    	
    	
        return "Hello World!"+n;
    }
    
    @ResponseBody
    @RequestMapping(value = "/user")
    String user() {    
    	
    	System.out.println("user当前线程："+Thread.currentThread().getName());
    	System.out.println("user请求数量："+(++m));
    	
    	System.out.println("user处理完成："+(m));
        return "Hello User!"+m;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }

}
