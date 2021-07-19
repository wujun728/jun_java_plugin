> 相关源码： [spring cloud demo](http://git.oschina.net/buxiaoxia/spring-demo "样例")

Spring Data Rest 是基于 Spring Data repositories，分析实体之间的关系。为我们生成Hypermedia API([HATEOAS](https://en.wikipedia.org/wiki/HATEOAS "HATEOAS"))风格的Http Restful API接口。

[Spring Data Rest 官方首页](http://projects.spring.io/spring-data-rest/ "Spring Data Rest 官方首页")中提到了它所具有的特性，比如：
* 根据model，生成HAL风格的restful API
* 根据model，维护实体之间的关系
* 支持分页
...

诸多的特性，[官方文档](http://docs.spring.io/spring-data/rest/docs/2.6.3.RELEASE/reference/html/ "官方文档")都会有提及。这里我们着重关注在Spring Data Rest中基于JPA维护实体之间关系。

## 资源实体的关系

![实体关系E-R图](http://upload-images.jianshu.io/upload_images/4742055-78c9e13412dc9e6c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* 一个用户（user）拥有一个身份证（card）
* 一个用户（user）拥有多辆车（car）
* 一个用户（user）拥有多门语音（language）
* 多门语言（language）拥有多个用户（user）

> 关系不用在意是否合理，只是为了涵盖几个基本的关系

### one to one 关系

#### 数据实体

```
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private Date createAt;

    @OneToOne(mappedBy = "user")
    private Card card;
}

```

```
@Data
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String card_num;

    private Date createAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    @RestResource(path = "user", rel = "user")
    private User user;

}

```

#### Repository结构

```
public interface UserRepository extends JpaRepository<User, Long> {
}
```
```
public interface CardRepository extends JpaRepository<Card, Long> {
}
```

通过以上的代码，Spring Data Rest 就已经足够帮我们维护其用户（user）和身份证（card）二者的关系，并且提供了HAL的接口。就是这么方便！下面，我们使用HAL Browser ，可以更加方便的在浏览器中查看接口，以及他们之间的关系。

#### HAL Browser 使用

[HAL-browser](https://github.com/mikekelly/hal-browser "HAL-browser") 是基于hal+json的media type的API浏览器，Spring Data Rest 提供了集成，pom文件中加个这个。
```
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-rest-hal-browser</artifactId>
</dependency>
```
启动我们的程序，打开浏览器 http://127.0.0.1:8081/api/v1/browser/index.html#/api/v1

> api/v1 是我指定的接口前缀，通过配置项 *spring.data.rest.base-path* 指定

可以看到如下界面


![hal-browser](http://upload-images.jianshu.io/upload_images/4742055-d34847e6269b2c45.PNG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

具体的使用这里不再赘述，可以自己点点或者看看[这个](https://github.com/mikekelly/hal-browser "HAL-browser")。

#### 接口调用

##### 新增一个user

      curl -i -X POST -H "Content-Type:application/json" -d "{\"name\":\"Lucy\",\"age\":25}" http://127.0.0.1:8081/api/v1/users

结果返回：  
![新增用户](http://upload-images.jianshu.io/upload_images/4742055-93a25bc3838c0443.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


返回结果的状态码是201
返回实体内容
返回_links资源，分别指向自己和对应的card资源的URI

##### 新增一个card

      curl -i -X POST -H "Content-Type:application/json" -d "{\"cardNum\":\"num1\"}" http://127.0.0.1:8081/api/v1/cards


![新增card](http://upload-images.jianshu.io/upload_images/4742055-8d076b7654062774.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 建立关系

创建的两个实体之后，我们需要建立起二者的关系。在Spring Data Rest中，二者的关系绑定，是通过URI来维护，用PUT请求动作。

      curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://127.0.0.1:8081/api/v1/users/1" http://127.0.0.1:8081/api/v1/cards/1/user

也可以用这样维护

      curl -i -X PUT -H "Content-Type:text/uri-list" -d "1" http://127.0.0.1:8081/api/v1/cards/1/user

或者：

      curl -i -X PUT -H "Content-Type:text/uri-list" -d "api/v1/users/1" http://127.0.0.1:8081/api/v1/cards/1/user

三者是等价的操作。

如果创建成功，将会返回响应码204，如下图：

![关系创建](http://upload-images.jianshu.io/upload_images/4742055-80ceb38c35f2e097.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我们来核实下user下的card

      curl -i -X GET http://127.0.0.1:8081/api/v1/users/1/card

返回如下：


![获取user的card](http://upload-images.jianshu.io/upload_images/4742055-6c297ff29ac46871.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

上图可以看出，user-card的关系维护成功！

> 以上是通过PUT Card资源的User来维护二者关系。外键在于Card上，是资源维护方。反过来就通过User资源的Card维护是不被允许的。这个是我的一个疑问，没有深入研究过，一个tip

### one to many 关系

#### 数据实体

```
@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String carNum;

    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
```

```
@Data
@Entity
public class User {

    // ...

    @OneToMany(mappedBy = "user")
    private List<Car> cars = new ArrayList<>();

   // ...
}
```
#### Repository结构

```
public interface CarRepository extends JpaRepository<Car, Long> {
}
```

#### 接口调用

##### 新增一个car

      curl -i -X POST -H "Content-Type:application/json" -d "{\"carNum\":\"A1001\"}" http://127.0.0.1:8081/api/v1/cars

返回如下：

![](http://upload-images.jianshu.io/upload_images/4742055-73920e195cc05d43.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 建立关系

一对多的关系中，也是通过URI通过PUT请求维护关系。

      curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://127.0.0.1:8081/api/v1/users/1" http://127.0.0.1:8081/api/v1/cars/1/user 

结果返回：


![](http://upload-images.jianshu.io/upload_images/4742055-e3af9ae7b40804eb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我们看下User下的Cars

      curl -i -X GET http://127.0.0.1:8081/api/v1/users/1/cars

结果如下：

![](http://upload-images.jianshu.io/upload_images/4742055-050bcee65974931d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

很明显，已经可以看到User下的Cars。

> 也是只有通过多的一方维护关系

### many to many 关系

#### 数据实体

```
@Data
@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "rel_user_language",
            joinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false))
    private List<User> users = new ArrayList<>();

}
```
```
@Data
@Entity
public class User {
    // ...

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Language> languages = new ArrayList<>();

    //...
}
```

#### Repository结构

```
public interface LanguageRepository extends JpaRepository<Language, Long> {
}
```

#### 接口调用

同样新增方式，多门语言，多个用户

      curl -i -X POST -H "Content-Type:application/json" -d "{\"name\":\"eng\"}" http://127.0.0.1:8081/api/v1/languages

      curl -i -X POST -H "Content-Type:application/json" -d "{\"name\":\"chs\"}" http://127.0.0.1:8081/api/v1/languages

      curl -i -X POST -H "Content-Type:application/json" -d "{\"name\":\"jp\"}" http://127.0.0.1:8081/api/v1/languages

      curl -i -X POST -H "Content-Type:application/json" -d "{\"name\":\"Jack\",\"age\":25}" http://127.0.0.1:8081/api/v1/users

#### 创建关系

多对多的关系创建方式比之前两种更为丰富。

##### PUT方式添加关系

      curl -i -X PUT -H "Content-Type:text/uri-list" -d "api/v1/users/1" http://127.0.0.1:8081/api/v1/languages/1/users

##### POST方式添加关系

      curl -i -X POST -H "Content-Type:text/uri-list" -d "api/v1/users/1" http://127.0.0.1:8081/api/v1/languages/2/usersrs

##### PATCH方式添加关系

      curl -i -X PATCH -H "Content-Type:text/uri-list" -d "api/v1/users/1" http://127.0.0.1:8081/api/v1/languages/3/users

---

以上三种方式都可以用于创建多对多的关系，可以查看下：

      curl -i -X GET http://127.0.0.1:8081/api/v1/users/1/languages

```
{
  "_embedded" : {
    "languages" : [ {
      "name" : "eng",
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:8081/api/v1/languages/1"
        },
        "language" : {
          "href" : "http://127.0.0.1:8081/api/v1/languages/1"
        },
        "users" : {
          "href" : "http://127.0.0.1:8081/api/v1/languages/1/users"
        }
      }
    }, {
      "name" : "chs",
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:8081/api/v1/languages/2"
        },
        "language" : {
          "href" : "http://127.0.0.1:8081/api/v1/languages/2"
        },
        "users" : {
          "href" : "http://127.0.0.1:8081/api/v1/languages/2/users"
        }
      }
    }, {
      "name" : "jp",
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:8081/api/v1/languages/3"
        },
        "language" : {
          "href" : "http://127.0.0.1:8081/api/v1/languages/3"
        },
        "users" : {
          "href" : "http://127.0.0.1:8081/api/v1/languages/3/users"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:8081/api/v1/users/1/languages"
    }
  }
}
```

结果上是可以看出，三种方式的结果都成功了。

---

反过来，通过语言查看用户

      curl -i -X GET http://127.0.0.1:8081/api/v1/languages/3/users

```
{
  "_embedded" : {
    "users" : [ {
      "name" : "Lucy",
      "age" : 25,
      "createAt" : null,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/1"
        },
        "user" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/1"
        },
        "card" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/1/card"
        },
        "languages" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/1/languages"
        },
        "cars" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/1/cars"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:8081/api/v1/languages/3/users"
    }
  }
}
```

结果也是我们所想要的。

#### PUT POST PATCH 三者之间区别

PATCH 请求的作用等同于 POST 请求，而他们二者有点不同于PUT请求。大家都知道 PUT 请求是整体替换，而PATCH是局部更新。在Spring Data Rest 中 PATCH 表示添加，而不是覆盖，PUT请求是完全覆盖。

---

我们在原来的数据基础上给User2(Jack)添加一门Language:

      curl -i -X PATCH -H "Content-Type:text/uri-list" -d "api/v1/users/2" http://127.0.0.1:8081/api/v1/languages/1/users

之后，我们查看下 id 为1的 language 的users

      curl -i -X GET http://127.0.0.1:8081/api/v1/languages/1/users

```
{
  "_embedded" : {
    "users" : [ {
      "name" : "Lucy",
      "age" : 25,
      "createAt" : null,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/1"
        },
        "user" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/1"
        },
        "card" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/1/card"
        },
        "languages" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/1/languages"
        },
        "cars" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/1/cars"
        }
      }
    }, {
      "name" : "Jack",
      "age" : 25,
      "createAt" : null,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/2"
        },
        "user" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/2"
        },
        "card" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/2/card"
        },
        "languages" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/2/languages"
        },
        "cars" : {
          "href" : "http://127.0.0.1:8081/api/v1/users/2/cars"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:8081/api/v1/languages/1/users"
    }
  }
}
```

可以看到拥有两个user。这里可以看出 PATCH 的作用添加了一个item。

接下来，我们再调用 PUT 请求，更新下。

      curl -i -X PUT -H "Content-Type:text/uri-list" -d "api/v1/users/2" http://127.0.0.1:8081/api/v1/languages/1/users

> 与上一个请求的唯一区别是用了PUT做更新

再GET下User，结果如下：

```
{                                                                                        
  "_embedded" : {                                                                        
    "users" : [ {                                                                        
      "name" : "Jack",                                                                   
      "age" : 25,                                                                        
      "createAt" : null,                                                                 
      "_links" : {                                                                       
        "self" : {                                                                       
          "href" : "http://127.0.0.1:8081/api/v1/users/2"                                
        },                                                                               
        "user" : {                                                                       
          "href" : "http://127.0.0.1:8081/api/v1/users/2"                                
        },                                                                               
        "card" : {                                                                       
          "href" : "http://127.0.0.1:8081/api/v1/users/2/card"                           
        },                                                                               
        "languages" : {                                                                  
          "href" : "http://127.0.0.1:8081/api/v1/users/2/languages"                      
        },                                                                               
        "cars" : {                                                                       
          "href" : "http://127.0.0.1:8081/api/v1/users/2/cars"                           
        }                                                                                
      }                                                                                  
    } ]                                                                                  
  },                                                                                     
  "_links" : {                                                                           
    "self" : {                                                                           
      "href" : "http://127.0.0.1:8081/api/v1/languages/1/users"                          
    }                                                                                    
  }                                                                                      
}                                                                                        
```

对比很明显，PUT请求覆盖了之前的数据，只留下了一个Jack的user关联。这就是PUT 和 PATCH 的区别。

> tip1:POST的效果同PATCH 不做多说明。

> tip2:多对多的关系维护中，维护方的资源来维护二者关系。


## Spring Data Rest Events

Spring Data Rest Events 提供了AOP方式的开发，定义了8种不同事件。

* 资源保存前
* 资源保存后
* 资源更新前
* 资源更新后
* 资源删除前
* 资源删除后
* 关系创建前
* 关系插件后

不同的事件触发的场景不同，我们可以自定义这些事件内容来完成我们的业务。这个以后再说...有兴趣可以看看我的[样例代码](http://git.oschina.net/buxiaoxia/spring-demo "样例")