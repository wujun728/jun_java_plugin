package org.typroject.tyboot.component.opendata.im.easemob;

import org.springframework.http.HttpMethod;

public interface EasemobApiInfo {

    public HttpMethod getMethod();

    public String getUrl();

    public String[] getRequestParams();

    public String[] getPathParams();
}
