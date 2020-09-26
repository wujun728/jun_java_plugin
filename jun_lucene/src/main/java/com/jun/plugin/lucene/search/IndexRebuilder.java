package com.jun.plugin.lucene.search;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.lucene.search.util.NumberUtils;
import com.jun.plugin.lucene.search.util.StringUtils;

/**
 * 重建索引
 * User: Winter Lau
 * Date: 13-1-10
 * Time: 上午11:42
 */
public class IndexRebuilder {

    private final static Logger  log                 = LoggerFactory
        .getLogger(IndexRebuilder.class);
    private final static int     DEFAULT_BATCH_COUNT = 2000;

    private final static Options options;

    static {
        options = new Options() {
            /** 描述 */
            private static final long serialVersionUID = 5966095779728776706L;

            {
                addOption("s", "batch_size", true, "Objects count one time");
                addOption("p", "path", true, "Lucene index root path");
                addOption("b", "beans", true,
                    "Beans classes, use comma to seperate multiple class");
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
        String beans = cmd.getOptionValue('b');
        if (StringUtils.isBlank(idx_path) || StringUtils.isBlank(beans)) {
            new HelpFormatter().printHelp(
                "java -Djava.ext.dirs=lib net.oschina.search.IndexRebuilder [options]", options);
            System.exit(-1);
        }
        //每批次处理对象数，提升这个数值可提醒构建的性能，但要注意内存是否足够
        int batch_count = NumberUtils.toInt(cmd.getOptionValue('s'), DEFAULT_BATCH_COUNT);
        //初始化索引管理器
        IndexHolder holder = IndexHolder.init(idx_path);
        for (String beanClass : StringUtils.split(beans, ',')) {
            //Searchable obj = (Searchable)IndexRebuilder.class.getClassLoader().loadClass(beanClass).newInstance();
            Searchable obj = (Searchable) Class.forName(beanClass).newInstance();
            build(holder, obj, batch_count);
            holder.optimize(obj.getClass());
        }
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
     * 构建索引
     * @param holder
     * @param objClass
     * @param batch_count
     * @return
     */
    private static int build(IndexHolder holder, Searchable obj, int batch_count) throws Exception {
        int ic = 0;
        long last_id = 0L;
        do {
            List<? extends Searchable> objs = obj.ListAfter(last_id, batch_count);
            if (objs != null && objs.size() > 0) {
                ic += holder.add(objs);
                last_id = objs.get(objs.size() - 1).getId();

                log.info(ic + " documents of " + obj.getClass().getSimpleName() + " added.");
            }
            if (objs == null || objs.size() < batch_count)
                break;
        } while (true);

        return ic;
    }

}
