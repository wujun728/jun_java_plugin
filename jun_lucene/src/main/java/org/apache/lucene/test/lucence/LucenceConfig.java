package org.apache.lucene.test.lucence;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * lucence 配置项
 * @author DF
 *
 */
public class LucenceConfig {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(LucenceConfig.class);
	//索引目录
	private static Directory directory;
	//分词器 
	private static Analyzer analyzer;
	// 写索引的配置
	private  static IndexWriterConfig indexWriterConfig; 
	//索引目录
	private static String indexPath = "D://lucence";
	static {
		try {
			//文件目录
			if (StringUtils.isEmpty(indexPath)) {
				//内存目录 缺点 断电丢失
				directory = new RAMDirectory();
			} else {
				directory = FSDirectory.open(Paths.get(indexPath));
			}
			//做好的法  内存操作同步到硬盘
			IOContext context=new IOContext();
			directory =  new RAMDirectory(FSDirectory.open(Paths.get(indexPath)),context);
			
			//分词器可以换 
			analyzer = new StandardAnalyzer();
			indexWriterConfig =  new IndexWriterConfig(analyzer);
			// 指定在JVM退出前要执行的代码
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					LucenceUtil.indexChanged();
					try {
						if (null != directory) {
							directory.close();
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			});

		} catch (Exception e) {
			logger.error("lucence 配置错误.....");
			throw new RuntimeException(e);
		}
	}
	public static Directory getDirectory() {
		return directory;
	}
	public static Analyzer getAnalyzer() {
		return analyzer;
	}
	public static IndexWriterConfig getIndexWriterConfig(){
		return indexWriterConfig;
	}
}
