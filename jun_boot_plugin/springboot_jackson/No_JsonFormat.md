# 不使用@JsonFormat序列化、反序列化日期时间类型

下面所有例子：

* 测试了以下日期时间类型：
  1. 老的Java``java.util.Date``
  1. Java 8的``java.time.LocalDate``
  1. Java 8的``java.time.LocalTime``
  1. Java 8的``java.time.LocalDateTime``
  1. Java 8的``java.time.ZonedDateTime``
  1. Joda的``org.joda.time.LocalDate``
  1. Joda的``org.joda.time.LocalTime``
  1. Joda的``org.joda.time.LocalDateTime``
  1. Joda的``org.joda.time.DateTime``
* 拿序列化结果反序列化，查看是否结果正确
* 没有使用``@JsonFormat``，也就是意味着使用的是全局配置

## 例子1: 全默认配置

源代码在[NoJsonFormat1Test][src-NoJsonFormat1Test]，结果是：

| 类型                              | 序列化结果              | 反序列化结果        |
|:--------------------------------:|:----------------------:|:-----------------:|
|``java.util.Date``                |1483203661000           |Ok                 |
|``java.time.LocalDate``           |[2017,1,1]              |Ok                 |
|``java.time.LocalTime``           |[1,1,1]                 |Ok                 |
|``java.time.LocalDateTime``       |1483203661000           |Ok                 |
|``java.time.ZonedDateTime``       |1483203661.000000000    |expected [2017-01-01T01:01:01+08:00[Asia/Shanghai]] but found [2016-12-31T17:01:01Z[UTC]]             |
|``org.joda.time.LocalDate``       |[2017,1,1]              |Ok                 |
|``org.joda.time.LocalTime``       |[1,1,1,0]               |Ok                 |
|``org.joda.time.LocalDateTime``   |[2017,1,1,1,1,1,0]      |Ok                 |
|``org.joda.time.DateTime``        |1483203661000           |expected [2017-01-01T01:01:01.000+08:00] but found [2016-12-31T17:01:01.000Z]             |


在这个例子里我们可以看到两个问题：

1. 日期的序列化结果不是timestamp数字，就是数组，这个对于前端开发人员来讲很不友好。
1. 因为序列化结果缺少时区，拿这个结果反序列化得到的结果都变成了0时区，也就是格林尼治时区，虽然从逻辑上等价，但是时间表达不一致。

## 例子2: 配置spring.jackson.date-format

添加配置：

* ``spring.jackson.date-format=yyyy-MM-dd HH:mm:ss``

源代码在[NoJsonFormat2Test][src-NoJsonFormat2Test]，结果是：

| 类型                              | 序列化结果                      | 反序列化结果        |
|:--------------------------------:|:------------------------------:|:-----------------:|
|``java.util.Date``                |"2016-12-31 17:01:01"           |Ok                 |
|``java.time.LocalDate``           |"2017-01-01"                    |Ok                 |
|``java.time.LocalTime``           |"01:01:01"                      |Ok                 |
|``java.time.LocalDateTime``       |"2017-01-01T01:01:01"           |Ok                 |
|``java.time.ZonedDateTime``       |"2017-01-01T01:01:01+08:00"     |expected [2017-01-01T01:01:01+08:00[Asia/Shanghai]] but found [2016-12-31T17:01:01Z[UTC]]             |
|``org.joda.time.LocalDate``       |"2017-01-01"                    |Ok                 |
|``org.joda.time.LocalTime``       |"01:01:01.000"                  |Ok                 |
|``org.joda.time.LocalDateTime``   |"2017-01-01T01:01:01.000"       |Ok                 |
|``org.joda.time.DateTime``        |"2016-12-31 17:01:01"           |Invalid format: "2016-12-31 17:01:01" is malformed at " 17:01:01"             |


在这个例子里我们可以看到一个现象，就是输出结果都是human-readable了，但是却发现了更多问题：

1. ``java.util.Date``和``org.joda.time.DateTime``的序列化结果都转成了格林尼治时间，即往前推移了8小时（因为中国的时区是东8区）
1. ``java.time.LocalDateTime``、``java.time.ZonedDateTime``、``org.joda.time.LocalTime``、``org.joda.time.LocalDateTime``的序列化结果和配置不一致
1. ``java.time.ZonedDateTime``反序列化失败，原因依然时区
1. ``org.joda.time.DateTime``反序列化失败，原因是parse失败

## 例子3: 配置spring.jackson.date-format、spring.jackson.joda-date-time-format

添加配置：

* ``spring.jackson.date-format=yyyy-MM-dd HH:mm:ss``
* ``spring.jackson.joda-date-time-format=yyyy-MM-dd HH:mm:ss``

源代码在[NoJsonFormat3Test][src-NoJsonFormat3Test]，结果是：

| 类型                              | 序列化结果                      | 反序列化结果        |
|:--------------------------------:|:------------------------------:|:-----------------:|
|``java.util.Date``                |"2016-12-31 17:01:01"           |Ok                 |
|``java.time.LocalDate``           |"2017-01-01"                    |Ok                 |
|``java.time.LocalTime``           |"01:01:01"                      |Ok                 |
|``java.time.LocalDateTime``       |"2017-01-01T01:01:01"           |Ok                 |
|``java.time.ZonedDateTime``       |"2017-01-01T01:01:01+08:00"     |expected [2017-01-01T01:01:01+08:00[Asia/Shanghai]] but found [2016-12-31T17:01:01Z[UTC]]             |
|``org.joda.time.LocalDate``       |"2017-01-01"                    |Ok                 |
|``org.joda.time.LocalTime``       |"01:01:01.000"                  |Ok                 |
|``org.joda.time.LocalDateTime``   |"2017-01-01T01:01:01.000"       |Ok                 |
|``org.joda.time.DateTime``        |"2016-12-31 17:01:01"           |Invalid format: "2016-12-31 17:01:01" is malformed at " 17:01:01"             |


在本例和例子2的结果是一样的。

## 例子4：配置jackson的一些feature

添加配置：

* ``spring.jackson.serialization.write_dates_as_timestamps=false``
* ``spring.jackson.serialization.write_dates_with_zone_id=true``
* ``spring.jackson.deserialization.adjust_dates_to_context_time_zone=false``

源代码在[NoJsonFormat4Test][src-NoJsonFormat4Test]，结果是：

| 类型                              | 序列化结果                                     | 反序列化结果        |
|:--------------------------------:|:---------------------------------------------:|:-----------------:|
|``java.util.Date``                |"2016-12-31T17:01:01.000+0000"                 |Ok                 |
|``java.time.LocalDate``           |"2017-01-01"                                   |Ok                 |
|``java.time.LocalTime``           |"01:01:01"                                     |Ok                 |
|``java.time.LocalDateTime``       |"2017-01-01T01:01:01"                          |Ok                 |
|``java.time.ZonedDateTime``       |"2017-01-01T01:01:01+08:00[Asia/Shanghai]"     |Ok                 |
|``org.joda.time.LocalDate``       |"2017-01-01"                                   |Ok                 |
|``org.joda.time.LocalTime``       |"01:01:01.000"                                 |Ok                 |
|``org.joda.time.LocalDateTime``   |"2017-01-01T01:01:01.000"                      |Ok                 |
|``org.joda.time.DateTime``        |"2017-01-01T01:01:01.000+08:00[Asia/Shanghai]" |Ok                 |

这个例子里，所有的序列化和反序列化都成功了，达成了以下效果：

1. ``java.util.Date``转换成了格林尼治时间
1. 序列化结果human-readable
1. 序列化结果保留了时区信息，而不是定位到了格林尼治时间
1. 拿序列化结果反序列化也成功了

## 例子5：spring.jackson.date-format + jackson的一些feature 

添加配置：

* ``spring.jackson.date-format=yyyy-MM-dd HH:mm:ss``
* ``spring.jackson.serialization.write_dates_as_timestamps=false``
* ``spring.jackson.serialization.write_dates_with_zone_id=true``
* ``spring.jackson.deserialization.adjust_dates_to_context_time_zone=false``

源代码在[NoJsonFormat5Test][src-NoJsonFormat5Test]，结果是：

| 类型                              | 序列化结果                                     | 反序列化结果        |
|:--------------------------------:|:---------------------------------------------:|:-----------------:|
|``java.util.Date``                |"2016-12-31 17:01:01"                          |Ok                 |
|``java.time.LocalDate``           |"2017-01-01"                                   |Ok                 |
|``java.time.LocalTime``           |"01:01:01"                                     |Ok                 |
|``java.time.LocalDateTime``       |"2017-01-01T01:01:01"                          |Ok                 |
|``java.time.ZonedDateTime``       |"2017-01-01T01:01:01+08:00[Asia/Shanghai]"     |Ok                 |
|``org.joda.time.LocalDate``       |"2017-01-01"                                   |Ok                 |
|``org.joda.time.LocalTime``       |"01:01:01.000"                                 |Ok                 |
|``org.joda.time.LocalDateTime``   |"2017-01-01T01:01:01.000"                      |Ok                 |
|``org.joda.time.DateTime``        |"2017-01-01 01:01:01[Asia/Shanghai]"           |Invalid format: "2017-01-01 01:01:01" is malformed at " 01:01:01"             |


在这个例子里我们可以发现：

1. ``java.util.Date``的序列化结果都转成了格林尼治时间，即往前推移了8小时（因为中国的时区是东8区）
1. ``java.time.LocalDateTime``、``java.time.ZonedDateTime``、``org.joda.time.LocalTime``、``org.joda.time.LocalDateTime``、``org.joda.time.DateTime``的序列化结果和配置不一致
1. ``java.time.ZonedDateTime``和``org.joda.time.DateTime``携带了时区信息，虽然格式上没有要求
1. ``org.joda.time.DateTime``反序列化失败，原因是parse失败

## 例子6：配置spring.jackson.date-format、spring.jackson.joda-date-time-format + jackson的一些feature

添加配置：

* ``spring.jackson.date-format=yyyy-MM-dd HH:mm:ss``
* ``spring.jackson.joda-date-time-format=yyyy-MM-dd HH:mm:ss``
* ``spring.jackson.serialization.write_dates_as_timestamps=false``
* ``spring.jackson.serialization.write_dates_with_zone_id=true``
* ``spring.jackson.deserialization.adjust_dates_to_context_time_zone=false``

源代码在[NoJsonFormat6Test][src-NoJsonFormat6Test]，结果是：

| 类型                              | 序列化结果                                     | 反序列化结果        |
|:--------------------------------:|:---------------------------------------------:|:-----------------:|
|``java.util.Date``                |"2016-12-31 17:01:01"                          |Ok                 |
|``java.time.LocalDate``           |"2017-01-01"                                   |Ok                 |
|``java.time.LocalTime``           |"01:01:01"                                     |Ok                 |
|``java.time.LocalDateTime``       |"2017-01-01T01:01:01"                          |Ok                 |
|``java.time.ZonedDateTime``       |"2017-01-01T01:01:01+08:00[Asia/Shanghai]"     |Ok                 |
|``org.joda.time.LocalDate``       |"2017-01-01"                                   |Ok                 |
|``org.joda.time.LocalTime``       |"01:01:01.000"                                 |Ok                 |
|``org.joda.time.LocalDateTime``   |"2017-01-01T01:01:01.000"                      |Ok                 |
|``org.joda.time.DateTime``        |"2017-01-01 01:01:01[Asia/Shanghai]"           |Invalid format: "2017-01-01 01:01:01" is malformed at " 01:01:01"             |


结果和例子5一模一样。

## 例子7: 配置spring.jackson.date-format（format有一些特别）

添加配置：

* ``spring.jackson.date-format=yyyy-MMM-dd HH:mm:ss``

源代码在[NoJsonFormat7Test][src-NoJsonFormat7Test]，结果是：

| 类型                              | 序列化结果                      | 反序列化结果        |
|:--------------------------------:|:------------------------------:|:-----------------:|
|``java.util.Date``                |"2016-十二月-31 17:01:01"        |Ok                 |
|``java.time.LocalDate``           |"2017-01-01"                    |Ok                 |
|``java.time.LocalTime``           |"01:01:01"                      |Ok                 |
|``java.time.LocalDateTime``       |"2017-01-01T01:01:01"           |Ok                 |
|``java.time.ZonedDateTime``       |"2017-01-01T01:01:01+08:00"     |expected [2017-01-01T01:01:01+08:00[Asia/Shanghai]] but found [2016-12-31T17:01:01Z[UTC]]             |
|``org.joda.time.LocalDate``       |"2017-01-01"                    |Ok                 |
|``org.joda.time.LocalTime``       |"01:01:01.000"                  |Ok                 |
|``org.joda.time.LocalDateTime``   |"2017-01-01T01:01:01.000"       |Ok                 |
|``org.joda.time.DateTime``        |"2016-十二月-31 17:01:01"        |Invalid format: "2016-十二月-31 17:01:01" is malformed at "-十二月-31 17:01:01"             |


在这个例子里我们发现：

1. ``java.util.Date``和``org.joda.time.DateTime``的序列化结果都转成了格林尼治时间，即往前推移了8小时（因为中国的时区是东8区）
1. ``java.util.Date``和``org.joda.time.DateTime``都符合配置的格式，其余的则不符合


## 例子8: 配置spring.jackson.date-format、spring.jackson.joda-date-time-format（format有一些特别）

添加配置：

* ``spring.jackson.date-format=yyyy-MMM-dd HH:mm:ss``
* ``spring.jackson.joda-date-time-format=yyyy-MMM-dd'T'HH:mm:ss``

源代码在[NoJsonFormat8Test][src-NoJsonFormat8Test]，结果是：

| 类型                              | 序列化结果                      | 反序列化结果        |
|:--------------------------------:|:------------------------------:|:-----------------:|
|``java.util.Date``                |"2016-十二月-31 17:01:01"        |Ok                 |
|``java.time.LocalDate``           |"2017-01-01"                    |Ok                 |
|``java.time.LocalTime``           |"01:01:01"                      |Ok                 |
|``java.time.LocalDateTime``       |"2017-01-01T01:01:01"           |Ok                 |
|``java.time.ZonedDateTime``       |"2017-01-01T01:01:01+08:00"     |expected [2017-01-01T01:01:01+08:00[Asia/Shanghai]] but found [2016-12-31T17:01:01Z[UTC]]             |
|``org.joda.time.LocalDate``       |"2017-01-01"                    |Ok                 |
|``org.joda.time.LocalTime``       |"01:01:01.000"                  |Ok                 |
|``org.joda.time.LocalDateTime``   |"2017-01-01T01:01:01.000"       |Ok                 |
|``org.joda.time.DateTime``        |"2016-十二月-31T17:01:01"        |Invalid format: "2016-十二月-31T17:01:01" is malformed at "-十二月-31T17:01:01"             |


在这个例子里我们发现：

1. ``java.util.Date``的格式被``spring.jackson.date-format``控制
1. ``org.joda.time.DateTime``的格式被``spring.jackson.joda-date-time-format``控制
1. ``java.util.Date``和``org.joda.time.DateTime``都符合配置的格式，其余的则不符合

## 小结

在不使用@JsonFormat仅仅通过全局配置来控制序列化结果的情况下，有以下几个规律：

* ``spring.jackson.date-format``只控制``java.util.Date``的序列化format
* ``spring.jackson.joda-date-time-format``只控制``org.joda.time.DateTime``的序列化format
* ``spring.jackson.joda-date-time-format``没有配置时，会fallback到``spring.jackson.date-format``（Spring Boot官方文档也是这么说的）
* ``java.util.Date``和``org.joda.time.DateTime``之外的日期时间类型序列化的format无法控制
* ``spring.jackson.serialization.write_dates_as_timestamps``控制日期时间类型的序列化结果是时间戳还是human-readable字符串
* 配置``spring.jackson.date-format``似乎相当于自动配置了``spring.jackson.serialization.write_dates_as_timestamps=false``
* ``spring.jackson.serialization.write_dates_with_zone_id``控制在对携带时区信息的日期时间类型序列化时，是否要追加时区信息
* 不自定义format，使用jackson自己的序列化机制，配合例子4中的配置，能够做到序列化、反序列化行为一致

  
[src-NoJsonFormat1Test]: src/test/java/me/chanjar/no_json_format/NoJsonFormat1Test.java
[src-NoJsonFormat2Test]: src/test/java/me/chanjar/no_json_format/NoJsonFormat2Test.java
[src-NoJsonFormat3Test]: src/test/java/me/chanjar/no_json_format/NoJsonFormat3Test.java
[src-NoJsonFormat4Test]: src/test/java/me/chanjar/no_json_format/NoJsonFormat4Test.java
[src-NoJsonFormat5Test]: src/test/java/me/chanjar/no_json_format/NoJsonFormat5Test.java
[src-NoJsonFormat6Test]: src/test/java/me/chanjar/no_json_format/NoJsonFormat6Test.java
[src-NoJsonFormat7Test]: src/test/java/me/chanjar/no_json_format/NoJsonFormat7Test.java
[src-NoJsonFormat8Test]: src/test/java/me/chanjar/no_json_format/NoJsonFormat8Test.java
[src-NoJsonFormat9Test]: src/test/java/me/chanjar/no_json_format/NoJsonFormat9Test.java
