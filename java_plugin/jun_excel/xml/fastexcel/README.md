fastexcel
=========

作者:peiyu<br>
[我的微博](http://weibo.com/1728407960)<br>
QQ:125331682<br>

快速简单读取excel数据，将Apache POI进行了薄封装，基于注解，更加易于使用<br>

#使用样例
```java
FastExcel fastExcel = new FastExcel("E:/data.xlsx");
        fastExcel.setSheetName("活动信息数据");
        List<MyTest> tests = fastExcel.parse(MyTest.class);
        if(null != tests && !tests.isEmpty()) {
            for(MyTest myTest : tests) {
                LOG.debug("记录:{}", myTest.toString());
            }
        } else {
            LOG.debug("没有结果");
        }
```

映射类

```java
 public class MyTest {

    @MapperCell(cellName = "名称")
    private String name;

    @MapperCell(cellName = "联系电话")
    private String phone;

    @MapperCell(cellName = "地址")
    private String address;

    @MapperCell(cellName = "一级分类ID")
    private int type;

    @MapperCell(cellName = "经度")
    private double lat;

}
```

Maven 项目引入
==========
```xml
<dependency>
    <groupId>com.github.sd4324530</groupId>
    <artifactId>fastexcel</artifactId>
    <version>0.0.4</version>
</dependency>
```

开源协议
==========
[Apache License](http://www.apache.org/licenses/LICENSE-2.0)
