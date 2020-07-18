package cn.org.rapid_framework.generator.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerHelper {

	public static List<String> getAvailableAutoInclude(Configuration conf,List<String> autoIncludes) {
		List<String> results = new ArrayList();
		for(String autoInclude : autoIncludes) {
			try {
				Template t = new Template("__auto_include_test__",new StringReader("1"),conf);
				conf.setAutoIncludes(Arrays.asList(new String[]{autoInclude}));
				t.process(new HashMap(), new StringWriter());
				results.add(autoInclude);
			}catch(Exception e) {
			}
		}
		return results;
	}
	
	public static void processTemplate(Template template, Map model, File outputFile,String encoding) throws IOException, TemplateException {
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile),encoding));
		template.process(model,out);
		out.close();
	}
	
	public static String processTemplateString(String templateString,Map model,Configuration conf) {
		StringWriter out = new StringWriter();
		try {
			Template template = new Template("templateString...",new StringReader(templateString),conf);
			template.process(model, out);
			return out.toString();
		}catch(Exception e) {
			throw new IllegalStateException("cannot process templateString:"+templateString+" cause:"+e,e);
		}
	}
}
