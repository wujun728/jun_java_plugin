
##声明

本工具纯粹写来自用, 并没有与别人竞争的意思, 欢迎大神们给我这个渣渣提提建议, 不喜欢本项目的也不要喷我

##前言

我是一个曾纠结springboot还是jfinal的渣渣程序员

jfinal的orm超级爽, 但是controller并不是十分方便而且官方并不支持注解.

jfinal-ext支持注解了但是写起单元测试来十分麻烦. jfinal也不支持restful

oscgit上也有数个基于jfinal的restful框架,但是没有在官方支持下感觉十分奇怪,

比如取PathParam要getAttr(),这样就感觉很奇怪了.

最终我败在了Spring的大生态下, 虽然Spring库有点大了, 但是总是有用的.

SpringBoot的简单配置实在让我非常心动, 加上SpringMVC强大又稳定, 所以我最终选择了SpringBoot

但是SpringBoot自带的JPA写起一些多表查询,动态查询实在会死人, 所以我决定写一个基于JDBC类似JFinal的ORM框架(其实只算是封装好的工具吧)

##x-orm简介

跟JFinal一样有Model和Db+Record两种方式, 不过我在Model上加上了注解,这样配置就更加少了.

在注册Record的时候实在比不上波总的JFinal..小弟才疏学浅.感觉在服务启动的性能上比JFinal差多了

功能还在慢慢完善, 不废话了, 有兴趣的小伙伴来试试顺便给个星

##配置
x-orm是基于SpringBoot+Jdbc的 Maven就依赖这几个东西就好了
```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.3.6.RELEASE</version>
  </parent>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
```



配置上面就非常简单了, 在SpringBoot的服务启动类加上两个注册的语句~如果不需要Record的就不需要填了

```
@Controller
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.xdivo")
public class SpringBootStarter {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootStarter.class, args);
        Register.registerModel("com.xdivo.model"); //扫描的包名
        Register.registerRecord("online_class"); //数据库名
        Register.initTheadPool(100, 100, 1000); //初始化线程池 0为使用默认值
    }
}

```

定义Model
```
/**
 * 用户类
 * Created by liujunjie on 16-7-19.
 */
@Entity(table = "c_user")
public class model.User extends Model<model.User> {

    @PK
    @Column(name = "id_")
    private long id;

    @Column(name = "mobile_")
    private String mobile;

    @Column(name = "password_")
    private String password;

    @Join(refColumn = "id")
    @Column(name = "room_id_")
    private Room room;

    @Join(refColumn = "id_") //关联的refColumn的值为数据库的关联列
    @Column(name = "student_id_")
    private Student student;

    public long getId() {
        return id;
    }

    public model.User setId(long id) {
        this.id = id;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public model.User setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public model.User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public model.User setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Student getStudent() {
        return student;
    }

    public model.User setStudent(Student student) {
        this.student = student;
        return this;
    }
}
```

在使用的时候就跟JFinal基本一样了
```
//保存User对象
new model.User().setMobile("abc")
      .setPassword("123")
      .save();

//根据主键查询User
model.User user = new model.User().findById(id);

//获取关联对象

//异步保存到数据
user.asyncSave();

//异步更新到数据
user.asyncUpdate();
```

```
//查询record
Record record = Db.findById("c_user", 23);

//转换到Model(转换到Model后直接使用getter就能获取关联Model)
model.User user = record.toModel(model.User.class);

//保存Record对象
Record record = new Record()
    .set("mobile_", "abc")
    .set("password_", "123");
Db.save("c_user", record);
```

```
//直接使用JdbcTemplate增加自定义查询 并转换成Model
Map<String, Object> resultMap = jdbcTemplate.queryForMap("SELECT * FROM user WHERE id = ?", 1);
model.User user = new model.User().mapping(resultMap);

List<Map<String, Object>> resultList = jdbcTemplate.queryForList("SELECT * FROM user");
List<model.User> users = new model.User().mappingList(resultList);

```

```
/**
     * 瀑布流分页(暂时只支持Number类型的列)
     *
     * @param orderColName  排序列名
     * @param orderColValue 排序列值
     * @param direction     方向
     * @param params        参数
     * @param pageSize      每页数量
     * @return ScrollResult
     */
    public ScrollResult scroll(String orderColName, Number orderColValue, String direction, Map<String, Object> params, int pageSize)


    //滚动分页方法
    ScrollResult result = user.scroll("id", id, Model.Direction.DESC, null, 2);

```

像事务那些东西就是基于SpringBoot了.省了一笔功夫



##联系方式
###QQ: 41369927
###邮箱: 41369927@qq.com
