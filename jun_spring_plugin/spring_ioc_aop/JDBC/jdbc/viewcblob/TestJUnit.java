package jdbc.viewcblob;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJUnit {
   
	@Test
		public void testCreate(){
			String[] files=new String[]{"spring-jdbc.xml"};
			ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(files);
			ViewDao dao=(ViewDao) context.getBean("viewDao");
			dao.createViewTable();
			context.close();
		}
		//@Test
		public void testCBlobStraight() throws Exception{
			String[] files=new String[]{"spring-jdbc.xml"};
			ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(files);
			ViewDao dao=(ViewDao) context.getBean("viewDao");
			dao.insertCBlob();
			context.close();
		}
		//@Test
		public void testCBlob()throws Exception{
			String[] files=new String[]{"spring-jdbc.xml"};
			ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(files);
			ViewDao dao=(ViewDao) context.getBean("viewDao");	
			View vs=new View(56,
					         "likeCloblikeClob",
					         new File("d:"+File.separator+"MaxJ.jpg"));
			dao.insertViewCBlob(vs);
			context.close();
		}
//		@Test
		public void testRead() throws IOException{
			String[] files=new String[]{"spring-jdbc.xml"};
			ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(files);
			ViewDao dao=(ViewDao) context.getBean("viewDao");
			dao.getImgFile(56);
			context.close();
		}
}
