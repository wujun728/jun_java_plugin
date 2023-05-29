# activerecord

## 介绍
由于很多小伙伴需要在非 jfinal 环境中独立使用 ActiveRecordPlugin，所以将该插件抽取成独立的项目

## Maven 坐标
```
<dependency>
    <groupId>com.jfinal</groupId>
    <artifactId>activerecord</artifactId>
    <version>5.0.4</version>
</dependency>
```

## 使用示例

```
public class ActiveRecordDemo {

    static String jdbcUrl = "jdbc:mysql://localhost/jfinal_demo?useSSL=false&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    static String user = "root";
    static String password = "yourpassword";

    public static DruidPlugin createDruidPlugin() {
        DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, user, password);
        return druidPlugin;
    }

    public static void initActiveRecordPlugin() {
        DruidPlugin druidPlugin = createDruidPlugin();

        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setDevMode(true);
        arp.setShowSql(true);

        // 添加 sql 模板文件，实际开发时将 sql 文件放在 src/main/resources 下
        arp.addSqlTemplate("com/jfinal/plugin/activerecord/test.sql");

        // 所有映射在生成的 _MappingKit.java 中自动化搞定
        _MappingKit.mapping(arp);

        // 先启动 druidPlugin，后启动 arp
        druidPlugin.start();
        arp.start();
    }

    public static void main(String[] args) {
        initActiveRecordPlugin();
        
        // 使用 Model
        Blog dao = new Blog().dao();
        Blog blog = dao.template("findBlog", 1).findFirst();
        System.out.println(blog.getTitle());
        
        // 使用 Db + Record 模式
        Record record = Db.template("findBlog", 1).findFirst();
        System.out.println(record.getStr("title"));
    }
}
```

注意初始化时需要手动调用 druidPlugin.start()、arp.start() 来启动组件。

以上代码可在项目的 test 包下直接获取： [ActiveRecordDemo.java](https://gitee.com/jfinal/activerecord/blob/master/src/test/java/com/jfinal/plugin/activerecord/ActiveRecordDemo.java)

## 获取生成器代码
[_Generator.java](https://gitee.com/jfinal/activerecord/blob/master/src/test/java/com/jfinal/plugin/activerecord/_Generator.java)

## 更多用法
jfinal ActiveRecordPlugin 的用法与在 jfinal 中的使用方式完全样：
更多用法见官方文档： [jfinal.com](https://www.jfinal.com/doc/5-1)






