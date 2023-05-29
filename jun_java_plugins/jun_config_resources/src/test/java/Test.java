import org.junit.Before;

import com.jun.plugin.resources.KeyConstants;
import com.jun.plugin.resources.Resources;
import com.jun.plugin.resources.config.GlobalConfig;
import com.jun.plugin.resources.core.AutoResources;
import com.jun.plugin.resources.db.select.Select;
import com.jun.plugin.resources.extend.*;
import com.jun.plugin.resources.repository.RemoteRepository;
import com.jun.plugin.resources.repository.git.GitCore;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Hong on 2017/12/27.
 */
public class Test {

    @Before
    public void useKey() {
        //设置密钥
        GlobalConfig.useKey("Hong");
    }

    @org.junit.Test
    public void globalConfig() {
        System.setProperty("ant.core.resources.profile", "release");
        Map<Object, Object> map = GlobalConfig.get().getConfig();
        for (Object key : map.keySet()) {
            System.err.println(key + "：" + map.get(key));
        }
    }

    @org.junit.Test
    public void loadProperties() throws IOException {
        PropertiesResources resources = new PropertiesResources();
        resources.loadByClassPath("properties_test.properties");
        for (Object key : resources.getResources().keySet()) {
            System.err.println(key + "：" + resources.getResources().get(key));
        }
    }

    @org.junit.Test
    public void loadPropertiesByFilePath() throws IOException {
        PropertiesResources resources = new PropertiesResources();
        resources.loadByFilePath("C:\\Users\\Hong\\AppData\\Local\\Temp\\AntResources\\git\\app.properties");
        System.err.println(resources.getResources());
    }

    @org.junit.Test
    public void loadXmlProperties() throws IOException {
        XmlResources resources = new XmlResources();
        resources.loadByClassPath("xml_test.xml");
        for (Object key : resources.getResources().keySet()) {
            System.err.println(key + "：" + resources.getResources().get(key));
        }
    }

    @org.junit.Test
    public void loadXmlPropertiesByFilePath() throws IOException {
        XmlResources resources = new XmlResources();
        resources.loadByFilePath("D:\\config\\application.xml");
        System.err.println(resources.getResources());
    }

    @org.junit.Test
    public void loadYamlProperties() throws IOException {
        YamlResources resources = new YamlResources();
        resources.loadByClassPath("application.yml");
        for (Object key : resources.getResources().keySet()) {
            System.err.println(key + "：" + resources.getResources().get(key));
        }
    }

    @org.junit.Test
    public void loadYamlPropertiesByFilePath() throws IOException {
        YamlResources resources = new YamlResources();
        resources.loadByFilePath("D:/config/application.yml");
        for (Object key : resources.getResources().keySet()) {
            System.err.println(key + "：" + resources.getResources().get(key));
        }
    }

    @org.junit.Test
    public void loadDbProperties() {
        DbResources resources = new DbResources();
        resources.load();
        for (Object key : resources.getResources().keySet()) {
            System.err.println(key + "：" + resources.getResources().get(key));
        }
    }

    @org.junit.Test
    public void loadDbPropertiesByTableName() {
        DbResources resources = new DbResources();
        resources.load("tb_config");
        for (Object key : resources.getResources().keySet()) {
            System.err.println(key + "：" + resources.getResources().get(key));
        }
    }

    @org.junit.Test
    public void autoReadResources() throws IOException {
        System.setProperty("ant.core.resources.profile", "release");
        //Resources resources = new AutoResources("db:tb_config");
        //Resources resources = new AutoResources("classpath:application.yml");
        Resources resources = new AutoResources("git:yml");
        for (Object key : resources.getResources().keySet()) {
            System.err.println(key + "：" + resources.getResources().get(key));
        }
    }

    @org.junit.Test
    public void sqlBuilder() {
        Select select = new Select();
        select.table(GlobalConfig.get().getValue(KeyConstants.DB_TABLE_NAME))
                .columnAll();
        System.err.println(select);
    }

    @org.junit.Test
    public void testGit() {
        RemoteRepository remoteRepository = new GitCore();
        remoteRepository.cloneRepository();
        remoteRepository.pullRepository();
    }

    @org.junit.Test
    public void loadGit() {
        System.setProperty("ant.core.resources.profile", "release");
        GitResources resources = new GitResources();
        resources.load("yml");
        for (Object key : resources.getResources().keySet()) {
            System.err.println(key + "：" + resources.getResources().get(key));
        }
    }
}
