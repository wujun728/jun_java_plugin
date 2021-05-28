# 不使用@JsonFormat序列化、反序列化携带时区信息的日期时间类型

在前一章的例子里，有两个携带时区信息的类型``java.time.ZonedDateTime``和``org.joda.time.DateTime``，他们的序列化结果会直接影响到是否能够正确的反序列化。

在下面的例子里：

* 设置了``spring.jackson.serialization.write_dates_as_timestamps=false``
* 没有配置``spring.jackson.date-format``
* 没有配置``spring.jackson.joda-date-time-format``
* 因为``spring.jackson.date-format``或者``spring.jackson.joda-date-time-format``都会影响到``org.joda.time.DateTime``的序列化结果。

## 例子1

配置：

* ``spring.jackson.serialization.write_dates_with_zone_id=false``（spring boot默认）
* ``spring.jackson.deserialization.adjust_dates_to_context_time_zone=true``（spring boot默认）

结果：

| 类型                              | 序列化结果                              | 反序列化结果        |
|:--------------------------------:|:--------------------------------------:|:-----------------:|
|``java.time.ZonedDateTime``       |"2017-01-01T01:01:01+08:00"             |expected [2017-01-01T01:01:01+08:00[Asia/Shanghai]] but found [2016-12-31T17:01:01Z[UTC]]             |
|``org.joda.time.DateTime``        |"2016-12-31T17:01:01.000Z"              |expected [2017-01-01T01:01:01.000+08:00] but found [2016-12-31T17:01:01.000Z]             |

从结果可以看到：

* ``java.time.ZonedDateTime``序列化结果有Zone偏移量，但是没有ZoneID，反序列化之后被调整到了UTC时区
* ``org.joda.time.DateTime``序列化结果没有Zone偏移量（直接被调整到到了UTC时区，Z代表UTC），反序列化之后依然是UTC时区

代码：

* 测试代码见[ZoneDateTimeType1Test][src-ZoneDateTimeType1Test]。
* ``java.time.ZonedDateTime``使用[DateTimeFormatter.ISO_OFFSET_DATE_TIME][javadoc-DateTimeFormatter.ISO_OFFSET_DATE_TIME]序列化。
代码见[ZonedDateTimeSerializer][src-ZonedDateTimeSerializer]。
* ``org.joda.time.DateTime``使用[ISODateTimeFormat.dateTime][javadoc-ISODateTimeFormat.dateTime]序列化。
代码见[DateTimeSerializer][src-DateTimeSerializer]。


## 例子2

配置：

* ``spring.jackson.serialization.write_dates_with_zone_id=true``
* ``spring.jackson.deserialization.adjust_dates_to_context_time_zone=true``（spring boot默认）

结果：

| 类型                              | 序列化结果                                       | 反序列化结果        |
|:--------------------------------:|:-----------------------------------------------:|:-----------------:|
|``java.time.ZonedDateTime``       |"2017-01-01T01:01:01+08:00[Asia/Shanghai]"       |expected [2017-01-01T01:01:01+08:00[Asia/Shanghai]] but found [2016-12-31T17:01:01Z[UTC]]  |
|``org.joda.time.DateTime``        |"2017-01-01T01:01:01.000+08:00[Asia/Shanghai]"   |OK                 |

从结果可以看到：

* ``java.time.ZonedDateTime``序列化结果有了Zone偏移量+ZoneID，不过反序列化之后还是被调整到了UTC时区
* ``org.joda.time.DateTime``序列化结果有了Zone偏移量+ZoneID，反序列化结果正常

代码：

* 测试代码见[ZoneDateTimeType2Test][src-ZoneDateTimeType2Test]。
* ``java.time.ZonedDateTime``使用[DateTimeFormatter.ISO_ZONED_DATE_TIME][javadoc-DateTimeFormatter.ISO_ZONED_DATE_TIME]序列化。
代码见[ZonedDateTimeSerializer][src-ZonedDateTimeSerializer]。
* ``org.joda.time.DateTime``使用[ISODateTimeFormat.dateTime][javadoc-ISODateTimeFormat.dateTime] `withOffsetParsed` 再追加`[zoneid]`序列化
代码见[DateTimeSerializer][src-DateTimeSerializer]。所以这里有个坑，就是如果在@JsonFormat里也配置了时区的格式，那么输出的时候，会有重复的时区信息。


## 例子3

配置：

* ``spring.jackson.serialization.write_dates_with_zone_id=true``
* ``spring.jackson.deserialization.adjust_dates_to_context_time_zone=false``

结果：

| 类型                              | 序列化结果                                       | 反序列化结果        |
|:--------------------------------:|:-----------------------------------------------:|:-----------------:|
|``java.time.ZonedDateTime``       |"2017-01-01T01:01:01+08:00[Asia/Shanghai]"       |OK                 |
|``org.joda.time.DateTime``        |"2017-01-01T01:01:01.000+08:00[Asia/Shanghai]"   |OK                 |

从结果可以看到：

* ``java.time.ZonedDateTime``序列化结果有了Zone偏移量+ZoneID，反序列化结果正常
* ``org.joda.time.DateTime``序列化结果有了Zone偏移量+ZoneID，反序列化结果正常

代码：

* 测试代码见[ZoneDateTimeType3Test][src-ZoneDateTimeType3Test]。

## 小结

* 如果想要正确序列化``java.time.ZonedDateTime``和``org.joda.time.DateTime``，给它的数据必须携带时区信息（Zone偏移量+ZoneID）。
* 对于``java.time.ZonedDateTime``来说，必须关闭``spring.jackson.deserialization.adjust_dates_to_context_time_zone``，否则反序列化结果会移动到系统默认时区。
* 总而言之，如果你想要正确序列化``java.time.ZonedDateTime``和``org.joda.time.DateTime``，那么就采用例子3的配置。

  
[javadoc-DateTimeFormatter.ISO_OFFSET_DATE_TIME]: https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_DATE_TIME
[javadoc-DateTimeFormatter.ISO_ZONED_DATE_TIME]: https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_ZONED_DATE_TIME
[javadoc-ISODateTimeFormat.dateTime]: http://www.joda.org/joda-time/apidocs/org/joda/time/format/ISODateTimeFormat.html#dateTime--
[src-DateTimeSerializer]: https://github.com/FasterXML/jackson-datatype-joda/blob/jackson-datatype-joda-2.8.8/src/main/java/com/fasterxml/jackson/datatype/joda/ser/DateTimeSerializer.java
[src-ZonedDateTimeSerializer]: https://github.com/FasterXML/jackson-datatype-jsr310/blob/jackson-datatype-jsr310-2.8.4/src/main/java/com/fasterxml/jackson/datatype/jsr310/ser/ZonedDateTimeSerializer.java
[src-ZoneDateTimeType1Test]: src/test/java/me/chanjar/zonedatetime/ZoneDateTimeType1Test.java
[src-ZoneDateTimeType2Test]: src/test/java/me/chanjar/zonedatetime/ZoneDateTimeType2Test.java
[src-ZoneDateTimeType3Test]: src/test/java/me/chanjar/zonedatetime/ZoneDateTimeType3Test.java
[github-jackson-databind-issue204]: https://github.com/FasterXML/jackson-databind/issues/204
