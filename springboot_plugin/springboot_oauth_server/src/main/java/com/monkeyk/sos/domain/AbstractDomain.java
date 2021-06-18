package com.monkeyk.sos.domain;

import com.monkeyk.sos.domain.shared.GuidGenerator;
import com.monkeyk.sos.infrastructure.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Wujun
 */
public abstract class AbstractDomain implements Serializable {

    private static final long serialVersionUID = 6569365774429340632L;
    /**
     * Database id
     */
    protected int id;

    protected boolean archived;
    /**
     * Domain business guid.
     */
    protected String guid = GuidGenerator.generate();

    /**
     * The domain create time.
     */
    protected LocalDateTime createTime = DateUtils.now();

    public AbstractDomain() {
    }

    public int id() {
        return id;
    }

    public void id(int id) {
        this.id = id;
    }

    public boolean archived() {
        return archived;
    }

    public AbstractDomain archived(boolean archived) {
        this.archived = archived;
        return this;
    }

    public String guid() {
        return guid;
    }

    public void guid(String guid) {
        this.guid = guid;
    }

    public LocalDateTime createTime() {
        return createTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractDomain)) {
            return false;
        }
        AbstractDomain that = (AbstractDomain) o;
        return guid.equals(that.guid);
    }

    @Override
    public int hashCode() {
        return guid.hashCode();
    }

    //For subclass override it
    public void saveOrUpdate() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{id=").append(id);
        sb.append(", archived=").append(archived);
        sb.append(", guid='").append(guid).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}