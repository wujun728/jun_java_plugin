package book.string.console;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 * 命令行程序的基类，是一个抽象类
 * 采用了Apache组织的命令行解析器的包
 */
public abstract class BaseConsoleClient {
    /**帮助选项，参数为-h或者--help，功能描述为Displays help */
    private static final Option HELP =
        OptionBuilder.withDescription("Displays help")
        .withLongOpt("help")
        .create("h");
    /** debug选项，是否打印调试信息     */
    public static final Option DEBUG = new Option("debug", "Enables debug mode");

    // 命令的参数选项
    private Options options = new Options();
    // 命令的帮助信息
    private String usageMsg;
    // 命令的头信息
    private String header;
    //是否debug
    private boolean debugMode; 
    /**
     * 默认构造函数
     */
    protected BaseConsoleClient(){
    	//不打印debug信息
        debugMode = false;
        //将帮助和调试选项添加到命令的参数选项中
        options.addOption(HELP);
        options.addOption(DEBUG);
        //默认帮助信息是类名
        usageMsg = getClass().getName();
    }
    
    /**
     * 增加一个命令的参数选项
     * @param option
     */
    protected void addOption(Option option){
        this.options.addOption(option);
    }
    
    protected void setUsageMsg(String msg){
        this.usageMsg = msg;
    }
    
    protected void setHeader(String header){
        this.header = header;
    }
    
    /**
     * 显示帮助信息
     */
    public void displayUsage(){
        HelpFormatter formatter = new HelpFormatter();
        String header = (this.header == null) ?
                "Options:" : this.header;
        formatter.printHelp(usageMsg, header, options, null, false);
    }
    
    /**
     * 解析命令行参数
     * @param args
     * @return
     * @throws Exception
     */
    public CommandLine parse(String[] args) throws Exception{
        //新建一个命令行解析器
        CommandLineParser parser = new BasicParser();
        //用解析器解析命令行参数
        CommandLine line = parser.parse(options, args);
        //如果命令行中有-h，则打印帮助信息，并退出
        if (line.hasOption("h")){
            displayUsage();
            System.exit(0);
        }      
        if (line.hasOption("debug")){
            this.debugMode = true;
        }
        return line;        
    }
    
    public boolean isDebugMode(){
        return this.debugMode;	
    }
}
