# json-util
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/yidasanqian/json-util/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/badge/maven--central-1.0.0-blue.svg)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22json-util%22)
[![Jar Size](https://img.shields.io/badge/jar--size-14.07k-blue.svg)](http://repo1.maven.org/maven2/io/github/yidasanqian/json-util/1.0.0/)

Json Util Integrate Jackson、Gson And Fastjson

json-util工具类整合了Jackson、Gson和Fastjson库并统一API。

# Env
- JDK7+
- Maven

# Feature
统一调用API，形如`toXXX`。目前支持的转换的列表：
- List
- Map
- JsonString
- Pojo

List支持泛型，JsonString支持指定日期格式化，支持对象和Map的互相转换。

支持从`application.properties`或`application.yml`中设置解析Json的库(jackson, gson, fastjson)。

application.properties:
```
json.class.type=jackson
```
application.yml:
```
json:
  class-type: gson
```

支持自动查找用户引入的Json库(jackson, gson, fastjson)用来解析Json

# Usage
## Maven Dependency
```
<properties>
    <jackson.version>2.9.0</jackson.version>
    <gson.version>2.8.2</gson.version>
    <fastjson.version>1.2.44</fastjson.version>
</properties>
<dependencies>
    <dependency>
        <groupId>io.github.yidasanqian</groupId>
        <artifactId>json-util</artifactId>
        <version>1.0.0</version>
    </dependency>
    <!-- use jackson lib-->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>${jackson.version}</version>          
    </dependency>
     <!-- or use gson lib-->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>${gson.version}</version>     
    </dependency>
     <!-- or use fastjson lib-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>${fastjson.version}</version>      
    </dependency>
</dependencies>
```

## Example 

默认从`application.properties`或`application.yml`中读取配置的Json库，

若未配置则自动查找用户引入的Json库(jackson, gson, fastjson)，若存在多个则默认使用Jackson作为json解析库。


**解析到`List`**
```
 String json = "[1, 2, 4, 5]";
 List result = JsonUtil.toList(json);
 
 ...
 json = "[{"id": 1,"username": "yidasanqian"},{"id": 2,"username": "yidasanqian2"}]"
 TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
 List<User> result = JsonUtil.toList(json, typeReference.getType());
```

**解析到`Map`**
```
String json = "{
                 "id": 1,
                 "username": "yidasanqian",
                 "address": {
                   "id": 1,
                   "city": "杭州"
                 }
               }";
Map result = JsonUtil.toMap(json);
```

**解析到`String`**
```
User user = new User();
user.setId(1);
user.setUsername("yidasanqian");
String result = JsonUtil.toJsonString(user);
```

**解析到指定对象**
```
String json = "{
                 "id": 1,
                 "username": "yidasanqian"
               }";
User user = JsonUtil.toPojo(json, User.class);
```

更多API请查看[JavaDoc-1.0.0.zip](https://github.com/yidasanqian/json-util/releases/download/v1.0.0/JavaDoc.zip)