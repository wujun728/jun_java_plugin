[查看原文](https://github.com/FasterXML/jackson/blob/master/README.md)
## 什么是Jackson ##
众所周知Jackson是一个Java（或者说JVM平台）的JSON转换的工具。其实它还可以支持JSON数据流的转换或生成JSON数据流，从流中读取数据并转换成POJO。另外它还支持其它多种格式的数据序列化转换，比如Avro,BSON,CBOR,CSV,Smile,(Java)Properties,Protobuf,XML和YAML。甚至还支持被广泛使用的Guava, Joda, PCollections等数据类型。

Jackson由如下工程组成：三个核心包 (streaming, databind, annotations); 数据格式化 data format libraries; 数据类型 data type libraries; [JAX-RS provider](https://github.com/FasterXML/jackson-jaxrs-providers); 和各种各样的扩展模块；

更多的信息可以在[jackson-wiki](https://github.com/FasterXML/jackson/wiki)查看。

##Jackson的版本说明  
Jackson有两个主要分支:1.x处于维护模式，只发布bug修复版本;2.x是活跃的开发版本。这两个主要版本使用不同的Java包和Maven artifact ids，因此它们不是相互兼容的，但是可以和平地共存:项目可以同时依赖Jackson 1.x和2.x,没有冲突。

最新的稳定版本为：

* [2.9.5](../../wiki/Jackson-Release-2.9), released on 28-3-2018.
* [1.9.13](../../wiki/JacksonRelease1.9), released 14-7-2013

推荐的使用Jackson的方法是通过Maven中央仓库的发布版本。个别项目的wiki页面也提供指向maven中央仓库的下载地址。
发布版本的相关信息可以在[Jackson Releases](../../wiki/Jackson-Releases)找到。

## Active Jackson项目 ##

下面列出的大多数项目是由Jackson开发团队主导；但是也有一些项目是由Jackson社区的其他团队主导。

### 核心模块

核心模块是其他扩展模块构建的基础。Jackson2.x版本有3个核心模块：

* [Streaming](../../../jackson-core) ([docs](https://github.com/FasterXML/jackson-core/wiki)) ("jackson-core") 定义了一些底层的“流API”, 和一些JSON专用的实现
* [Annotations](../../../jackson-annotations) ([docs](../../../jackson-annotations/wiki)) ("jackson-annotations") 包含了标准的Jackson注解
* [Databind](../../../jackson-databind) ([docs](../../../jackson-databind/wiki)) ("jackson-databind") 实现了流数据包的数据绑定和对象序列化；它依赖了 `streaming` 和 `annotations`。

### 第三方数据类型模块 ###
即[jackson-datatypes-collections](https://github.com/FasterXML/jackson-datatypes-collections)

它是Jackson的插件模块（注册方式：`ObjectMapper.registerModule()`）,并添加了对JAVA库常用数据类型的支持，通过(`ObjectMapper` / `ObjectReader` / `ObjectWriter`)来对这些数据类型进行序列化和反序列化。


*jackson-datatypes-collections*同样也是由Jackson团队维护，它包括：

* 标准的第三方[集合数据类型](https://github.com/FasterXML/jackson-datatypes-collections)模块:
    * [Guava](https://github.com/google/guava): 支持各种Guava数据类型。Guava是一个被很多谷歌项目使用的核心库
    * [HPPC](https://github.com/carrotsearch/hppc): 支持HPPC(High Performance Primitive Collections),是指原始的高性能Java集合库。
    * [PCollections](http://pcollections.org/): support for [PCollections](http://pcollections.org/) datatypes (NEW in Jackson 2.7!)。PCollections是一个持久化的不可变的JAVA集合框架。
* [Hibernate](https://github.com/FasterXML/jackson-datatype-hibernate): support for Hibernate features (lazy-loading, proxies)
* [Joda](https://github.com/FasterXML/jackson-datatype-joda): support for types of [Joda](http://joda-time.sourceforge.net/) date/time library datatypes
* [JDK7](https://github.com/FasterXML/jackson-datatype-jdk7): support for JDK 7 data types not included in previous versions
    * 在2.7版本后合并到`jackson-databind`
* [Java 8 Modules](https://github.com/FasterXML/jackson-modules-java8): support or JDK 8 features and datatypes through 3 separate modules
    * `jackson-module-parameter-names`: Module that adds support for using a new JDK8 feature, ability to access names of constructor and method parameters, to allow omitting `@JsonProperty`.
    * `jackson-datatype-jsr310`: support for "Java 8 Dates" (ones added in JDK 8)
        * Also, for pre-Java8 users can use one of alternate pre-Java8 backports:
            * [joschi/jackson-datatype-threetenbp](https://github.com/joschi/jackson-datatype-threetenbp)
            * [lldata/jackson-datatype-threetenbp](https://github.com/lldata/jackson-datatype-threetenbp)
    * `jackson-datatype-jdk8`: support for JDK 8 data types other than date/time types, including `Optional`
* [JSR-353](../../../jackson-datatype-jsr353): support for "Java JSON API" types (specifically, its tree model objects)
* [org.json](../../../jackson-datatype-json-org): support for "org.json JSON lib" types like `JSONObject`, `JSONArray`

另外，还有一些附加的modules，它们不是由Jackson核心团队维护的：

* [jackson-datatype-bolts](https://github.com/v1ctor/jackson-datatype-bolts) support for reading/writing types defined by [Yandex Bolts](https://bitbucket.org/stepancheg/bolts/wiki/Home) collection types (Functional Programming inspired immutable collections)
* [jackson-datatype-commons-lang3](https://github.com/bramp/jackson-datatype-commons-lang3) for types of [Apache Commons Lang v3](https://commons.apache.org/proper/commons-lang/)(当前只支持org.apache.commons.lang3.math.Fraction数据类型，Fraction是指“分数、小数”)
* [jackson-datatype-money](https://github.com/zalando/jackson-datatype-money) for "Java Money", see [javax.money](http://javamoney.github.io/api.html)
* [javaslang-jackson](https://github.com/javaslang/javaslang-jackson) for [Javaslang](https://github.com/javaslang/javaslang) support (Feature-rich & self-contained functional programming in Java™ 8 and above)
* [jackson-datatype-json-lib](https://github.com/swquinn/jackson-datatype-json-lib) for supporting types defined by "net.sf.json" library (aka "json-lib")
* [jackson-datatype-jts](https://github.com/bedatadriven/jackson-datatype-jts) (JTS Geometry) for [GeoJSON](https://en.wikipedia.org/wiki/GeoJSON) support
* [jackson-lombok](https://github.com/xebia/jackson-lombok) for better support of [Lombok](http://projectlombok.org/) classes
* [jackson-datatype-mongo](https://github.com/commercehub-oss/jackson-datatype-mongo) for MongoDB types
    * NOTE: there are a few alternatives to handling MongoDB datatypes
* [jackson-module-objectify](https://github.com/tburch/jackson-module-objectify) for datatypes of [Objectify](https://github.com/objectify/objectify)
* [jackson-datatype-protobuf](https://github.com/HubSpot/jackson-datatype-protobuf) for handling datatypes defined by the standard Java protobuf library, developed by [HubSpot](http://www.hubspot.com/)
    * NOTE! This is different from `jackson-dataformat-protobuf` which adds support for encoding/decoding protobuf content but which does NOT depend on standard Java protobuf library
* [TinyTypes](https://github.com/caligin/tinytypes) includes Jackson module (group id `com.github.caligin.tinytypes`, artifact `tinytypes-jackson`)
* [jackson-datatype-vertx](https://github.com/Crunc/jackson-datatype-vertx) for reading/writing [Vert.x](http://vertx.io/) `org.vertx.java.core.json.JsonObject` objects (repackaged `org.json` node types)

### Providers for JAX-RS
JAX-RS是JAVA EE6 引入的一个新技术。 JAX-RS即Java API for RESTful Web Services，是一个Java 编程语言的应用程序接口，支持按照表述性状态转移（REST）架构风格创建Web服务。JAX-RS使用了Java SE5引入的Java注解来简化Web服务的客户端和服务端的开发和部署。
包括：
@Path，标注资源类或者方法的相对路径
@GET，@PUT，@POST，@DELETE，标注方法是HTTP请求的类型。
@Produces，标注返回的MIME媒体类型
@Consumes，标注可接受请求的MIME媒体类型
@PathParam，@QueryParam，@HeaderParam，@CookieParam，@MatrixParam，@FormParam,分别标注方法的参数来自于HTTP请求的不同位置，例如@PathParam来自于URL的路径，@QueryParam来自于URL的查询参数，@HeaderParam来自于HTTP请求的头信息，@CookieParam来自于HTTP请求的Cookie。

[Jackson JAX-RS Providers](https://github.com/FasterXML/jackson-jaxrs-providers) has handlers to add dataformat
support for JAX-RS implementations (like Jersey, RESTeasy, CXF).
Providers implement `MessageBodyReader` and `MessageBodyWriter`.
Supported formats currently include `JSON`, `Smile`, `XML`, `YAML` and `CBOR`.

### Data format modules

Data format modules 提供了JSON以为的数据格式。
Most of them simply implement `streaming` API abstractions, so that databinding component can be used as is; some offer (and few require) additional `databind` level functionality for handling things like schemas.

Currently following data format modules are fully usable and supported (version number in parenthesis, if included, is the
first Jackson 2.x version to include module; if missing, included from 2.0)

* [Avro](../../../jackson-dataformat-avro): supports [Avro](http://en.wikipedia.org/wiki/Apache_Avro) data format, with `streaming` implementation plus additional `databind`-level support for Avro Schemas
* [CBOR](../../../jackson-dataformat-cbor): supports [CBOR](http://tools.ietf.org/search/rfc7049) data format (a binary JSON variant).
* [CSV](../../../jackson-dataformat-csv): supports [Comma-separated values](http://en.wikipedia.org/wiki/Comma-separated_values) format -- `streaming` api, with optional convenience `databind` additions
* [Ion](../../../jackson-dataformats-binary/tree/master/ion) (NEW with Jackson 2.9!): support for [Amazon Ion](https://amznlabs.github.io/ion-docs/) binary data format (similar to CBOR, Smile, i.e. binary JSON - like)
* [(Java) Properties](../../../jackson-dataformat-properties) (2.8): creating nested structure out of implied notation (dotted by default, configurable), flattening similarly on serialization
* [Protobuf](../../../jackson-dataformat-protobuf) (2.6): supported similar to `Avro`
* [Smile](../../../jackson-dataformat-smile): supports [Smile (binary JSON)](http://wiki.fasterxml.com/SmileFormatSpec) -- 100% API/logical model compatible via `streaming` API, no changes for `databind`
* [XML](../../../jackson-dataformat-xml): supports XML; provides both `streaming` and `databind` implementations. Similar to JAXB' "code-first" mode (no support for "XML Schema first", but can use JAXB beans)
* [YAML](../../../jackson-dataformat-yaml): supports [YAML](http://en.wikipedia.org/wiki/Yaml), which being similar to JSON is fully supported with simple `streaming` implementation


There are also other data format modules, provided by developers outside Jackson core team:

* [BEncode](https://github.com/zsoltm/jackson-dataformat-bencode): support for reading/writing [BEncode](https://en.wikipedia.org/wiki/Bencode) (BitTorrent format) encoded data
* [bson4jackson](https://github.com/michel-kraemer/bson4jackson): adds support for [BSON](http://en.wikipedia.org/wiki/BSON) data format (by Mongo project).
    * Implemented as full streaming implementation, which allows full access (streaming, data-binding, tree-model)
    * Also see [MongoJack] library below; while not a dataformat module, it allows access to BSON data as well.
* [EXIficient](https://github.com/EXIficient/exificient-for-json) supports [Efficient XML Interchange](https://en.wikipedia.org/wiki/Efficient_XML_Interchange)
* [jackson-dataformat-msgpack](https://github.com/msgpack/msgpack-java/tree/develop/msgpack-jackson) adds support [MessagePack](http://en.wikipedia.org/wiki/MessagePack) (aka `MsgPack`) format
    * Implemented as full streaming implementation, which allows full access (streaming, data-binding, tree-model)
* [HOCON](https://github.com/jclawson/jackson-dataformat-hocon): experimental, partial implementation to support [HOCON](https://github.com/typesafehub/config) format -- work in progress
* [Rison](https://github.com/Hronom/jackson-dataformat-rison): Jackson backend to support [Rison](https://github.com/Nanonid/rison))

### JVM Language modules

* [Kotlin](https://github.com/FasterXML/jackson-module-kotlin) to handle native types of [Kotlin](http://kotlinlang.org/)
* [Scala](https://github.com/FasterXML/jackson-module-scala) to handle native Scala types (including but not limited to Scala collection/map types, case classes)
    * Currently (July 2017) Scala 2.10, 2.11 and 2.12 are supported (2.9 was supported up to Jackson 2.3)

### Support for Schemas

Jackson annotations define intended properties and expected handling for POJOs, and in addition to Jackson itself
using this for reading/writing JSON and other formats, it also allows generation of external schemas.
Some of this functionality is included in above-mentioned data-format extensions; but there are also
many stand-alone Schema tools, such as:

#### JSON Schema

* [Ant Task for JSON Schema Generation](https://github.com/xdarklight/jackson-jsonschema-ant-task): Generate JSON Schemas from your Java classes with Apache Ant using the Jackson library and extension modules.
* [JSON Schema generator module](../../../jackson-module-jsonSchema): programmatically generate JSON Schema, based on Jackson POJO introspection, including annotations
* [Maven plug-in](../../../jackson-schema-maven-plugin) for JSON Schema generation (based on JSON Schema module)

#### Other schema languages

* [Ember Schema Generator](../../../../marcus-nl/ember-schema-generator): Generate schemas for [Ember.js](https://github.com/emberjs/ember.js)

### Other modules, stable

Other fully usable modules by FasterXML team include:

* [Base](../../../jackson-modules-base) modules:
    * [Afterburner](../../../jackson-modules-base/tree/master/afterburner): speed up databinding by 30-40% with bytecode generation to replace use of Reflection for field access, method/constructor calls
    * [Guice](../../../jackson-modules-base/tree/master/guice): extension that allows injection values from Guice injectors (and basic Guice annotations), instead of standard `@JacksonInject` (or in addition to)
    * [JAXB Annotations](../../../jackson-modules-base/tree/master/jackson-module-jaxb-annotations): allow use of `JAXB` annotations as an alternative (in addition to or instead of) standard Jackson annotations
    * [Mr Bean](../../../jackson-modules-base/tree/master/mrbean): "type materialization" -- let Mr Bean generate implementation classes on-the-fly (NO source code generation), to avoid monkey code
    * [OSGi](../../../jackson-modules-base/tree/master/osgi): allows injection of values from OSGi registry, via standard Jackson `@JacksonInject` annotation
    * [Paranamer](../../../jackson-modules-base/tree/master/paranamer): tiny extension for automatically figuring out creator (constructor, factory method) parameter names, to avoid having to specify `@JsonProperty`.

### Jackson jr
jackson-jr是一个更轻量级的工具包，它仅仅依赖jackson-streaming，不需要jackson-databind。它的内存、cpu开销更小。它的jar包更小，API更加精简。

### Third-party non-module libraries based on Jackson

#### Jackson helper libraries

* [Jackson Ant path filter](https://github.com/Antibrumm/jackson-antpathfilter) adds powerful filtering of properties to serialize, using Ant Path notation for hierarchic filtering

#### Support for datatypes

* [MongoJack](http://mongojack.org/) supports efficient handling of [BSON](http://en.wikipedia.org/wiki/BSON) encoded data store in [MongoDB](http://en.wikipedia.org/wiki/MongoDB).

## Participation

The easiest ways to participate beyond using Jackson is to join one of Jackson mailing lists (Jackson google groups):

* [Jackson Announce](https://groups.google.com/forum/#!forum/jackson-announce): Announcement-only list for new Jackson releases, meetups and other events related to Jackson
* [Jackson model.User](https://groups.google.com/forum/#!forum/jackson-user): List dedicated for discussion on Jackson usage
* [Jackson Dev](https://groups.google.com/forum/#!forum/jackson-dev): List for developers of Jackson core components and modules, discussing implementation details, API changes.

There are other related lists and forums as well:

* [Smile Format Discussion](https://groups.google.com/forum/#!forum/smile-format-discussion): List for discussing details of the binary JSON format called [Smile](https://en.wikipedia.org/wiki/Smile_%28data_interchange_format%29) (see [Smile Specification](http://wiki.fasterxml.com/SmileFormat))
* [Jackson Users](http://jackson-users.ning.com) is a Jackson-specific discussion forum for usage questions.

### Other things related to or inspired by Jackson

* [Pyckson](https://github.com/antidot/Pyckson) is a Python library that aims for same goals as Java Jackson, such as Convention over Configuration
* [Rackson](https://github.com/griffindy/rackson) is a Ruby library that offers Jackson-like functionality on Ruby platform

## Documentation

### Web sites

* [jackson-docs](../../../jackson-docs) is our Github Jackson documentation hub
* [Jackson Wiki](http://wiki.fasterxml.com/JacksonHome) contains older documentation (some 1.x specific; but mostly relevant for both 1.x and 2.x)
* [CowTalk](http://cowtowncoder.com/blog/blog.html) -- Blog with lots of Jackson-specific content

### Note on reporting Bugs

Jackson bugs need to be reported against component they affect: for this reason, issue tracker
is not enabled for this project.
If you are unsure which specific project issue affects, the most likely component
is `jackson-databind`, so you would use
[Jackson Databind Issue Tracker](https://github.com/FasterXML/jackson-databind/issues).

### Paperwork

* Contributor License Agreement, needed by core team to accept contributions. There are 2 options:
    * Standard Jackson [Contributor License Agreement](../../blob/master/contributor-agreement.pdf) (CLA) is a one-page document we need from every contributor of code (we will request it for pull requests), used mostly by individual contributors
    * [Corporate CLA](../../blob/master/contributor-agreement-corporate.txt) is used by Corporations to avoid individual employees from having to send separate CLAs; it is also favored by corporate IP lawyers.

Note that the first option is available for corporations as well, but most companies have opted to use the second option instead. Core team has no preference over which one gets used; both work; we care more about actual contributions.

### Java JSON library comparisons

Since you probably want opinions by Java developers NOT related to Jackson project, regarding which library to use,
here are links to some of existing independent comparisons:

* [Top 7 Open-Source JSON-binding providers](http://www.developer.com/lang/jscript/top-7-open-source-json-binding-providers-available-today.html) (April 2014)
* [Be a Lazy but a Productive Android Developer, Part 3: JSON Parsing Library](http://java.dzone.com/articles/be-lazy-productive-android) (April 2014)
* ["Can anyone recommend a good Java JSON library"](https://www.linkedin.com/groups/Can-anyone-recommend-good-Java-50472.S.226644043) (Linked-In group) (March 2013)
* ["Which JSON library to use on Android?"](http://thetarah.com/2012/09/21/which-json-library-should-i-use-in-my-android-and-java-projects/) (September 2012) 