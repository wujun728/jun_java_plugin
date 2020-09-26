/**
 * 
 */
package com.jun.plugin.lucene.search.test;

import java.io.IOException;

import com.jun.plugin.lucene.search.IndexHolder;

/**
 * 测试索引过程
 * @author Winter Lau
 */
public class LuceneTester {

	/**
	 * 测试添加索引
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		IndexHolder holder = IndexHolder.init("D:\\TEST");
		for(int i=0;i<10;i++)
			holder.optimize(Post.class);
		System.exit(0);
	}

}
