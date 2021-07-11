package modisefileupload.java.test;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import junit.framework.Assert;
import modisefileupload.java.config.WebConfig;
import modisefileupload.java.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class FileUploadTest {
	
	@Autowired
	private UserService userService;

	@Test
	public void testSavingUser() {
		//Assert
		
		fail("Not yet implemented");
	}

}
