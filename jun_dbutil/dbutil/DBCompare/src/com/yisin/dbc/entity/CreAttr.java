package com.yisin.dbc.entity;

import com.yisin.dbc.util.Utililies;

public class CreAttr implements Cloneable {

	private String daoFrame;
	private String conFrame;
	private String entityName;
	private String entityPackage;
	private String entityFilePath;
	private String daoName;
	private String daoPackage;
	private String daoFilePath;
	private String serviceName;
	private String iserviceName;
	private String servicePackage;
	private String serviceFilePath;
	private String iservicePackage;
	private String iserviceFilePath;
	private String mapName;
	private String mapPackage;
	private String mapFilePath;
	private String hbmName;
	private String hbmPackage;
	private String hbmFilePath;
	private String controllerName;
	private String controllerPackage;
	private String controllerFilePath;
	private String actionName;
	private String actionPackage;
	private String actionFilePath;
	private String saveDir;
	private String saveDir2;
	
	private boolean isCreateBaseDao;
	private boolean isCreateBaseService;
	private boolean isCreateBaseController;
	
	private boolean isCreateController;
	private boolean isCreateService;
	private boolean isCreateDao;
	private boolean isCreateMapper;

	public String getDaoFrame() {
		return daoFrame;
	}

	public void setDaoFrame(String daoFrame) {
		this.daoFrame = daoFrame;
	}

	public String getConFrame() {
		return conFrame;
	}

	public void setConFrame(String conFrame) {
		this.conFrame = conFrame;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public String getDaoName() {
		return daoName;
	}

	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getMapPackage() {
		return mapPackage;
	}

	public void setMapPackage(String mapPackage) {
		this.mapPackage = mapPackage;
	}

	public String getHbmName() {
		return hbmName;
	}

	public void setHbmName(String hbmName) {
		this.hbmName = hbmName;
	}

	public String getHbmPackage() {
		return hbmPackage;
	}

	public void setHbmPackage(String hbmPackage) {
		this.hbmPackage = hbmPackage;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getControllerPackage() {
		return controllerPackage;
	}

	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionPackage() {
		return actionPackage;
	}

	public void setActionPackage(String actionPackage) {
		this.actionPackage = actionPackage;
	}

	public String getEntityFilePath() {
		return entityFilePath;
	}

	public void setEntityFilePath(String entityFilePath) {
		this.entityFilePath = entityFilePath;
	}

	public String getDaoFilePath() {
		return daoFilePath;
	}

	public void setDaoFilePath(String daoFilePath) {
		this.daoFilePath = daoFilePath;
	}

	public String getServiceFilePath() {
		return serviceFilePath;
	}

	public void setServiceFilePath(String serviceFilePath) {
		this.serviceFilePath = serviceFilePath;
	}

	public String getIservicePackage() {
		return iservicePackage;
	}

	public void setIservicePackage(String iservicePackage) {
		this.iservicePackage = iservicePackage;
	}

	public String getIserviceFilePath() {
		return iserviceFilePath;
	}

	public void setIserviceFilePath(String iserviceFilePath) {
		this.iserviceFilePath = iserviceFilePath;
	}

	public String getIserviceName() {
        return iserviceName;
    }

    public void setIserviceName(String iserviceName) {
        this.iserviceName = iserviceName;
    }

    public String getMapFilePath() {
		return mapFilePath;
	}

	public void setMapFilePath(String mapFilePath) {
		this.mapFilePath = mapFilePath;
	}

	public String getHbmFilePath() {
		return hbmFilePath;
	}

	public void setHbmFilePath(String hbmFilePath) {
		this.hbmFilePath = hbmFilePath;
	}

	public String getControllerFilePath() {
		return controllerFilePath;
	}

	public void setControllerFilePath(String controllerFilePath) {
		this.controllerFilePath = controllerFilePath;
	}

	public String getActionFilePath() {
		return actionFilePath;
	}

	public void setActionFilePath(String actionFilePath) {
		this.actionFilePath = actionFilePath;
	}

	public String getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}

	public String getSaveDir2() {
        return saveDir2;
    }

    public void setSaveDir2(String saveDir2) {
        this.saveDir2 = saveDir2;
    }

    public boolean isCreateBaseDao() {
        return isCreateBaseDao;
    }

    public void setCreateBaseDao(boolean isCreateBaseDao) {
        this.isCreateBaseDao = isCreateBaseDao;
    }

    public boolean isCreateBaseService() {
        return isCreateBaseService;
    }

    public void setCreateBaseService(boolean isCreateBaseService) {
        this.isCreateBaseService = isCreateBaseService;
    }

    public boolean isCreateBaseController() {
        return isCreateBaseController;
    }

    public void setCreateBaseController(boolean isCreateBaseController) {
        this.isCreateBaseController = isCreateBaseController;
    }

    public boolean isCreateController() {
        return isCreateController;
    }

    public void setCreateController(boolean isCreateController) {
        this.isCreateController = isCreateController;
    }

    public boolean isCreateService() {
        return isCreateService;
    }

    public void setCreateService(boolean isCreateService) {
        this.isCreateService = isCreateService;
    }

    public boolean isCreateDao() {
        return isCreateDao;
    }

    public void setCreateDao(boolean isCreateDao) {
        this.isCreateDao = isCreateDao;
    }

    public boolean isCreateMapper() {
        return isCreateMapper;
    }

    public void setCreateMapper(boolean isCreateMapper) {
        this.isCreateMapper = isCreateMapper;
    }

    public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName());
		sb.append(" # daoFrame=" + (daoFrame == null ? "null" : daoFrame.toString()));
		sb.append("; conFrame=" + (conFrame == null ? "null" : conFrame.toString()));
		sb.append("; entityName=" + (entityName == null ? "null" : entityName.toString()));
		sb.append("; entityPackage=" + (entityPackage == null ? "null" : entityPackage.toString()));
		sb.append("; entityFilePath=" + (entityFilePath == null ? "null" : entityFilePath.toString()));
		sb.append("; daoName=" + (daoName == null ? "null" : daoName.toString()));
		sb.append("; daoPackage=" + (daoPackage == null ? "null" : daoPackage.toString()));
		sb.append("; daoFilePath=" + (daoFilePath == null ? "null" : daoFilePath.toString()));
		sb.append("; serviceName=" + (serviceName == null ? "null" : serviceName.toString()));
		sb.append("; iserviceName=" + (iserviceName == null ? "null" : iserviceName.toString()));
		sb.append("; servicePackage=" + (servicePackage == null ? "null" : servicePackage.toString()));
		sb.append("; iservicePackage=" + (iservicePackage == null ? "null" : iservicePackage.toString()));
		sb.append("; serviceFilePath=" + (serviceFilePath == null ? "null" : serviceFilePath.toString()));
		sb.append("; iserviceFilePath=" + (iserviceFilePath == null ? "null" : iserviceFilePath.toString()));
		sb.append("; mapName=" + (mapName == null ? "null" : mapName.toString()));
		sb.append("; mapPackage=" + (mapPackage == null ? "null" : mapPackage.toString()));
		sb.append("; mapFilePath=" + (mapFilePath == null ? "null" : mapFilePath.toString()));
		sb.append("; hbmName=" + (hbmName == null ? "null" : hbmName.toString()));
		sb.append("; hbmPackage=" + (hbmPackage == null ? "null" : hbmPackage.toString()));
		sb.append("; hbmFilePath=" + (hbmFilePath == null ? "null" : hbmFilePath.toString()));
		sb.append("; controllerName=" + (controllerName == null ? "null" : controllerName.toString()));
		sb.append("; controllerPackage=" + (controllerPackage == null ? "null" : controllerPackage.toString()));
		sb.append("; controllerFilePath=" + (controllerFilePath == null ? "null" : controllerFilePath.toString()));
		sb.append("; actionName=" + (actionName == null ? "null" : actionName.toString()));
		sb.append("; actionPackage=" + (actionPackage == null ? "null" : actionPackage.toString()));
		sb.append("; actionFilePath=" + (actionFilePath == null ? "null" : actionFilePath.toString()));
		sb.append("; saveDir=" + (saveDir == null ? "null" : saveDir.toString()));
		return sb.toString();
	}

	public void replaceAll(String table) {
		this.entityName = Utililies.tableToEntity(table);
		this.entityPackage = Utililies.parseTemplate(this.entityPackage, "EntityName", this.entityName);
		this.entityFilePath = Utililies.parseTemplate(this.entityFilePath, "EntityName", this.entityName);

		this.daoName = Utililies.parseTemplate(this.daoName, "EntityName", this.entityName);
		this.daoPackage = Utililies.parseTemplate(this.daoPackage, "EntityName", this.entityName);
		this.daoFilePath = Utililies.parseTemplate(this.daoFilePath, "EntityName", this.entityName);

		this.serviceName = Utililies.parseTemplate(this.serviceName, "EntityName", this.entityName);
		this.servicePackage = Utililies.parseTemplate(this.servicePackage, "EntityName", this.entityName);
		this.serviceFilePath = Utililies.parseTemplate(this.serviceFilePath, "EntityName", this.entityName);
		this.iserviceName = Utililies.parseTemplate(this.iserviceName, "EntityName", this.entityName);
		this.iservicePackage = Utililies.parseTemplate(this.iservicePackage, "EntityName", this.entityName);
		this.iserviceFilePath = Utililies.parseTemplate(this.iserviceFilePath, "EntityName", this.entityName);

		this.mapName = Utililies.parseTemplate(this.mapName, "EntityName", this.entityName);
		this.mapPackage = Utililies.parseTemplate(this.mapPackage, "EntityName", this.entityName);
		this.mapFilePath = Utililies.parseTemplate(this.mapFilePath, "EntityName", this.entityName);

		this.hbmName = Utililies.parseTemplate(this.hbmName, "EntityName", this.entityName);
		this.hbmPackage = Utililies.parseTemplate(this.hbmPackage, "EntityName", this.entityName);
		this.hbmFilePath = Utililies.parseTemplate(this.hbmFilePath, "EntityName", this.entityName);

		this.controllerName = Utililies.parseTemplate(this.controllerName, "EntityName", this.entityName);
		this.controllerPackage = Utililies.parseTemplate(this.controllerPackage, "EntityName", this.entityName);
		this.controllerFilePath = Utililies.parseTemplate(this.controllerFilePath, "EntityName", this.entityName);

		this.actionName = Utililies.parseTemplate(this.actionName, "EntityName", this.entityName);
		this.actionPackage = Utililies.parseTemplate(this.actionPackage, "EntityName", this.entityName);
		this.actionFilePath = Utililies.parseTemplate(this.actionFilePath, "EntityName", this.entityName);
	}

	public CreAttr clone() {
		CreAttr o = null;
		try {
			o = (CreAttr) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

}
