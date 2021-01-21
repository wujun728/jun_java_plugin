package com.jun.plugin.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util.DbUtils;

/**
 * 配置
 * @author Wujun
 */
public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    private static final List<String> allModules = new ArrayList<String>();

    static {
        allModules.add(ModuleEnum.Entity.name());
        allModules.add(ModuleEnum.Dao.name());
        allModules.add(ModuleEnum.MapperXML.name());
        // allModules.add(ModuleEnum.DaoTest.name());
        // allModules.add(ModuleEnum.Model.name());
        // allModules.add(ModuleEnum.ModelTest.name());
        allModules.add(ModuleEnum.Service.name());
        // allModules.add(ModuleEnum.ServiceTest.name());
        allModules.add(ModuleEnum.ServiceImpl.name());
        allModules.add(ModuleEnum.Controller.name());
        // allModules.add(ModuleEnum.ControllerTest.name());
    }


    private String basePackage;

    private String entityPackage;
    private String entity_dir;
    private String entity_modulename;

    private String daoPackage;
    private String dao_dir;
    private String dao_modulename;

    private String modelPackage;
    private String model_dir;
    private String model_modulename;

    private String xmlPackage;
    private String xml_dir;
    private String xml_modulename;

    private String servicePackage;
    private String service_dir;
    private String service_modulename;


    private String controllerPackage;
    private String controller_dir;
    private String controller_modulename;


    private String serviceImplPackage;
    private String serviceimpl_dir;
    private String serviceimpl_modulename;

    private String entitySuffix;
    private String daoName;
    private String implSuffix;


    private String serviceName;
    private String controllerName;
    private String mapperXmlName;
    private boolean serviceNeedI;
    private String testSuffix;

    private String tables;
    private String needModules;
    //	private List<String> modules;
    private boolean force;// 重新生成文件
    private boolean prefix;// 表名前缀
    private boolean underline2Camel;// 是否需要将表字段转成驼峰
    private String templatePath;
    
    
    /**
     * @return
     */
    public Config getConf() {
        Properties pro = new Properties();
        try {
            pro.load(DbUtils.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            logger.error("未找到配置文件", e);
        }

        String basePackage = pro.getProperty("base_package");

        String entityPackage = pro.getProperty("entity_package");
        String entity_dir = pro.getProperty("entity_dir");
        String entity_modulename = pro.getProperty("entity_modulename");

        String daoPackage = pro.getProperty("dao_package");
        String dao_modulename = pro.getProperty("dao_modulename");
        String dao_dir = pro.getProperty("dao_dir");

        String modelPackage = pro.getProperty("model_package");
        String model_dir = pro.getProperty("model_dir");
        String model_modulename = pro.getProperty("model_modulename");

        String xmlPackage = pro.getProperty("xml_package");
        String xml_dir = pro.getProperty("xml_dir");
        String xml_modulename = pro.getProperty("xml_modulename");

        String servicePackage = pro.getProperty("service_package");
        String service_dir = pro.getProperty("service_dir");
        String service_modulename = pro.getProperty("service_modulename");


        String serviceImplPackage = pro.getProperty("serviceimpl_package");
        String serviceimpl_dir = pro.getProperty("serviceimpl_dir");
        String serviceimpl_modulename = pro.getProperty("service_modulename");




        String controllerPackage = pro.getProperty("controller_package");
        String controller_dir = pro.getProperty("controller_dir");
        String controller_modulename = pro.getProperty("controller_modulename");

        String tables = pro.getProperty("tables");
        String needModules = pro.getProperty("needModules");
        String forceStr = pro.getProperty("force");
        String prefixStr = pro.getProperty("prefix");
        String underline2CamelStr = pro.getProperty("underline2Camel");


        String entitySuffix = pro.getProperty("eneity_suffix");
        String daoName = pro.getProperty("dao_name");

        String implSuffix = pro.getProperty("impl_suffix");
        String serviceName = pro.getProperty("service_name");
        String controllerName = pro.getProperty("controller_name");
        String mapperXmlName = pro.getProperty("mapperxml_name");
        String testSuffix = pro.getProperty("test_suffix");
        String templatePath = pro.getProperty("templatePath");

        this.setBasePackage(basePackage);

        this.setEntityPackage(entityPackage);
        this.setEntity_dir(entity_dir);
        this.setEntity_modulename(entity_modulename);

        this.setDaoPackage(daoPackage);
        this.setDao_dir(dao_dir);
        this.setDao_modulename(dao_modulename);

        this.setXmlPackage(xmlPackage);
        this.setXml_dir(xml_dir);
        this.setXml_modulename(xml_modulename);

        this.setModelPackage(modelPackage);
        this.setModel_dir(model_dir);
        this.setModel_modulename(model_modulename);

        this.setServicePackage(servicePackage);
        this.setService_dir(service_dir);
        this.setService_modulename(service_modulename);

        this.setServiceImplPackage(serviceImplPackage);
        this.setServiceimpl_dir(serviceimpl_dir);
        this.setServiceimpl_modulename(service_modulename);

        this.setControllerPackage(controllerPackage);
        this.setController_dir(controller_dir);
        this.setController_modulename(controller_modulename);

        this.setTables(tables);
        this.setNeedModules(needModules);
        this.setEntitySuffix(entitySuffix);
        this.setDaoName(daoName);
        this.setImplSuffix(implSuffix);


        this.setServiceName(serviceName);
        this.setControllerName(controllerName);
        this.setMapperXmlName(mapperXmlName);
        this.setTestSuffix(testSuffix);
        this.setTemplatePath(templatePath);

        String serviceNeedIStr = pro.getProperty("service_need_i");

        boolean force = false;
        boolean prefix = true;
        boolean underline2Camel = true;
        boolean serviceNeedI = false;

        if (StringUtils.isNotBlank(serviceNeedIStr)
                && (serviceNeedIStr.equals("true") || serviceNeedIStr.equals("false"))) {
            serviceNeedI = Boolean.parseBoolean(serviceNeedIStr);
        }
        if (StringUtils.isNotBlank(forceStr) && (forceStr.equals("true") || forceStr.equals("false"))) {
            force = Boolean.parseBoolean(forceStr);
        }
        if (StringUtils.isNotBlank(prefixStr) && (prefixStr.equals("true") || prefixStr.equals("false"))) {
            prefix = Boolean.parseBoolean(prefixStr);
        }
        if (StringUtils.isNotBlank(underline2CamelStr)
                && (underline2CamelStr.equals("true") || underline2CamelStr.equals("false"))) {
            underline2Camel = Boolean.parseBoolean(underline2CamelStr);
        }

        this.setForce(force);
        this.setPrefix(prefix);
        this.setUnderline2Camel(underline2Camel);
        this.setServiceNeedI(serviceNeedI);

        return this;
    }
    

    public String getTemplatePath() {
		return templatePath;
	}


	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}


	public static List<String> getAllModules() {
        return allModules;
    }

    // public static void setAllModules(List<String> allModules) {
    // Conf.allModules = allModules;
    // }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public boolean isPrefix() {
        return prefix;
    }

    public void setPrefix(boolean prefix) {
        this.prefix = prefix;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    public String getNeedModules() {
        return needModules;
    }

    public void setNeedModules(String needModules) {
        this.needModules = needModules;
    }

    public List<String> getModules() {
        List<String> modules = new ArrayList<>();
        if (StringUtils.isBlank(needModules) || needModules.equals("all")) {
            modules = allModules;
        } else {
            String needModules = getNeedModules();
            modules = Arrays.asList(needModules.split(","));
        }
        return modules;
    }

//	public void setModules(List<String> modules) {
//		this.modules = modules;
//	}

    public boolean isUnderline2Camel() {
        return underline2Camel;
    }

    public void setUnderline2Camel(boolean underline2Camel) {
        this.underline2Camel = underline2Camel;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getXmlPackage() {
        return xmlPackage;
    }

    public void setXmlPackage(String xmlPackage) {
        this.xmlPackage = xmlPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getEntitySuffix() {
        return entitySuffix;
    }

    public void setEntitySuffix(String entitySuffix) {
        this.entitySuffix = entitySuffix;
    }

    public String getDaoName() {
        return daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getImplSuffix() {
        return implSuffix;
    }

    public void setImplSuffix(String implSuffix) {
        this.implSuffix = implSuffix;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public void setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getMapperXmlName() {
        return mapperXmlName;
    }

    public void setMapperXmlName(String mapperXmlName) {
        this.mapperXmlName = mapperXmlName;
    }

    public boolean isServiceNeedI() {
        return serviceNeedI;
    }

    public void setServiceNeedI(boolean serviceNeedI) {
        this.serviceNeedI = serviceNeedI;
    }

    public String getTestSuffix() {
        return testSuffix;
    }

    public void setTestSuffix(String testSuffix) {
        this.testSuffix = testSuffix;
    }

//    public static Logger getLogger() {
////        return logger;
//    }

    public String getEntity_dir() {
        return entity_dir;
    }

    public void setEntity_dir(String entity_dir) {
        this.entity_dir = entity_dir;
    }

    public String getEntity_modulename() {
        return entity_modulename;
    }

    public void setEntity_modulename(String entity_modulename) {
        this.entity_modulename = entity_modulename;
    }

    public String getDao_dir() {
        return dao_dir;
    }

    public void setDao_dir(String dao_dir) {
        this.dao_dir = dao_dir;
    }

    public String getDao_modulename() {
        return dao_modulename;
    }

    public void setDao_modulename(String dao_modulename) {
        this.dao_modulename = dao_modulename;
    }

    public String getModel_dir() {
        return model_dir;
    }

    public void setModel_dir(String model_dir) {
        this.model_dir = model_dir;
    }

    public String getModel_modulename() {
        return model_modulename;
    }

    public void setModel_modulename(String model_modulename) {
        this.model_modulename = model_modulename;
    }

    public String getXml_dir() {
        return xml_dir;
    }

    public void setXml_dir(String xml_dir) {
        this.xml_dir = xml_dir;
    }

    public String getXml_modulename() {
        return xml_modulename;
    }

    public void setXml_modulename(String xml_modulename) {
        this.xml_modulename = xml_modulename;
    }

    public String getService_dir() {
        return service_dir;
    }

    public void setService_dir(String service_dir) {
        this.service_dir = service_dir;
    }

    public String getService_modulename() {
        return service_modulename;
    }

    public void setService_modulename(String service_modulename) {
        this.service_modulename = service_modulename;
    }

    public String getController_dir() {
        return controller_dir;
    }

    public void setController_dir(String controller_dir) {
        this.controller_dir = controller_dir;
    }

    public String getController_modulename() {
        return controller_modulename;
    }

    public void setController_modulename(String controller_modulename) {
        this.controller_modulename = controller_modulename;
    }

    public String getServiceimpl_dir() {
        return serviceimpl_dir;
    }

    public void setServiceimpl_dir(String serviceimpl_dir) {
        this.serviceimpl_dir = serviceimpl_dir;
    }

    public String getServiceimpl_modulename() {
        return serviceimpl_modulename;
    }

    public void setServiceimpl_modulename(String serviceimpl_modulename) {
        this.serviceimpl_modulename = serviceimpl_modulename;
    }
}
