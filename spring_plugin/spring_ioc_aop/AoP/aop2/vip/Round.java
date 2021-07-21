package aop2.vip;

import org.aspectj.lang.ProceedingJoinPoint;
//import org.springframework.util.StopWatch;

public class Round {
 
    public Object roundOut( ProceedingJoinPoint call, 
    		                String  str, Integer num) throws Throwable {
//            StopWatch clock = new StopWatch(str+num);
//            try {
//                clock.start(call.toShortString());
//                return call.proceed();
//            } finally {
//                clock.stop();
//                System.out.println(clock.prettyPrint());
//            }
    	     
    	    Object result=call.proceed();
    	    return result;
        }
    
}
