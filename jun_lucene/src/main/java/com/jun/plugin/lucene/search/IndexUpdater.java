package com.jun.plugin.lucene.search;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.lucene.search.util.StringUtils;

/**
 * 索引增量更新
 * 该类接受两个参数
 * idx_path 索引根目录
 * tasker 任务获取实现类
 * 
 * 对应 build.xml 的配置
 * 
    <target depends="lucene_init" name="lucene_build">
        <echo message="Build lucene index of ${ant.project.name}"/>    	
		<java classname="net.oschina.search.IndexUpdater" classpathref="oschina.classpath" fork="true">
			<jvmarg value="-Xmx512m" />			
			<arg value="-p" />			
			<arg value="${lucene.dir}" />			
			<arg value="-t" />			
			<arg value="net.oschina.search.IndexTasker" />
		</java>
	</target>
	
 * @author Winter Lau
 */
public class IndexUpdater {

    private final static Logger  log = LoggerFactory.getLogger(IndexUpdater.class);

    private final static Options options;

    static {
        options = new Options() {
            /** 描述 */
            private static final long serialVersionUID = -8014718159826725974L;

            {
                addOption("p", "path", true, "Lucene index root path");
                addOption("t", "tasker", true, "Full classname of the tasker");
                addOption("h", "help", true, "Print this message");
            }
        };
    }

    public static void main(String[] args) throws Exception {
        execute(args);
        System.exit(0);
    }

    public static void execute(String[] args) throws Exception {
        //参数解析
        CommandLine cmd = parseCmdArgs(args);
        String idx_path = cmd.getOptionValue('p');
        String taskerClassname = cmd.getOptionValue('t');
        if (StringUtils.isBlank(idx_path) || StringUtils.isBlank(taskerClassname)) {
            new HelpFormatter().printHelp(
                "java -Djava.ext.dirs=lib net.oschina.search.IndexUpdater [options]", options);
            System.exit(-1);
        }
        IndexTasker tasker = null;
        try {
            tasker = (IndexTasker) Class.forName(cmd.getOptionValue('t')).newInstance();
        } catch (Exception e) {
            log.error("Unabled to instantiate index loader", e);
            System.exit(-2);
        }
        //初始化索引管理器
        IndexHolder holder = IndexHolder.init(idx_path);

        //处理索引更新
        List<IndexTask> tasks = tasker.list();

        for (IndexTask task : tasks) {
            execute(holder, task, true);
        }

        if (tasks.size() > 0)
            log.info(tasks.size() + " Index tasks executed finished.");
    }

    private static CommandLine parseCmdArgs(String[] args) {
        CommandLineParser parser = new PosixParser();
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            log.error("Arguments parsing failed.", e);
        }
        return null;
    }

    /**
     * 处理单个索引任务
     * @param holder
     * @param task
     * @param update_status
     * @throws Exception
     */
    private static void execute(IndexHolder holder, IndexTask task,
                                boolean update_status) throws Exception {
        Searchable obj = (Searchable) task.object();
        if (obj != null) {
            switch (task.getOpt()) {
                case IndexTask.OPT_ADD:
                    holder.add(Arrays.asList(obj));
                    break;
                case IndexTask.OPT_DELETE:
                    holder.delete(Arrays.asList(obj));
                    break;
                case IndexTask.OPT_UPDATE:
                    holder.update(Arrays.asList(obj));
            }
            if (update_status)
                task.afterBuild();
        }
    }

}
