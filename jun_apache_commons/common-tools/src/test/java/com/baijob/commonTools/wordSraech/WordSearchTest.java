package com.baijob.commonTools.wordSraech;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.baijob.commonTools.wordSearch.Words;

public class WordSearchTest {
	@Test
	public void wordSearchTest() {
		Words words = new Words();
		//----------------------------添加敏感词 start
		words.addWord("敏感词1");
		words.addWord("敏感词2");
		words.addWord("敏感词3");
		words.addWord("敏感词4");
		words.addWord("敏感词5");
		//----------------------------添加敏感词 end
		
		String content = "我是一句话，我包含了敏感词2，你能找到敏感词2吗？还有敏  感  词4";
		//给定文本是否包含敏感词
		Assert.assertTrue(words.contains(content));
		//返回第一个找到的敏感词
		String firstWord = words.getFindedFirstWord(content);
		//返回找到的所有敏感词
		List<String> allWords = words.getFindedAllWords(content);
		
		Assert.assertEquals(firstWord, "敏感词2");
		Assert.assertEquals(firstWord, "敏感词2");
		Assert.assertArrayEquals(new String[]{"敏感词2", "敏感词2", "敏感词4"}, allWords.toArray());
	}
}
