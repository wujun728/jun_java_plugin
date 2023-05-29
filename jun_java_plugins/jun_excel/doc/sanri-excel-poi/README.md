# sanri-excel-poi Excel 导入导出工具

sanri-excel-poi 是一个开源的用于企业开发的 Excel 导入导出，在 2017 年完成第一版，已经在线上环境实验过两年。主要用于解决 Excel 导出麻烦 ，导出慢，及 Excel 导入数据的空行，空格，数字精度，日期转换，Excel 公式 等问题。

博客地址：https://blog.csdn.net/sanri1993/article/details/100601578

javaDoc：https://apidoc.gitee.com/sanri/sanri-excel-poi

-----

## 优势

1. 支持 Excel 公式 
2. 导入的时候空行问题
3. 导入数据去前后空格
4. 继承类也可以导出数据
5. 完美的支持日期格式的导入导出
6. 数字为浮点型的精度处理
7. 完美解决导出时的中文列宽问题
8. 可自定义列的顺序
9. 增加列转化器

## 快速上手

* 引入 maven 依赖

```xml
<dependency>
    <groupId>com.sanri.excel</groupId>
    <artifactId>sanri-excel-poi</artifactId>
    <version>1.0-RELEASE</version>
</dependency>
```

目前需要自己下载代码来构建，或下载已经构建好的 release 包 ：
https://github.com/sanri1993/sanri-excel-poi/releases/tag/v1.0-RELEASE

* 还需要添加第三方依赖，如果项目中已经存在依赖，请忽略。(真实项目一般都是有依赖的)

```xml
<!-- Excel poi  -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>3.10-FINAL</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>3.10-FINAL</version>
</dependency>

<!--apache commons -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.8.1</version>
</dependency>
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.6</version>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-csv</artifactId>
    <version>1.2</version>
</dependency>

<!-- slf4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.21</version>
</dependency>
```

* 最简单的导出示例

```java
// 定义导出实体
@ExcelExport
@Data
public class Simple {
    @ExcelColumn(value = "姓名",order = 0)
    private String name;
}

//获取数据
List<Simple> datas = new ArrayList();
datas.add(new Simple());

//导出Excel 
ExcelExportWriter excelExportWriter = new ExcelExportWriter(Simple.class);
excelExportWriter.export(simpleList);		//这一步只是生成了 Workbook 的数据，还需要用户来决定输出到哪
//写到文件流
excelExportWriter.writeTo(new FileOutputStream("d:/test/"+System.currentTimeMillis()+".xlsx"));
//或写到响应流
excelExportWriter.writeTo(response.getOutputStream());
```

 `order` 属性在导出不存在问题，但如果**使用导入下标必须从 0 开始**



* 最简单的导入示例

```java
// 定义导入实体
@ExcelImport(startRow = 1)
@Data
public class Simple {
    @ExcelColumn(value = "姓名",order = 0)
    private String name;
}

//获取输入流
InputStream fileInputStream = new FileInputStream("simple.xlsx");
//创建错误行处理器，可以使用默认收集处理器
 ErrorRowHandler collectErrorRowHandler = new CollectErrorRowHandler();
 //导入数据
List<Simple> simples = ExcelImportUtil.importData(fileInputStream, Simple.class,collectErrorRowHandler);
```

-----

## 配置你的导入导出

### 注解 @ExcelExport 相关配置

```java
// Excel 导出版本，可选值 ExcelVersion.EXCEL2007,ExcelVersion.EXCEL2003
ExcelVersion version() default ExcelVersion.EXCEL2007;

// 当导出有设置标题时，设置标题行的高度
short titleRowHeight() default 40;

// 头部行的高度，即列说明行的高度 
short headRowHeight() default 30;

// 每一行数据的高度
short bodyRowHeight() default 25;

// 是否自动宽度，Excel 的自动宽度对中文支持不好，我这里对中文宽度做了适应
boolean autoWidth() default true;

// 一个 sheet 页的最大数据量，设置 -1 表示不限制，2003 版本最大 60000 行；超过的行数会另起 sheet 页
int sheetMaxRow() default -1;

// 快速导出模式，使用  new SXSSFWorkbook(rowAccessWindowSize) workbook 对象 
boolean fastModel() default true;

// 快速导出模式的一个设置，一般默认就行
int rowAccessWindowSize() default 1000;

```

### 注解 @ExcelImport 相关配置

```java
// 数据起始行，一般设置 1 就行，从 0 开始
int startRow();

// 支持导入的版本，默认都支持
ExcelVersion[] support() default {ExcelVersion.EXCEL2003,ExcelVersion.EXCEL2007};
```



### 列注解 @ExcelColumn 相关配置

```java
// 导出的中文头部列的值,导入不用管
String value();
// 导入的顺序，默认从 0 开始，导出不用管
int order() default -1;


// 宽度，Excel 宽度单元
int width() default -1;
// 使用字符数来定义宽度，一个中文算一个字符
int charWidth() default -1;
// 使用像素值来定义宽度
int pxWidth() default -1;
// 在使用自动宽度的时候，标记当前列是中文列
boolean chineseWidth() default false;

// 导出是否隐藏当前列
boolean hidden() default false;
// 导入的时候对当前列做字符串前后空格过滤 
boolean trim() default true;
// 导出的时候的单元格类型，可选值有CELL_TYPE_NUMERIC,CELL_TYPE_STRING,CELL_TYPE_BLANK,CELL_TYPE_BOOLEAN
CellType cellType() default CellType.CELL_TYPE_STRING;

// 导入、导出日期格式
String pattern() default "yyyy-MM-dd";
// 导入、导出的数字精度设置,会对浮点型做处理
int precision() default -1;
```



## 更新记录

### 更新于 2019/09/13

- 增加错误行处理器，解决导入有错误行不能处理问题
- 更新测试类