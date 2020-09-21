package com.osmp.web.system.bundle.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * 
 * @author: wangqiang
 * @date: 2014年11月25日 下午2:19:44
 */
public class BundleInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1591023131135627099L;
    private List<PropObj> props;

    public List<PropObj> getProps() {
        return props;
    }

    public void setProps(List<PropObj> props) {
        this.props = props;
    }

    @Override
    public String toString() {
        return "BundleInfo [props=" + props + "]";
    }

}
