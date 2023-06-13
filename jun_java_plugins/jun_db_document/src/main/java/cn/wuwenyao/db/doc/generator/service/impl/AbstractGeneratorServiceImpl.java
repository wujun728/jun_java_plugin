package cn.wuwenyao.db.doc.generator.service.impl;

import cn.wuwenyao.db.doc.generator.config.GeneratorConfig;
import cn.wuwenyao.db.doc.generator.dao.DbInfoDao;
import cn.wuwenyao.db.doc.generator.service.GeneratorService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/***
 * 文档生成服务-抽象基类
 *
 * @author wwy shiqiyue.github.com
 *
 */
public abstract class AbstractGeneratorServiceImpl implements GeneratorService {

    protected DbInfoDao dbInfoDao;

    protected GeneratorConfig generatorConfig;

    /**
     * windows系统名称前缀
     */
    private static final String SYSTEM_WINDOWS_PREFIX = "win";


    @Override
    public void generate() throws Exception {
        generateDbDoc();
        openDir();
    }

    /***
     * 打开目录
     */
    private void openDir() throws IOException {
        // 弹出目标文件夹
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith(SYSTEM_WINDOWS_PREFIX)) {
            Runtime.getRuntime().exec("explorer " + generatorConfig.getTargetFileDir());
        }

    }

    /***
     * 创建文件
     * @param fileSuffix 文件后缀
     * @return
     */
    protected File createFile(String fileSuffix) throws IOException {
        File dir = new File(generatorConfig.getTargetFileDir());
        FileUtils.forceMkdir(dir);
        Random random = new Random();
        String filename = DateFormatUtils.format(new Date(), "yyyy-MM-dd_hh-mm-ss") + random.nextInt(10) + "." + fileSuffix;
        File file = new File(dir, filename);
        return file;
    }

    @Override
    public void setDbInfoDao(DbInfoDao dbInfoDao) {
        this.dbInfoDao = dbInfoDao;
    }

    @Override
    public void setGeneratorConfig(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }


}
