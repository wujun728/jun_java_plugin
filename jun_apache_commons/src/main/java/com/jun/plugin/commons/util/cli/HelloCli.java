/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.cli.HelloCli.java
 * Class:			HelloCli
 * Date:			2013年9月4日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.jun.plugin.commons.util.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年9月4日 下午3:07:20 
 */

public class HelloCli {

	/**  
	 * 描述
	 * @param args  
	 * @throws ParseException 
	 */
	public static void main(String[] args) {
		Options options = new Options();
		//其中 addOption() 方法有三个参数，
		//第一个参数设定这个 option 的单字符名字，
		//第二个参数指明这个 option 是否需要输入数值，
		//第三个参数是对这个 option 的简要描述。在这个代码片段中，
		//第一个参数只是列出帮助文件，不需要用户输入任何值，
		//而第二个参数则是需要用户输入 HTTP 的通信协议，所以这两个 option 的第二个参数分别为 false 和 true，
		//完整的代码及注释请参考第二章节的命令行开发部分。
		options.addOption("h", "help", false, "say hi.");
		options.addOption("hi", true, "exe hi.");
		
		String usage = "SAYHI -hi name"; 
 		
		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = null;
		
		HelpFormatter formatter = new HelpFormatter();
		try {
			commandLine = parser.parse(options, args);
		} catch (Exception e) {
			formatter.printHelp(usage, options);
		}
		
		if (commandLine.hasOption("h")) {
			formatter.printHelp(usage, options);
			return ;
		}	
		
		if (commandLine.hasOption("hi")) {
			System.out.println("Hi, " + commandLine.getOptionValue("hi"));
		}
	}
	
	@SuppressWarnings("static-access")
	public void test() {
		Option help = new Option("h", "the command help");
		Option user = OptionBuilder.withArgName("type").hasArg()
				.withDescription("target the search type").create("t");

		// 此处定义参数类似于 java 命令中的 -D<name>=<value>
		Option property = OptionBuilder
				.withArgName("property=value")
				.hasArgs(2)
				.withValueSeparator()
				.withDescription(
						"search the objects which have the target property and value")
				.create("D");
		
		Options opts = new Options();
		opts.addOption(help);
		opts.addOption(user);
		opts.addOption(property);
	}

}
