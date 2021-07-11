package com.luo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luo.dao.MessageDao;
import com.luo.pojo.Message;
import com.luo.util.IndexUtil;
import com.luo.vo.IndexField;

@Service
@Transactional
public class MessageService {
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private SolrService solrService;
	
	public List<Message> list() {
		return messageDao.findAll();
	}

	public void save(Message message) {
		messageDao.save(message);
		
		//添加索引
		IndexField indexField=IndexUtil.message2IndexField(message);
		solrService.addIndex(indexField);
	}

	public void delete(int id) {
		messageDao.del(id);
		
		//删除索引
		Message message=messageDao.findById(id);
		IndexField indexField=IndexUtil.message2IndexField(message);
		solrService.deleteIndex(indexField);
	}

}
