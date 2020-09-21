package cn.jiangzeyin.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jiangzeyin on 2017/8/14.
 */
public class ResourceUtil {

    private ResourceUtil() {
    }

    public static InputStream getResource(String location) throws IOException {
        Assert.notNull(location, "location is null");
        if (location.startsWith("/")) {
            return getInputStream(location);
        }
        if (location.startsWith("classpath:")) {
            return getInputStream(location.substring("classpath:".length()));
        }
        try {
            URL url = new URL(location);
            return getInputStream(url);
        } catch (MalformedURLException var5) {
            return getInputStream(location);
        }
    }


    private static InputStream getInputStream(String classLocation) throws IOException {
        InputStream is = ResourceUtil.class.getResourceAsStream(classLocation);
        if (is == null)
            is = ClassLoader.getSystemResourceAsStream(classLocation);
        if (is == null) {
            throw new FileNotFoundException(classLocation + " cannot be opened because it does not exist");
        }
        return is;
    }

    private static InputStream getInputStream(URL url) throws IOException {
        URLConnection con = url.openConnection();
        boolean connected = con.getClass().getSimpleName().startsWith("JNLP");
        if (connected)
            throw new IllegalStateException("Already connected");
        try {
            return con.getInputStream();
        } catch (IOException var3) {
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
            throw var3;
        }
    }
}
