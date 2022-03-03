package com.jun.plugin.blog.dao;

import java.util.List;
import java.util.Map;

import com.jun.plugin.blog.entity.Comment;

/**
 * ����Dao�ӿ�
 * @author Wujun
 *
 */
public interface CommentDao {

	/**
	 * �������
	 * @param comment
	 * @return
	 */
	public int add(Comment comment);
	
	/**
	 * �޸�����
	 * @param comment
	 * @return
	 */
	public int update(Comment comment);
	
	/**
	 * �����û�������Ϣ
	 * @param map
	 * @return
	 */
	public List<Comment> list(Map<String,Object> map);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * ɾ��������Ϣ
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	
}
