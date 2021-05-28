package com.icloud.cache.vo;

import java.util.ArrayList;
import java.util.List;

import com.icloud.cache.entity.CacheBean;

public class CacheTree extends CacheBean {
    private String id;
    private String parentId;
    private String text = null;
    private List<CacheTree> nodes = new ArrayList<CacheTree>();

    public CacheTree(CacheBean cache) {
        this.setKey(cache.getKey());
        this.setDesc(cache.getDesc());
        this.setExpireTime(cache.getExpireTime());
    }

    public CacheTree() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.text = id;
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CacheTree other = (CacheTree) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (parentId == null) {
            if (other.parentId != null)
                return false;
        } else if (!parentId.equals(other.parentId))
            return false;
        return true;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CacheTree> getNodes() {
        return nodes;
    }

    public void setNodes(List<CacheTree> nodes) {
        this.nodes = nodes;
    }

}
