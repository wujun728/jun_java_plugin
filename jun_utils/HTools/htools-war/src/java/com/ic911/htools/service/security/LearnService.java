package com.ic911.htools.service.security;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ic911.htools.entity.security.Learn;
import com.ic911.htools.persistence.security.LearnDao;
/**
 * 知识库读取
 * @author caoyang
 */
@Service
@Transactional
public class LearnService {
	@Resource
	private LearnDao learnDao;
	private static int countor = 10;//点击十次就重新读数据库;
	private static long count = 0;
	
	public void save(String... contexts){
		learnDao.deleteAll();
		int i = 1;
		for(String context : contexts){
			if(context.trim().isEmpty()){
				continue;
			}
			Learn learn = new Learn();
			learn.setContext(context.trim());
			learn.setNum(i++);
			learnDao.save(learn);
		}
	}
	
	/**
	 * 随机获取题库知识最多10个
	 * @return
	 */
	public Map<String,String> findRandom(){
		if(countor++>=10||count<1){//如果计数器累计10次或总和没有值就读数据库
			countor = 0;
			count = learnDao.count();
		}
		if(count<1){//如果没数据就退出
			return null;
		}
		long loop = 10;
		if(count<10){
			loop = count;
		}
		Random rdm = new Random();
		Set<Integer> nums = Sets.newHashSet();
		for(int i =0 ;i<loop ;i++){
			int rand = rdm.nextInt((int)count)+1;
			nums.add(rand);
		}
		Collection<Learn> learns = learnDao.findByNumIn(nums);
		Map<String,String> values = Maps.newLinkedHashMap();
		int count = 1;
		for(Learn learn : learns){
			values.put(""+(count++), learn.getContext());
		}
		learns.clear();
		return values;
	}
	
}
