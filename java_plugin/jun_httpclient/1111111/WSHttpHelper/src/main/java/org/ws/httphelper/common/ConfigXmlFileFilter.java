package org.ws.httphelper.common;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Administrator on 15-12-30.
 */
public class ConfigXmlFileFilter implements FilenameFilter {
    private String matchPathName;

    public ConfigXmlFileFilter(String matchPathName) {
        super();
        this.matchPathName = matchPathName.replace("*", "[^/]+");
    }

    @Override
    public boolean accept(File file, String name) {
        boolean result = name.matches(this.matchPathName);
        if (result && file.isFile()) {
            result = result && name.endsWith(".xml");
        }
        return result;
    }
}
