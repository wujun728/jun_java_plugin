/**
 * 
 */
package com.jmx;

/**
 * @author liubing
 *
 */
import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceMBean;
import com.alibaba.fastjson.JSONObject;

import org.quartz.core.QuartzSchedulerMBeanImpl;
public class JVMTest {
	/**
	 * @param args
	 */
	@SuppressWarnings("restriction")
	public static void main(String[] args) { 
        try { 
 
            String jmxURL = "service:jmx:rmi:///jndi/rmi://127.0.0.1:8999/jmxrmi";//tomcat jmx url   
            JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
            Map map = new HashMap(); 
            
            //String[] credentials = new String[] { "monitorRole", "QED" }; 
            //map.put("jmx.remote.credentials", credentials); 
            JMXConnector connector = JMXConnectorFactory.connect(serviceURL, map); 
            
            MBeanServerConnection mbsc = connector.getMBeanServerConnection(); 
            
  
         // heap
            ThreadMXBean threadMXBean=ManagementFactory.newPlatformMXBeanProxy  
            		(mbsc,ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
            System.out.println(threadMXBean.getThreadCount()+","+threadMXBean.getPeakThreadCount()+","+threadMXBean.getDaemonThreadCount()+","+threadMXBean.findDeadlockedThreads());
            //Object o=ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE; 非常核心
            
            // MBeanInfo info = mbsc.getMBeanInfo(objectDruidName);
          //  System.out.println("Hello Operation:" + info.getOperations()[0].getName());   
//            JMXDruidDataSourceMBean druidDataSource= MBeanServerInvocationHandler.newProxyInstance(mbsc, objectDruidName, JMXDruidDataSourceMBean.class, false);
//            System.out.println(druidDataSource.getUrl());
//            System.out.println(druidDataSource.getActivePeak());
//            druidDataSource.getJdbcUrl();
//            MBeanInfo beanInfo= mbsc.getMBeanInfo(objectDruidName);
//            System.out.println(JSONObject.toJSON(beanInfo.getAttributes()));
//            System.out.println(mbsc.getAttribute(objectDruidName, "ResetCount"));
//            ObjectName objectName1=new ObjectName("quartz:instance=liubingdeMacBook-Pro.local1483156390401,name=quartzScheduler,type=QuartzScheduler");
//            for(MBeanAttributeInfo beanAttributeInfo:mbsc.getMBeanInfo(objectName1).getAttributes()){
//            	System.out.println(beanAttributeInfo.getName()+"---"+beanAttributeInfo.getType());
//            }
            
            Set<?> MBeanset = mbsc.queryMBeans(null, null);
			System.out.println("MBeanset.size() : " + MBeanset.size());
			Iterator<?> MBeansetIterator = MBeanset.iterator();
			while (MBeansetIterator.hasNext()) {
				ObjectInstance objectInstance = (ObjectInstance) MBeansetIterator.next();
				ObjectName objectName = objectInstance.getObjectName();
				String canonicalName = objectName.getCanonicalName();
				System.out.println("canonicalName : " + canonicalName);
				if (canonicalName.equals("Catalina:host=localhost,type=Cluster")) {
					// Get details of cluster MBeans
					System.out.println("Cluster MBeans Details:");
					System.out.println("=========================================");
					// getMBeansDetails(canonicalName);
					String canonicalKeyPropList = objectName.getCanonicalKeyPropertyListString();
				}
			}
            
            //端口最好是动态取得   
            ObjectName threadObjName = new ObjectName("Catalina:type=ThreadPool,name=http-8080"); 
            Set<ObjectName> s3 = mbsc.queryNames(threadObjName, null); 
            for (ObjectName obj : s3) { 
                System.out.println("端口名:" + obj.getKeyProperty("name")); 
                ObjectName objname = new ObjectName(obj.getCanonicalName()); 
                System.out.println("最大线程数:" + mbsc.getAttribute(objname, "maxThreads")); 
                System.out.println("当前线程数:" + mbsc.getAttribute(objname, "currentThreadCount")); 
                System.out.println("繁忙线程数:" + mbsc.getAttribute(objname, "currentThreadsBusy")); 
                System.out.println("111:"+mbsc.getAttribute(objname, "running"));
            }             
            //------------------------- system ----------------------   
            ObjectName runtimeObjName = new ObjectName("java.lang:type=Runtime"); 
            System.out.println("厂商:" + (String) mbsc.getAttribute(runtimeObjName, "VmVendor")); 
            System.out.println("程序:" + (String) mbsc.getAttribute(runtimeObjName, "VmName")); 
            System.out.println("版本:" + (String) mbsc.getAttribute(runtimeObjName, "VmVersion")); 
            Date starttime = new Date((Long) mbsc.getAttribute(runtimeObjName, "StartTime")); 
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            System.out.println("启动时间:" + df.format(starttime)); 
 
            Long timespan = (Long) mbsc.getAttribute(runtimeObjName, "Uptime"); 
            System.out.println("连续工作时间:" + JVMTest.formatTimeSpan(timespan)); 
            //------------------------ JVM -------------------------   
            //堆使用率   
            ObjectName heapObjName = new ObjectName("java.lang:type=Memory"); 
            MemoryUsage heapMemoryUsage = MemoryUsage.from((CompositeDataSupport) mbsc.getAttribute(heapObjName, 
                    "HeapMemoryUsage")); 
            long maxMemory = heapMemoryUsage.getMax();//堆最大   
            long commitMemory = heapMemoryUsage.getCommitted();//堆当前分配   
            long usedMemory = heapMemoryUsage.getUsed(); 
            System.out.println("heap:" + (double) usedMemory * 100 / commitMemory + "%");//堆使用率   
 
            MemoryUsage nonheapMemoryUsage = MemoryUsage.from((CompositeDataSupport) mbsc.getAttribute(heapObjName, 
                    "NonHeapMemoryUsage")); 
            long noncommitMemory = nonheapMemoryUsage.getCommitted(); 
            long nonusedMemory = heapMemoryUsage.getUsed(); 
            System.out.println("nonheap:" + (double) nonusedMemory * 100 / noncommitMemory + "%"); 
 
            ObjectName permObjName = new ObjectName("java.lang:type=MemoryPool,name=PS Eden Space"); 
            MemoryUsage permGenUsage = MemoryUsage.from((CompositeDataSupport) mbsc.getAttribute(permObjName, "Usage")); 
            
            long committed = permGenUsage.getCommitted();//持久堆大小   
            long used = permGenUsage.getUsed();//  
            System.out.println(permGenUsage.getInit()+"----"+permGenUsage.getMax());
            System.out.println("perm gen:" + (double) used * 100 / committed + "%");//持久堆使用率   
 
            //-------------------- Session ---------------    
            ObjectName managerObjName = new ObjectName("Catalina:type=Manager,*"); 
            Set<ObjectName> s = mbsc.queryNames(managerObjName, null); 
            for (ObjectName obj : s) { 
                System.out.println("应用名:" + obj.getKeyProperty("path")); 
                ObjectName objname = new ObjectName(obj.getCanonicalName()); 
                System.out.println("最大会话数:" + mbsc.getAttribute(objname, "maxActiveSessions")); 
                System.out.println("会话数:" + mbsc.getAttribute(objname, "activeSessions")); 
                System.out.println("活动会话数:" + mbsc.getAttribute(objname, "sessionCounter")); 
                
                if(Integer.parseInt(String.valueOf(mbsc.getAttribute(objname, "sessionCounter")))!=0){
                    System.out.println(Double.parseDouble(String.valueOf(mbsc.getAttribute(objname, "activeSessions")))/Double.parseDouble(String.valueOf(mbsc.getAttribute(objname, "sessionCounter"))));

                }
            } 
 
            //-----------------所有 Thread Pool ----------------   
            ObjectName threadpoolObjName = new ObjectName("Catalina:type=ThreadPool,*"); 
            Set<ObjectName> s2 = mbsc.queryNames(threadpoolObjName, null); 
            for (ObjectName obj : s2) { 
                System.out.println("端口名:" + obj.getKeyProperty("name")); 
                ObjectName objname = new ObjectName(obj.getCanonicalName()); 
                System.out.println("最大线程数:" + mbsc.getAttribute(objname, "maxThreads")); 
                System.out.println("当前线程数:" + mbsc.getAttribute(objname, "currentThreadCount")); 
                System.out.println("繁忙线程数:" + mbsc.getAttribute(objname, "currentThreadsBusy")); 
                if(obj.getKeyProperty("name").contains("8080")){
                    System.out.println("11:"+mbsc.getAttribute(objname, "running"));

            	}
            } 
            //--------------------tomcat 应用------------------------------------
            ObjectName tomcatObjName = new ObjectName("Catalina:j2eeType=WebModule,J2EEApplication=none,J2EEServer=none,*");
            
            Set<ObjectName> s6 = mbsc.queryNames(tomcatObjName, null); 
            for (ObjectName obj : s6) {
            	System.out.println("tomcat:" + obj.getKeyProperty("name")); 
            }
            //---------------------quartz ---------------------------------------
            ObjectName quartzObjName = new ObjectName("quartz:type=QuartzScheduler,*");
            
            Set<ObjectName> s7 = mbsc.queryNames(quartzObjName, null); 
            for (ObjectName obj : s7) {
            	System.out.println("quartz:" + obj.getKeyProperty("name"));
            	
            	ObjectName objname = new ObjectName(obj.getCanonicalName()); 
            	System.out.println("ThreadPoolClassName:"+mbsc.getAttribute(objname, "ThreadPoolClassName"));
            	System.out.println("ThreadPoolSize:"+mbsc.getAttribute(objname, "ThreadPoolSize"));
            }
            //----------------------------------------------------------------------
            ObjectName cpuObjName = new ObjectName("java.lang:type=OperatingSystem,*");
            
            Set<ObjectName> s8 = mbsc.queryNames(cpuObjName, null); 
            for (ObjectName obj : s8) {
            	
            	ObjectName objname = new ObjectName(obj.getCanonicalName()); 
            	System.out.println("cputime:"+mbsc.getAttribute(objname, "ProcessCpuTime"));
            	System.out.println("ThreadPoolSize:"+mbsc.getAttribute(objname, "AvailableProcessors"));
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }

	public static String formatTimeSpan(long span) {
		long minseconds = span % 1000;

		span = span / 1000;
		long seconds = span % 60;

		span = span / 60;
		long mins = span % 60;

		span = span / 60;
		long hours = span % 24;

		span = span / 24;
		long days = span;
		return (new Formatter()).format("%1$d天 %2$02d:%3$02d:%4$02d.%5$03d",
				days, hours, mins, seconds, minseconds).toString();
	}
	

}
