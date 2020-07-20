
/**
 * 功 能 描 述:
 * 嵌入式web服务器启动
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-12下午03:17:01
 * <br>项 目 信 息:paramecium:.WebStart.java
 */

public class WebStart {

	public static void main(String[] args) throws Exception {
		 new TomcatServer().start();
	}

}
