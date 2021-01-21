package com.jun.plugin.creator.impl;

import com.jun.plugin.bean.Config;
import com.jun.plugin.bean.Constants;
import com.jun.plugin.bean.ModuleEnum;
import com.jun.plugin.bean.TableInfo;
import com.jun.plugin.creator.AbstractFileCreator;

/**
 * 创建mapperClass
 * @author Wujun
 */
public class DaoClassCreator extends AbstractFileCreator {
    private static DaoClassCreator creator;

    private DaoClassCreator() {
        super();
    }

    private DaoClassCreator(Config conf) {
        super();
        init(conf);
    }

    public static synchronized DaoClassCreator getInstance(Config conf) {
        if (null == creator) {
            creator = new DaoClassCreator(conf);
        }
        return creator;
    }

    @Override
    public String getFileName(TableInfo tableInfo) {
        return tableInfo.getBeanName() + conf.getDaoName() + Constants.JAVA_SUFFIX;
    }

    @Override
    public String getTempletName() {
        return ModuleEnum.Dao.name() + Constants.TEMPLET_SUFFIX;
    }

    @Override
    public String getDirPath() {
        return javaPath + conf.getDao_dir();
    }

    @Override
    public void setPackageName(TableInfo tableInfo) {
        String daoPackage = conf.getBasePackage() + Constants.PACKAGE_SEPARATOR + conf.getDaoPackage();
        tableInfo.setDaoPackage(daoPackage);
    }

}
