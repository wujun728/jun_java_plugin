# Spring Boot & Jackson 对于日期时间类型处理的例子

本文使用的是Spring Boot 1.5.4.RELEASE、Spring 4.3.9.RELEASE、Jackson 2.8.8、Joda 2.9.9

Spring Boot利用JacksonAutoConfiguration自动配置了ObjectMapper，并且在Spring MVC中全局性的使用了它，这样开发者就能够很方便实现Json序列化、反序列化。

不过Jackson对于日期时间类型的行为存在一些坑，一不小心就可能发生无法反序列化以及序列化的结果不符合预期，所以本文罗列了一些例子，帮助开发者获得正确的结果。

* [Chapter 1：不使用@JsonFormat序列化、反序列化日期时间类型][src-No_JsonFormat.md]
* [Chapter 2：使用@JsonFormat的序列化与反序列化日期时间类型][src-JsonFormat.md]
* [Chapter 3：不使用@JsonFormat序列化、反序列化携带时区信息的日期时间类型][src-No_JsonFormat_Zone.md]
* [Chapter 4：使用@JsonFormat的序列化与反序列化携带时区信息的日期时间类型][src-JsonFormat_Zone.md]

## 重要事项

在写本文的时候发现了一些Jackson和时区有关的Bug，见这里：

* [issue 92][github-issue-jackson-datatype-joda-92]
* [issue 93][github-issue-jackson-datatype-joda-93]

要等到确认后再完善本文，所以所有和`java.time.ZonedDateTime`、`org.joda.time.DateTime`相关的结果不能算数。
  
[src-No_JsonFormat.md]: No_JsonFormat.md
[src-No_JsonFormat_Zone.md]: No_JsonFormat_Zone.md
[src-JsonFormat.md]: JsonFormat.md
[src-JsonFormat_Zone.md]: JsonFormat_Zone.md
[github-issue-jackson-datatype-joda-92]: https://github.com/FasterXML/jackson-datatype-joda/issues/92
[github-issue-jackson-datatype-joda-93]: https://github.com/FasterXML/jackson-datatype-joda/issues/93
