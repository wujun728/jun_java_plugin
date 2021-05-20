package com.ibeetl.admin.core.file;

import java.io.OutputStream;

public abstract class FileItem {
    protected Long id;
	protected String name;
	protected String path;
	boolean isTemp = false;
	
	public abstract OutputStream openOutpuStream();
	
	public abstract void copy(OutputStream os);
	
	
	public abstract boolean delete();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

    public boolean isTemp() {
        return isTemp;
    }

    public void setTemp(boolean isTemp) {
        this.isTemp = isTemp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
	
}
