package com.jun.plugin.commons.util.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class Mkdir {
	/**
	 * @param args
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Options opt = new Options();
		opt.addOption("p", false, "no error if existing, "
				+ "make parent directories as needed.");
		opt.addOption("v", "verbose", false, "explain what is being done.");
		opt.addOption(
				OptionBuilder.withArgName("file")
				.hasArg()
				.withDescription("search for buildfile towards the root of the filesystem and use it")
				.create("O"));
		opt.addOption(
				OptionBuilder.withLongOpt("block-size")
				.withDescription("use SIZE-byte blocks")
				.withValueSeparator('=')
				.hasArg()
				.create());
		opt.addOption("h", "help", false, "print help for the command.");

		String formatstr = "gmkdir [-p][-v/--verbose][--block-size][-h/--help] DirectoryName";

		HelpFormatter formatter = new HelpFormatter();
		CommandLineParser parser = new PosixParser();
		CommandLine cl = null;
		try {
			// 处理Options和参数
			cl = parser.parse(opt, args);
		} catch (ParseException e) {
			formatter.printHelp(formatstr, opt); // 如果发生异常，则打印出帮助信息
		}
		// 如果包含有-h或--help，则打印出帮助信息
		if (cl.hasOption("h")) {
			HelpFormatter hf = new HelpFormatter();
			hf.printHelp(formatstr, "", opt, "");
			return;
		}
		// 判断是否有-p参数
		if (cl.hasOption("p")) {
			System.out.println("has p");
		}
		// 判断是否有-v或--verbose参数
		if (cl.hasOption("v")) {
			System.out.println("has v");
		}
		// 获取参数值，这里主要是DirectoryName
		String[] str = cl.getArgs();
		int length = str.length;
		System.out.println("length=" + length);
		System.out.println("Str[0]=" + str[0]);
		// 判断是否含有block-size参数
		if (cl.hasOption("block-size")) {
			// print the value of block-size
			System.out.println("block-size=" + cl.getOptionValue("block-size"));
		}
	}
}