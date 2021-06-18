# 使用@JsonFormat的序列化与反序列化日期时间类型

下面所有例子：

* 测试了以下日期时间类型：
  1. 老的Java``java.util.Date``
  1. Java 8的``java.time.LocalDate``
  1. Java 8的``java.time.LocalTime``
  1. Java 8的``java.time.LocalDateTime``
  1. Joda的``org.joda.time.LocalDate``
  1. Joda的``org.joda.time.LocalTime``
  1. Joda的``org.joda.time.LocalDateTime``
* 拿序列化结果反序列化，查看是否结果正确
* 使用``@JsonFormat``

## 例子1: 全默认配置

源代码在[JsonFormat1Test][src-JsonFormat1Test]，结果是：

| 类型                              | 序列化结果                | 反序列化结果        |
|:--------------------------------:|:------------------------:|:-----------------:|
|``java.util.Date``                |"2016-十二月-31 17:01:01"  |Ok                 |
|``java.time.LocalDate``           |"2017-一月-01"             |Ok                 |
|``java.time.LocalTime``           |"01:01:01"                |Ok                 |
|``java.time.LocalDateTime``       |"2017-一月-01 01:01:01"    |Ok                 |
|``org.joda.time.LocalDate``       |[2017,1,1]                 |Ok                 |
|``org.joda.time.LocalTime``       |[1,1,1,0]                  |Ok                 |
|``org.joda.time.LocalDateTime``   |[2017,1,1,1,1,1,0]         |Ok                 |


在这个例子里我们可以看到Joda的`LocalDate`、`LocalTime`、`LocalDateTime`都不受``@JsonFormat``控制。

## 例子2: 配置spring.jackson.serialization.write_dates_as_timestamps

添加配置：

* ``spring.jackson.serialization.write_dates_as_timestamps=false``

源代码在[JsonFormat2Test][src-JsonFormat2Test]，结果是：

| 类型                              | 序列化结果                | 反序列化结果        |
|:--------------------------------:|:------------------------:|:-----------------:|
|``java.util.Date``                |"2016-十二月-31 17:01:01"  |Ok                 |
|``java.time.LocalDate``           |"2017-一月-01"             |Ok                 |
|``java.time.LocalTime``           |"01:01:01"                |Ok                 |
|``java.time.LocalDateTime``       |"2017-一月-01 01:01:01"    |Ok                 |
|``org.joda.time.LocalDate``       |"2017-一月-01"             |Ok                 |
|``org.joda.time.LocalTime``       |"01:01:01"                |Ok                 |
|``org.joda.time.LocalDateTime``   |"2017-一月-01 01:01:01"    |Ok                 |


在这个例子里我们可以添加了这个配置后，Joda的`LocalDate`、`LocalTime`、`LocalDateTime`序列化受到了``@JsonFormat``控制，得到了我们期望的结果。

## 例子3: 配置spring.jackson.date-format

添加配置：

* ``spring.jackson.date-format=yyyy-MM-dd HH:mm:ss``

源代码在[JsonFormat3Test][src-JsonFormat3Test]，结果是：

| 类型                              | 序列化结果                | 反序列化结果        |
|:--------------------------------:|:------------------------:|:-----------------:|
|``java.util.Date``                |"2016-十二月-31 17:01:01"  |Ok                 |
|``java.time.LocalDate``           |"2017-一月-01"             |Ok                 |
|``java.time.LocalTime``           |"01:01:01"                |Ok                 |
|``java.time.LocalDateTime``       |"2017-一月-01 01:01:01"    |Ok                 |
|``org.joda.time.LocalDate``       |"2017-一月-01"             |Ok                 |
|``org.joda.time.LocalTime``       |"01:01:01"                |Ok                 |
|``org.joda.time.LocalDateTime``   |"2017-一月-01 01:01:01"    |Ok                 |


结果和例子2一样，在Chapter 1里我们讲过，配置``spring.jackson.date-format=yyyy-MM-dd HH:mm:ss``就相当于自动配置了``spring.jackson.serialization.write_dates_as_timestamps=false``。

而且我们也可以发现，``@JsonFormat``里定义的format会覆盖全局配置的format。
  
[src-JsonFormat1Test]: src/test/java/me/chanjar/json_format/JsonFormat1Test.java
[src-JsonFormat2Test]: src/test/java/me/chanjar/json_format/JsonFormat2Test.java
