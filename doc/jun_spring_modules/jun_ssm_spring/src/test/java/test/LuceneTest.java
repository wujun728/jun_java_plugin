package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.junit.Test;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.LuceneFinder;
import org.springrain.frame.util.LuceneUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.SecUtils;
import org.springrain.system.entity.User;

import test.dto.LuceneDto;

public class LuceneTest {
	
	@Test
	public void  testSave() throws Exception{
		String rootdir=GlobalStatic.rootDir+"/lucene/index";
		
        
        //LuceneUtils.addDictWord("暴龙");
        
		Long d1=System.currentTimeMillis();
		LuceneUtils.deleteAllDocument(rootdir, LuceneDto.class);
		
		
		System.out.println("删除耗时毫秒:"+(System.currentTimeMillis()-d1));
		
		File f=new File(rootdir);
		if(!f.exists()){
			f.mkdirs();
		}
		
		List<LuceneDto> list=new ArrayList<>();
		
		for (int i = 0; i < 50; i++) {
		    LuceneDto u=new LuceneDto();
			u.setId(SecUtils.getUUID());
			u.setName("我是中国人，我会说中文"+i);
			u.setInt1(u.getInt1()+i);
			//u.setInt2(u.getInt2()+i);
			list.add(u);
		    //LuceneUtils.saveDocument(rootdir, u);
		}
		
		
		 LuceneDto u=new LuceneDto();
         u.setId(SecUtils.getUUID());
         u.setName("暴龙眼镜"+60);
         u.setInt1(u.getInt1()+60);
         //u.setInt2(u.getInt2()+60);
         list.add(u);
         
         LuceneDto u2=new LuceneDto();
         u2.setId(SecUtils.getUUID());
         u2.setName("卫龙辣条"+61);
         u2.setInt1(u2.getInt1()+61);
        // u2.setInt2(u2.getInt2()+61);
         list.add(u2);
         
         Long d2=System.currentTimeMillis();
         LuceneUtils.saveListDocument(rootdir, list);
         System.out.println("保存"+list.size()+"耗时毫秒:"+(System.currentTimeMillis()-d2));
		
	
	}
	
	
	//@Test
	public void  testSaveList() throws Exception{
		String rootdir=GlobalStatic.rootDir+"/lucene/index";
		File f=new File(rootdir);
		if(!f.exists()){
			f.mkdirs();
		}
		List<User> list=new ArrayList<User>();
		for (int i = 0; i < 50; i++) {
			User u=new User();
			u.setId(i+"起来"+i);
			u.setName(i+"我是中国人"+i);
			list.add(u);
		}
		LuceneUtils.saveListDocument(rootdir,list);
	}
	
	
	
	@SuppressWarnings("unchecked")
	//@Test
	public void testSearch() throws Exception{
		String rootdir=GlobalStatic.rootDir+"/lucene/index";
		File f=new File(rootdir);
		if(!f.exists()){
			f.mkdirs();
		}
		Page page=new Page(3);
		page.setPageSize(5);
		List<LuceneDto> list = LuceneUtils.searchDocument(rootdir,LuceneDto.class, page,"龙");
		//List<LuceneDto> list = LuceneUtils.searchDocumentByTerm(rootdir,LuceneDto.class, page,"name", "我是中国人，我会说中文49");
		
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		for (LuceneDto u:list) {
			System.out.println(u.getId()+","+u.getName()+","+u.getD1()+","+u.getF1()+","+u.getInt1()+","+u.getD2()+","+u.getF2()+","+u.getInt2()+","+u.getDate());
			
		}
	}
	
	
	   @SuppressWarnings("unchecked")
	  // @Test
	    public void testSearchObject() throws Exception{
	        String rootdir=GlobalStatic.rootDir+"/lucene/index";
	        File f=new File(rootdir);
	        if(!f.exists()){
	            f.mkdirs();
	        }
	       
	        //List<LuceneDto> list = LuceneUtils.searchDocument(rootdir,LuceneDto.class, page,"name","我是中国人，我会说中文");
	        LuceneDto u = LuceneUtils.searchDocumentById(rootdir,LuceneDto.class, "ec28022ecc724a1b811f0176f1d57471");
	    
	            System.out.println(u.getId()+","+u.getName()+","+u.getD1()+","+u.getF1()+","+u.getInt1()+","+u.getD2()+","+u.getF2()+","+u.getInt2()+","+u.getDate());
	           
	            
	            u.setName("我是测试修改");
	            
	            LuceneUtils.updateDocument(rootdir, u);
	            
	            u = LuceneUtils.searchDocumentById(rootdir,LuceneDto.class, "ec28022ecc724a1b811f0176f1d57471");
	            
                System.out.println(u.getId()+","+u.getName()+","+u.getD1()+","+u.getF1()+","+u.getInt1()+","+u.getD2()+","+u.getF2()+","+u.getInt2()+","+u.getDate());
               
	            
	        
	    }
	   
	   
	 
	   
	   
	   
      // @Test
       public void testStringClause() throws Exception{
           String rootdir=GlobalStatic.rootDir+"/lucene/index";
           
        
           

           LuceneFinder lsc=new LuceneFinder("暴龙");
           
           //lsc.addWhereCondition("id", String.class, "6fd428265bb840b7b886b926cb45659a");
           
           //lsc.addWhereCondition("int2", Integer.class, 20,100);
           //lsc.addWhereCondition("int1", Integer.class, 10,15);
           //lsc.addWhereCondition("id", String.class, "121b77b104dd4369887a748368cafda7");
           
           //lsc.addSortField("int1", Integer.class, true);
        
           List<LuceneDto> list = LuceneUtils.searchDocument(rootdir,LuceneDto.class,null,lsc);
          
           
           for (LuceneDto u:list) {
                System.out.println(u.getId()+","+u.getName()+","+u.getD1()+","+u.getF1()+","+u.getInt1()+","+u.getD2()+","+u.getF2()+","+u.getInt2()+","+u.getDate());
                
            }
           
           
       }
       
       
       @Test
       public void testSelectAll() throws Exception{
           String rootdir=GlobalStatic.rootDir+"/lucene/index";
           
        
           

           LuceneFinder lsc=new LuceneFinder("中国");
           
           Long d3=System.currentTimeMillis();
           List<LuceneDto> list = LuceneUtils.searchDocument(rootdir,LuceneDto.class,null,lsc);
           System.out.println("查询"+list.size()+"耗时:"+(System.currentTimeMillis()-d3));
           
           for (LuceneDto u:list) {
                System.out.println(u.getId()+","+u.getName()+","+u.getD1()+","+u.getF1()+","+u.getInt1()+","+u.getD2()+","+u.getF2()+","+u.getInt2()+","+u.getDate());
                
            }
           
           
       }
       
    
	   
	   
	
	
	
	
	
}
