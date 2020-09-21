/*   
 * Project: OSMP
 * FileName: DataServiceMappingInfo.java
 * version: V1.0
 */
package com.osmp.service.bean;

import com.osmp.jdbc.define.Column;
import com.osmp.service.util.ServiceUtil;

public class DataServiceMappingInfo {
    @Column(mapField="bundle",name="bundle")
    private String bundle;
    @Column(mapField="version",name="version")
    private String version;
    @Column(mapField="serviceName",name="serviceName")
    private String serviceName;
    @Column(mapField="interfaceName",name="interfaceName")
    private String interfaceName;
    public String getBundle() {
        return bundle;
    }
    public void setBundle(String bundle) {
        this.bundle = bundle;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getInterfaceName() {
        return interfaceName;
    }
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
    public String generateServiceName(){
        return ServiceUtil.generateServiceName(bundle, version, serviceName);
    }
}
