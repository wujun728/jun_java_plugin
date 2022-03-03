@echo 1. 生成器的主要配置文件为generator.xml,里面修改数据库连接属性
@echo 2. template目录为代码生成器的模板目录,可自由调整模板的目录结构

@set classpath=%classpath%;.;.\lib\*;.\lib\rapid-generator.jar;.\lib\freemarker.jar;.\lib\h2-1.2.137.jar;.\lib\log4j-1.2.15.jar;.\lib\mysql-connector-java-5.0.5-bin.jar;.\lib\ojdbc14.jar;.\lib\postgresql-8.4-701.jdbc3.jar;.\lib\sqljdbc.jar

@set PATH=%JAVA_HOME%\bin;%PATH%;
@java -server -Xms128m -Xmx384m cn.org.rapid_framework.generator.ext.CommandLine -DtemplateRootDir=template
@if errorlevel 1 (
@echo ----------------------------------------------
@echo   ****错误***: 请设置好JAVA_HOME环境变量再运行或者检查你的classpath路径
@pause
)

:end