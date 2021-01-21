import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisGenerator {
	public static void main(String[] args) {
		List<String> war = new ArrayList<String>();
		Boolean ovr = true;
		File file = new File("D:\\workspace\\github\\jun_code_generator\\mybatis_generator\\generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(war);
		try {
			Configuration config = cp.parseConfiguration(file);
			DefaultShellCallback back = new DefaultShellCallback(ovr);
			MyBatisGenerator my = new MyBatisGenerator(config, back, war);
			my.generate(null);
			for (String s : war) {
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}