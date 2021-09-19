# Jackson-databind概述
Jackson-databind包含了通用的json数据处理的数据绑定的功能与tree模型。它依赖于jackson-core与jackson-annotations，jackson-core提供底层的数据处理抽象，jackson-annotations则是用于配置具体的java对象的序列化规则。  

-----

# 如何使用

## 通过Maven进行依赖引入

在pom.xml文件中增加如下依赖:

```xml  
<properties> 
  ...
  <!-- Use the latest version whenever possible. -->
  <jackson.version>2.9.5</jackson.version>
  ...
</properties>

<dependencies>
  ...
  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>${jackson.version}</version>
  </dependency>
  ...
</dependencies>
```

由于jackson-databind依赖了 `jackson-core` 和 `jackson-annotations` ，加入你没有使用Maven，你需要另外下载这两个jar包。为了确保使用兼容的版本，你也可以显试依赖它们：  

```xml
<dependencies>
  ...
  <dependency>
    <!-- Note: core-annotations version x.y.0 is generally compatible with
         (identical to) version x.y.1, x.y.2, etc. -->
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
    <version>${jackson.version}</version>
  </dependency>
  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>${jackson.version}</version>
  </dependency>
  ...
<dependencies>
```

请注意：只有在依赖关系传递过程中存在版本冲突时才显试引入这两个依赖，否则没有必要。

## 当不使用Maven时

加入没有使用Maven,可以从下面的链接直接下载jar包： [Central Maven repository](http://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/).

Databind jar也是一个功能性OSGi包，具有正确的导入/导出声明，因此可以在OSGi容器上使用。

-----

# 使用方法

更详细的文档请参考 [Jackson-docs](https://github.com/FasterXML/jackson-docs);
下面是一个简单的入门：

## 1 分钟介绍JAVA-POJO对象与JSON的互转

最常见的使用场景是从一个JSON转换成一个JAVA-POJO对象。加入我们有一个拥有2个属性的简单JAVA对象：


    public class MyValue {
    
    	private String name;
    
    	private int age;
    
    	public String getName() {
	    	return name;
	    }
    
    	public void setName(String name) {
    		this.name = name;
    	}
    
    	public int getAge() {
    		return age;
    	}
    
    	public void setAge(int age) {
    		this.age = age;
    	}
    }


然后，需要一个`com.fasterxml.jackson.databind.ObjectMapper` 对象实例用于数据绑定：  


    ObjectMapper mapper = new ObjectMapper(); // create once, reuse


默认的构造函数创建的实例已经可以很好的满足当前的使用场景，稍后再介绍如何配置以满足特别的需求。使用方法非常简单：


    MyValue value = mapper.readValue(new File("data.json"), MyValue.class);
    // or:
    value = mapper.readValue(new URL("http://some.com/api/entry.json"), MyValue.class);
    // or:
    value = mapper.readValue("{\"name\":\"Bob\", \"age\":13}", MyValue.class);


将对象转换成JSON:


    mapper.writeValue(new File("result.json"), myResultObject);
    // or:
    byte[] jsonBytes = mapper.writeValueAsBytes(myResultObject);
    // or:
    String jsonString = mapper.writeValueAsString(myResultObject);
    

到目前为止，是不是感觉良好呢？

## 3 分钟介绍: 集合、树模型的转换

除了处理简单的POJO对象，也可以处理JDK中的 `List`s, `Map`等集合对象:

    Map<String, Integer> map = new HashMap<>();
    map.put("张三", 30);
    map.put("李四", 32);
    map.put("王五", 35);
    
    String json = mapper.writeValueAsString(map);
    
    log("Map对象转成JSON： " + json);
    
    map = mapper.readValue(json, Map.class);
    log("JSON对象转成Map对象： " + map);
    
    
    List<String> names = new ArrayList<>();
    names.add("张三");
    names.add("李四");
    names.add("王五");
    
    json = mapper.writeValueAsString(names);
    log("List对象转JSON： " + json);
    
    names = mapper.readValue(json, List.class);
    log("JSON转成LIST对象： " + names);


当集合中的数据类型是简单的（）数据类型时这样处理没什么问题，但是如果集合中的数据类型是其他的Object类型是会有点麻烦。
此时[Tree model](https://github.com/FasterXML/jackson-databind/wiki/JacksonTreeModel)派上了用场：  

 	//对象的转换
    MyValue value = new MyValue();
    value.setAge(30);
    value.setName("张三");
    String json = mapper.writeValueAsString(value);
    
    JsonNode jsonNode = mapper.readTree(json);
    String name = jsonNode.get("name").asText();
    int age = jsonNode.get("age").asInt();
    log("name: " + name);
    log("age: " + age);
    
    jsonNode.with("other");
    json = mapper.writeValueAsString(jsonNode);
    log("value to json: " + json);
    
    
    //集合的转换
    List<MyValue> list = new ArrayList<>();
    list.add(value);
    value = new MyValue();
    value.setName("李四");
    value.setAge(35);
    list.add(value);
    json = mapper.writeValueAsString(list);
    log("pojoList对象转换成json: " + json);
    
    //JSON转成List
    ArrayNode arrayNode = mapper.readValue(json, ArrayNode.class);
    list = new ArrayList<>();
    int size = arrayNode.size();
    for (int i=0; i<size; i++) {
    JsonNode node = arrayNode.get(i);
    MyValue v = new MyValue();
    v.setAge(node.get("age").asInt());
    v.setName(node.get("name").asText());
    list.add(v);
    }
    log("JSON转成pojoList对象： " + list);

Tree Model可以比数据绑定更方便，特别是在结构高度动态的情况下，或者不能很好地映射到Java类的情况下。

## 5分钟介绍: 数据流的转换

相对于上面的处理方式，当json较大时，并且涉及到IO读写时，为了达到更高的性能，你应该使用流的方式来进行JSON的转换。

更深入了解JSON流的转换需要理解“Jackson-Core”。
下面是简单的示例：  


    JsonFactory f = mapper.getFactory(); // 也可以直接创建JsonFactory对象
    
    // 首先，向文件写入一个简单的JSON
    File jsonFile = new File("test.json");
    JsonGenerator g = f.createGenerator(jsonFile);
    // 写入 JSON: { "message" : "Hello world!" }
    g.writeStartObject();
    g.writeStringField("message", "Hello world!");
    g.writeEndObject();
    g.close();
    
    // 然后: 从文件读JSON内容
    JsonParser p = f.createParser(jsonFile);
    
    JsonToken t = p.nextToken(); // 也可以使用 JsonToken.START_OBJECT
    t = p.nextToken(); // JsonToken.FIELD_NAME
    if ((t != JsonToken.FIELD_NAME) || !"message".equals(p.getCurrentName())) {
       // handle error
    }
    t = p.nextToken();
    if (t != JsonToken.VALUE_STRING) {
       // 类似的处理方式
    }
    String msg = p.getText();
    System.out.printf("My message to you is: %s!\n", msg);
    p.close();

## 10分钟了解Jackson的配置

有两种配置方式:
[Features](https://github.com/FasterXML/jackson-databind/wiki/JacksonFeatures) 和 [Annotations](https://github.com/FasterXML/jackson-annotations).

### 通用的公共的配置使用 Features

下面是一些常用的特性配置示例。

让我们从高级别的data-binding配置开始：

    // SerializationFeature for changing how JSON is written
    
    // 使用标准缩进，传说中的 ("pretty-printing"):
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    // 允许序列化空对象(没有序列化的属性)
    // (如果没有这个配置，当序列化空对象时会出现异常)
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    // to write java.util.Date, Calendar as number (timestamp):
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    
    // 反序列化,将JSON转成POJO的相关配置:
    
    // 防止遇到未知属性时发生异常:
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);     	
    // 允许空字符串 ("") 转成空对象:
    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);//否则会抛出异常
    GeneralEmpty<Point> result = mapper.readerFor(new TypeReference<GeneralEmpty<Point>>() { })
                                       .readValue("{\"value\":\"\"}");
    Point p = result.value;//p为null


另外，你可能要配置更底层的JSON转换的细节：


    // JsonParser.Feature for configuring parsing settings:
    
    // 允许 C/C++ 样式的注释，默认不允许
    final String JSON = "[ /* foo */ 7 ]";
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
    ObjectReader reader = mapper.readerFor(int[].class);
    int[] value = reader.readValue(JSON); //value=7


    // 允许非标准的JSON字段名，例如字段名外没有双引号:
	final JsonFactory f = new JsonFactory();
    f.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    String JSON = "{a: 1, _foo:true, $:\"money!\", \" \":null }";
    //非标准的json: 指字段名没有带双引号；
    JsonParser p = f.createParser(JSON.getBytes());
    JsonToken token = p.nextToken();
    log(token.name());
    while(null != (token = p.nextToken())) {
        log(token.name());
        if (token.name().toString().equals("FIELD_NAME")) {
            log(p.getCurrentName());
        }
    }


    // 支持单引号JSON，如：String JSON = "{ 'a' : 1, \"foobar\": 'b', '_abcde1234':'d', '\"' : '\"\"', '':'' }";
    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    
    // UNICODE字符处理,默认会将UNICODE字符进行转换（JsonGenerator.Feature.ESCAPE_NON_ASCII）
    final String VALUE = "\u5f20\u4e09";
    final String KEY = "\u59d3\u540d";
    final JsonFactory f = new JsonFactory();

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    JsonGenerator g;

    g = f.createGenerator(bytes, JsonEncoding.UTF8);
    g.writeStartObject();
    g.writeFieldName(KEY);
    g.writeString(VALUE);
    g.writeEndObject();
    g.close();
    String json = bytes.toString("UTF-8");
    log(json);//输出： {"姓名":"张三"}


    bytes = new ByteArrayOutputStream();

    g = f.createGenerator(bytes, JsonEncoding.UTF8);
    g.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
    g.writeStartObject();
    g.writeFieldName(KEY);
    g.writeString(VALUE);
    g.writeEndObject();
    g.close();
    json = bytes.toString("UTF-8");
    log(json);//输出： {"\u59D3\u540D":"\u5F20\u4E09"}


更全面的Features配置，请参考 [Jackson Features](https://github.com/FasterXML/jackson-databind/wiki/JacksonFeatures) 。

### Annotations注解配置

最简单的注解使用方法：


    public class MyBean {
       private String _name;
    
       // 如果没有注解，转成的JSON，字段名为"theName", 加注解后字段名为"name":
       @JsonProperty("name")
       public String getTheName() { return _name; }
    
       // 注意，注解只需要加到get和set其中一个方法上;
       // 所以此处注解可以省略
       public void setTheName(String n) { _name = n; }
    }


### Annotations: Ignoring properties

There are two main annotations that can be used to to ignore properties: `@JsonIgnore` for individual properties; and `@JsonIgnoreProperties` for per-class definition

```java
// means that if we see "foo" or "bar" in JSON, they will be quietly skipped
// regardless of whether POJO has such properties
@JsonIgnoreProperties({ "foo", "bar" })
public class MyBean
{
   // will not be written as JSON; nor assigned from JSON:
   @JsonIgnore
   public String internal;

   // no annotation, public field is read/written normally
   public String external;

   @JsonIgnore
   public void setCode(int c) { _code = c; }

   // note: will also be ignored because setter has annotation!
   public int getCode() { return _code; }
}
```

As with renaming, note that annotations are "shared" between matching fields, getters and setters: if only one has `@JsonIgnore`, it affects others.
But it is also possible to use "split" annotations, to for example:

```java
public class ReadButDontWriteProps {
   private String _name;
   @JsonProperty public void setName(String n) { _name = n; }
   @JsonIgnore public String getName() { return _name; }
}
```

in this case, no "name" property would be written out (since 'getter' is ignored); but if "name" property was found from JSON, it would be assigned to POJO property!

For a more complete explanation of all possible ways of ignoring properties when writing out JSON, check ["Filtering properties"](http://www.cowtowncoder.com/blog/archives/2011/02/entry_443.html) article.

### Annotations: using custom constructor

Unlike many other data-binding packages, Jackson does not require you to define "default constructor" (constructor that does not take arguments).
While it will use one if nothing else is available, you can easily define that an argument-taking constructor is used:

```java
public class CtorBean
{
  public final String name;
  public final int age;

  @JsonCreator // constructor can be public, private, whatever
  private CtorBean(@JsonProperty("name") String name,
    @JsonProperty("age") int age)
  {
      this.name = name;
      this.age = age;
  }
}
```

Constructors are especially useful in supporting use of
[Immutable objects](http://www.cowtowncoder.com/blog/archives/2010/08/entry_409.html).

Alternatively, you can also define "factory methods":

```java
public class FactoryBean
{
    // fields etc omitted for brewity

    @JsonCreator
    public static FactoryBean create(@JsonProperty("name") String name) {
      // construct and return an instance
    }
}
```

Note that use of a "creator method" (`@JsonCreator` with `@JsonProperty` annotated arguments) does not preclude use of setters: you
can mix and match properties from constructor/factory method with ones that
are set via setters or directly using fields.

## Tutorial: fancier stuff, conversions

One useful (but not very widely known) feature of Jackson is its ability
to do arbitrary POJO-to-POJO conversions. Conceptually you can think of conversions as sequence of 2 steps: first, writing a POJO as JSON, and second, binding that JSON into another kind of POJO. Implementation just skips actual generation of JSON, and uses more efficient intermediate representation.

Conversions work between any compatible types, and invocation is as simple as:

```java
ResultType result = mapper.convertValue(sourceObject, ResultType.class);
```

and as long as source and result types are compatible -- that is, if to-JSON, from-JSON sequence would succeed -- things will "just work".
But here are couple of potentially useful use cases:

```java
// Convert from List<Integer> to int[]
List<Integer> sourceList = ...;
int[] ints = mapper.convertValue(sourceList, int[].class);
// Convert a POJO into Map!
Map<String,Object> propertyMap = mapper.convertValue(pojoValue, Map.class);
// ... and back
PojoType pojo = mapper.convertValue(propertyMap, PojoType.class);
// decode Base64! (default byte[] representation is base64-encoded String)
String base64 = "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlz";
byte[] binary = mapper.convertValue(base64, byte[].class);
```

Basically, Jackson can work as a replacement for many Apache Commons components, for tasks like base64 encoding/decoding, and handling of "dyna beans" (Maps to/from POJOs).

# Contribute!

We would love to get your contribution, whether it's in form of bug reports, Requests for Enhancement (RFE), documentation, or code patches.
The primary mechanism for all of above is [GitHub Issues system](https://github.com/FasterXML/jackson-databind/issues).

## Basic rules for Code Contributions

There is really just one main rule, which is that to accept any code contribution, we need to get a filled Contributor License Agreement (CLA) from the author. One CLA is enough for any number of contributions, but we need one. Or, rather, companies that use our code want it. It keeps their lawyers less unhappy about Open Source usage.

## Limitation on Dependencies by Core Components

One additional limitation exists for so-called core components (streaming api, jackson-annotations and jackson-databind): no additional dependendies are allowed beyond:

* Core components may rely on any methods included in the supported JDK
    * Minimum JDK version is 1.6 as of Jackson 2.4 and above (1.5 was baseline with 2.3 and earlier)
* Jackson-databind (this package) depends on the other two (annotations, streaming).

This means that anything that has to rely on additional APIs or libraries needs to be built as an extension,
usually a Jackson module.

-----

## Differences from Jackson 1.x

Project contains versions 2.0 and above: source code for last (1.x) release, 1.9, is available at
[Jackson-1](../../../jackson-1) repo.

Main differences compared to 1.x "mapper" jar are:

* Maven build instead of Ant
* Java package is now `com.fasterxml.jackson.databind` (instead of `org.codehaus.jackson.map`)

-----

## Further reading

* [Overall Jackson Docs](../../../jackson-docs)
* [Project wiki page](https://github.com/FasterXML/jackson-databind/wiki)

Related:

* [Core annotations](https://github.com/FasterXML/jackson-annotations) package defines annotations commonly used for configuring databinding details
* [Core parser/generator](https://github.com/FasterXML/jackson-core) package defines low-level incremental/streaming parsers, generators
* [Jackson Project Home](../../../jackson) has links to all modules
* [Jackson Docs](../../../jackson-docs) is project's documentation hub
