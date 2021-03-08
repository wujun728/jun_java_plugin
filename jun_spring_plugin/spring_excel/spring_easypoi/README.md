JEasyPoi (Excel和 Word简易工具类)
===========================
 EasyPOI 功能如同名字easy，追求的就是简易，让一个没接触过poi的人员，可以傻瓜化的快速实现Excel导入导出、Word模板导出、可以仅仅5行代码就可以完成Excel的导入导出。
	
---------------------------
EasyPoi的主要特点
--------------------------
	1.设计精巧,使用简单
	2.接口丰富,扩展简单
	3.默认值多,write less do more
	4.AbstractView 支持,web导出可以简单明了

---------------------------
EasyPoi的几个入口工具类
---------------------------

	1.ExcelExportUtil Excel导出(普通导出,模板导出)
	2.ExcelImportUtil Excel导入
	3.WordExportUtil  Word导出(只支持docx ,doc版本poi存在图片的bug,暂不支持)
	
---------------------------
关于Excel导出XLS和XLSX区别
---------------------------

	1.导出时间XLS比XLSX快2-3倍
	2.导出大小XLS是XLSX的2-3倍或者更多
	3.导出需要综合网速和本地速度做考虑^~^
	
---------------------------
几个工程的说明
---------------------------
	1.easypoi 父包--作用大家都懂得
	2.easypoi-base 导入导出的工具包,可以完成Excel导出,导入,Word的导出,Excel的导出功能
	3.easypoi-web  耦合了spring-mvc 基于AbstractView,极大的简化spring-mvc下的导出功能
	4.sax 导入使用xercesImpl这个包(这个包可能造成奇怪的问题哈),word导出使用poi-scratchpad,都作为可选包了
--------------------------
maven 
--------------------------

```xml
		<dependency>
			<groupId>org.jeecgframework</groupId>
			<artifactId>jeasypoi-web</artifactId>
			<version>2.2.0</version>
		</dependency>
```
	
---------------------------
EasyPoi 文档
---------------------------
旧的教程：

* [Excel 介绍篇](http://note.youdao.com/share/?id=ea5166076313d437abbfb28a18d85486&type=note)
* [Excel 工具类](http://note.youdao.com/share/?id=dcfef5355d1d75b718be8fd563ad5216&type=note)
* [Excel 注解介绍.第一篇](http://note.youdao.com/share/?id=9898d1777ca97b81d82a6e151b880dbb&type=note)
* [Excel 注解介绍.第二篇](http://note.youdao.com/share/?id=9898d1777ca97b81d82a6e151b880dbb&type=note)
* [Excel 实体类](http://note.youdao.com/share/?id=dab97eababf8e91356f87be204b581e8&type=note)
* [Word模板导出教程](http://note.youdao.com/share/?id=26794c8eb4a285828663178c0ae854a2&type=note)

新的教程：

* [EasyPoi-在财务报表中的应用](http://git.oschina.net/jueyue/easypoi/wikis/EasyPoi-%E5%9C%A8%E8%B4%A2%E5%8A%A1%E6%8A%A5%E8%A1%A8%E4%B8%AD%E7%9A%84%E5%BA%94%E7%94%A8)
* [EasyPoi-如何筛选导出属性](http://git.oschina.net/jueyue/easypoi/wikis/EasyPoi-%E5%A6%82%E4%BD%95%E7%AD%9B%E9%80%89%E5%AF%BC%E5%87%BA%E5%B1%9E%E6%80%A7)
* [EasyPoi 在spring mvc中简易开发方式](http://note.youdao.com/share/?id=8b04c4d88a0574a59aeaffd142c8e34b&type=note)
* [EasyPoi-新版自定义导出样式类型](http://note.youdao.com/share/?id=7937a9fe15f1016b8f39bf813be894f8&type=note)
* [EasyPoi-标签-模板导出的语法介绍](http://blog.csdn.net/qjueyue/article/details/45231801)
* [EasyPoi-Excel预览](http://blog.csdn.net/qjueyue/article/details/45620931)

更多教程：

* [Jeecg在线wiki](http://jeecg3.mydoc.io)
--------------------------
EasyPoi 模板 表达式支持
--------------------------
- 空格分割
- 三目运算  {{test ? obj:obj2}}
- n: 表示 这个cell是数值类型 {{n:}}
- le: 代表长度{{le:()}} 在if/else 运用{{le:() > 8 ? obj1 :  obj2}}
- fd: 格式化时间 {{fd:(obj;yyyy-MM-dd)}}
- fn: 格式化数字 {{fn:(obj;###.00)}}
- fe: 遍历数据,创建row
- !fe: 遍历数据不创建row 
- $fe: 下移插入,把当前行,下面的行全部下移.size()行,然后插入
- !if: 删除当前列 {{!if:(test)}}
- 单引号表示常量值 ''  比如'1' 那么输出的就是 1


---------------------------
EasyPoi导出实例
---------------------------
1.注解,导入导出都是基于注解的,实体上做上注解,标示导出对象,同时可以做一些操作
```Java
	@ExcelTarget("courseEntity")
	public class CourseEntity implements java.io.Serializable {
	/** 主键 */
	private String id;
	/** 课程名称 */
	@Excel(name = "课程名称", orderNum = "1", needMerge = true)
	private String name;
	/** 老师主键 */
	@ExcelEntity(id = "yuwen")
	@ExcelVerify()
	private TeacherEntity teacher;
	/** 老师主键 */
	@ExcelEntity(id = "shuxue")
	private TeacherEntity shuxueteacher;

	@ExcelCollection(name = "选课学生", orderNum = "4")
	private List<StudentEntity> students;
```
2.基础导出
	传入导出参数,导出对象,以及对象列表即可完成导出
```Java
	HSSFWorkbook workbook = ExcelExportUtil.exportExcel(new ExportParams(
				"2412312", "测试", "测试"), CourseEntity.class, list);
```
3.基础导出,带有索引
	在到处参数设置一个值,就可以在导出列增加索引
```Java
	ExportParams params = new ExportParams("2412312", "测试", "测试");
	params.setAddIndex(true);
	HSSFWorkbook workbook = ExcelExportUtil.exportExcel(params,
			TeacherEntity.class, telist);
```			
4.导出Map
	创建类似注解的集合,即可完成Map的导出,略有麻烦
```Java
	List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
	entity.add(new ExcelExportEntity("姓名", "name"));
	entity.add(new ExcelExportEntity("性别", "sex"));

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	Map<String, String> map;
	for (int i = 0; i < 10; i++) {
		map = new HashMap<String, String>();
		map.put("name", "1" + i);
		map.put("sex", "2" + i);
		list.add(map);
	}

	HSSFWorkbook workbook = ExcelExportUtil.exportExcel(new ExportParams(
			"测试", "测试"), entity, list);	
```			
5.模板导出
	根据模板配置,完成对应导出
```Java
	TemplateExportParams params = new TemplateExportParams();
	params.setHeadingRows(2);
	params.setHeadingStartRow(2);
	Map<String,Object> map = new HashMap<String, Object>();
    map.put("year", "2013");
    map.put("sunCourses", list.size());
    Map<String,Object> obj = new HashMap<String, Object>();
    map.put("obj", obj);
    obj.put("name", list.size());
	params.setTemplateUrl("org/jeecgframework/poi/excel/doc/exportTemp.xls");
	Workbook book = ExcelExportUtil.exportExcel(params, CourseEntity.class, list,
			map);
```			
6.导入
	设置导入参数,传入文件或者流,即可获得相应的list
```Java
	ImportParams params = new ImportParams();
	params.setTitleRows(2);
	params.setHeadRows(2);
	//params.setSheetNum(9);
	params.setNeedSave(true);
	long start = new Date().getTime();
	List<CourseEntity> list = ExcelImportUtil.importExcel(new File(
			"d:/tt.xls"), CourseEntity.class, params);
```	

7.和spring mvc的无缝融合
	简单几句话,Excel导出搞定
```Java
	@RequestMapping(params = "exportXls")
	public String exportXls(CourseEntity course,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap map) {

        CriteriaQuery cq = new CriteriaQuery(CourseEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, course, request.getParameterMap());
        List<CourseEntity> courses = this.courseService.getListByCriteriaQuery(cq,false);

        map.put(NormalExcelConstants.FILE_NAME,"用户信息");
        map.put(NormalExcelConstants.CLASS,CourseEntity.class);
        map.put(NormalExcelConstants.PARAMS,new ExportParams("课程列表", "导出人:Jeecg",
                "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST,courses);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;

	}
```

8.Excel导入校验,过滤不符合规则的数据,追加错误信息到Excel,提供常用的校验规则,已经通用的校验接口
```Java
	/**
     * Email校验
     */
    @Excel(name = "Email", width = 25)
    @ExcelVerify(isEmail = true, notNull = true)
    private String email;
    /**
     * 手机号校验
     */
    @Excel(name = "Mobile", width = 20)
    @ExcelVerify(isMobile = true, notNull = true)
    private String mobile;
    
    ExcelImportResult<ExcelVerifyEntity> result = ExcelImportUtil.importExcelVerify(new File(
            "d:/tt.xls"), ExcelVerifyEntity.class, params);
    for (int i = 0; i < result.getList().size(); i++) {
        System.out.println(ReflectionToStringBuilder.toString(result.getList().get(i)));
    }
```

9.导入Map
	设置导入参数,传入文件或者流,即可获得相应的list,自定义Key,需要实现IExcelDataHandler接口
```Java
	ImportParams params = new ImportParams();
	List<Map<String,Object>> list = ExcelImportUtil.importExcel(new File(
			"d:/tt.xls"), Map.class, params);
```	
