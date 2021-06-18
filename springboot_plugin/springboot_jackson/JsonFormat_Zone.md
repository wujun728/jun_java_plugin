# 使用@JsonFormat的序列化与反序列化携带时区信息的日期时间类型

下面所有例子：

* 测试了以下日期时间类型：
  1. Java 8的``java.time.ZonedDateTime``
  1. Joda的``org.joda.time.LocalDateTime``
* 拿序列化结果反序列化，查看是否结果正确
* 使用``@JsonFormat``

TODO  

[javadoc-java8-DateTimeFormatter]: https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
[javadoc-joda-DateTimeFormat]: http://www.joda.org/joda-time/apidocs/org/joda/time/format/DateTimeFormat.html
[javadoc-joda-DateTimeFormatter]: http://www.joda.org/joda-time/apidocs/org/joda/time/format/DateTimeFormatter.html

[src-joda-DateTimeDeserializer]: https://github.com/FasterXML/jackson-datatype-joda/blob/jackson-datatype-joda-2.8.8/src/main/java/com/fasterxml/jackson/datatype/joda/deser/DateTimeDeserializer.java
[src-java8-InstantDeserializer]: https://github.com/FasterXML/jackson-datatype-jsr310/blob/jackson-datatype-jsr310-2.8.4/src/main/java/com/fasterxml/jackson/datatype/jsr310/deser/InstantDeserializer.java
