package org.typroject.tyboot.component.cache.zset;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by yaohelang on 2018/7/10.
 */
public class ZsetPageModel<T> implements Serializable {

    private Collection<T> members;

    private Long size;

    public Collection<T> getMembers() {
        return members;
    }

    public void setMembers(Collection<T> members) {
        this.members = members;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
