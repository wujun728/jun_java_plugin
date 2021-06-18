package com.jd.ssh.sshdemo.search;

/**
 * 索引更新操作
 * @author Wujun
 */
public interface IndexTask {

	public byte OPT_ADD 	= 0x01;	//添加索引
	public byte OPT_UPDATE 	= 0x02;	//更新索引
	public byte OPT_DELETE 	= 0x04;	//删除索引
	
	/**
	 * 返回更新操作类型
	 * @return 请看上面三个常量
	 */
	public byte getOpt();
	
	/**
	 * 返回对应的可搜索对象
	 * @return
	 */
	public Searchable object();
	
	/**
	 * 当该索引更新操作完毕后执行此方法
	 */
	public void afterBuild();
	
}
