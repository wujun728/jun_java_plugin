@echo off
mode con cols=100 lines=30
color 3f
title DBDocTool数据库文档自动生成工具
rem 接收输入
set dbtype=
set dbhost=
set dbport=
set dbname=
set dbuser=
set dbpasswd=
set dbschema=
set docpath=
echo DBDocTool数据库文档自动生成工具
echo -------------------------------
echo 详情参看：http://git.oschina.net/xirb/Dbdoctool
echo 该项目将持续更新，计划提供各种模版，实现自定义模版，支持单表或表集合生成，方便程序员书写数据库文档
echo 把数据库表结构信息转化为数据库文档
echo ====================================================================================================
echo 数据库类型：A：Mysql B：SQL Server C：Oracle D：DB2 E：PostgreSQL
set /p dbtype=请选择数据库类型：
rem 输出得到的输入信息
echo 您选择的数据库类型是：%dbtype%
echo ====================================================================================================
set /p dbhost=数据库IP：
set /p dbport=数据库端口：
set /p dbname=数据库名称：
set /p dbuser=数据库用户名：
set /p dbpasswd=数据库密码：
if "%dbtype%"=="C" set /p dbschema=数据库模式：
if "%dbtype%"=="D" set /p dbschema=数据库模式：
if "%dbtype%"=="E" set /p dbschema=数据库模式：
set /p docpath=文档保存路径：
echo 正在生成......
if "%dbschema%"=="" (db_doc -h %dbhost% -d %dbname% -u %dbuser% -p %dbpasswd% -P %dbport% --path %docpath%) else (db_doc -h %dbhost% -d %dbname% -u %dbuser% -p %dbpasswd% -P %dbport% -s %dbschema% --path %docpath%)
pause
