package com.luo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luo.dao.TempIndexDao;
import com.luo.util.LuceneContext;
import com.luo.util.PropertiesUtil;
import com.luo.vo.IndexModel;

@Service
public class LuceneService {
	@Autowired
	private TempIndexDao tempIndexDao;
	
	
	public List<IndexModel> search(String keyword) {
		String indexDir = PropertiesUtil.get("luceneIndex");
		List<IndexModel> list=new ArrayList<IndexModel>();
		try {
			//1、创建索引目录
			Directory directory=FSDirectory.open(new File(indexDir));
			
			IndexSearcher indexSearcher=new IndexSearcher(directory);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	public void createIndex() {
//		LuceneContext.getContext()
	}
	
	
	public void commitRamIndex() {
		tempIndexDao.deleteAll();
		LuceneContext.getContext().commitIndex();
	}
	public void commitDBIndex() {
		// TODO Auto-generated method stub
		
	}
	public void updateReconstructorIndex() {
		// TODO Auto-generated method stub
		
	}
	public void deleteIndex() {
		// TODO Auto-generated method stub
		
	}

}
