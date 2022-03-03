package test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springrain.cms.entity.CmsChannel;
import org.springrain.cms.entity.CmsContent;
import org.springrain.cms.entity.CmsSite;
import org.springrain.cms.service.ICmsChannelService;
import org.springrain.cms.service.ICmsContentService;
import org.springrain.cms.service.ICmsSiteService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SiteTest {
	@Resource
	private ICmsSiteService cmsSiteService;
	@Resource
	private ICmsChannelService cmsChannelService;
	
	
	@Resource
	private ICmsContentService cmsContentService;
	
	//@Test
	public void addSite() throws Exception{
		CmsSite cmsSite=new CmsSite();
		cmsSite.setName("测试网站");
		cmsSite.setLogo("");
		cmsSite.setLookcount(1);
		cmsSite.setPhone("138");
		cmsSite.setQq("33333");
		cmsSite.setUserId("admin");
		cmsSite.setSiteType(0);
		cmsSite.setActive(1);
		cmsSite.setFooter("footer");
		cmsSite.setContacts("contacts");
		cmsSite.setDescription("description");
		cmsSite.setDomainurl("http://www.baidu.com");
		cmsSite.setTitle("title");
		cmsSiteService.saveCmsSite(cmsSite);
	}

	/**
	 * 添加栏目
	 * @param siteId
	 * @throws Exception
	 */
	
	//@Test
	public void addChannel() throws Exception{
		
		String siteId="s_11";
		
		CmsChannel cmsChannel=new CmsChannel();
		cmsChannel.setSiteId(siteId);
		cmsChannel.setName("测试栏目");
		cmsChannel.setKeywords("keywords");
		//cmsChannel.setComcode(value);
		cmsChannel.setPid(null);
		cmsChannel.setLookcount(0);
		cmsChannel.setPositionLevel(0);
		cmsChannel.setSortno(1);
		cmsChannel.setActive(1);
		
		cmsChannelService.saveChannel(cmsChannel);
		
	}
	
	/**
	 * 添加内容
	 * @throws Exception
	 */
	
	@Test
	public void addContent() throws Exception{
		
		String siteId="s_11";
		String channelId="h_101";
		
		CmsContent cmsContent=new CmsContent();
		cmsContent.setSiteId(siteId);
		cmsContent.setChannelId(channelId);
		cmsContent.setContent("测试内容的内容Content");
		cmsContent.setKeywords("keywords");
		//cmsChannel.setComcode(value);
		cmsContent.setLookcount(0);
		cmsContent.setSortno(1);
		cmsContent.setActive(1);
		cmsContent.setTitle("title");
		cmsContent.setCreateDate(new Date());
		cmsContent.setCreatePerson("admin");
		
		cmsContentService.saveContent(cmsContent);
		
	}
	
	
	
	
	
	
}
