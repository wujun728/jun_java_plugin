# easy-excel-utils
基于阿里easyExcel包装的工具类，poi版本3.17，主要简化easyExcel的web代码，增加一部分样式的定制操作
当前使用版本2.2.5
原本的使用中无法定制样式策略，后续版本已可以更改样式策略，未引入，有需要可查阅官方文档自行引入
https://www.yuque.com/easyexcel/doc/write#W4u1e

## 模块介绍
- easy-excel-util #工具类依赖#
- my-base-common #测试使用的其他依赖#
- my-web #测试使用的web工程#

## 启动介绍


my-web基于SpringBoot ，默认不使用外置tomcat容器，使用外置容器时将WebServletInitializerConf的注释打开，并且将打包方式修改为war


调用AdminApplication中的Main方法启动内部tomcat容器

## 导出代码位置及调用示例
my-web中 EasyExcelUtilController 

````
//单sheet输出
ExcelUtil.writeExcel(response, list, "导出测试", "sheet1", ExcelTypeEnum.XLSX);
//多sheet输出
ExcelUtil.writeExcel(response, "导出测试", ExcelTypeEnum.XLSX, list, list2);
//或
ExcelUtil.writeExcel(response, "导出测试", ExcelTypeEnum.XLSX, list, list2, list3);
````

## 导入
my-web中 EasyExcelUtilController 

````
//单sheet导入，只取第一个sheet信息
List<ExportTestModel> list = ExcelUtil.readFirstSheetExcel(files.get(0), ExportTestModel.class);
//多sheet导入
List[] result = ExcelUtil.readExcel(files.get(0), ExportTestModel.class, ExportTestModelSheet2.class);
````

## SheetName
导出sheet名称注解

````
@SheetName("sheet名称")
@Data
public class ExportTestModel {
    @ExcelProperty(index = 0 ,value = "标题")
    private String title;
}
````

##