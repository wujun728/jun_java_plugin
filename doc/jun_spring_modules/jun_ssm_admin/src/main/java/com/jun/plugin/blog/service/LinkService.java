package com.jun.plugin.blog.service;

import java.util.List;
import java.util.Map;

import com.jun.plugin.blog.entity.Link;

/**
 * ��������Service�ӿ�
 * @author Wujun
 *
 */
public interface LinkService {

	/**
	 * �����������
	 * @param link
	 * @return
	 */
	public int add(Link link);
	
	/**
	 * �޸���������
	 * @param link
	 * @return
	 */
	public int update(Link link);
	
	/**
	 * ��������������Ϣ
	 * @param map
	 * @return
	 */
	public List<Link> list(Map<String,Object> map);	
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * ɾ����������
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
}
