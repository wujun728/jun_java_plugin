@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

echo ==========================================
echo    jun_java_plugin 环境检查和初始化
echo ==========================================
echo.

REM 1. 检查前置条件
echo 1. 检查开发环境...
echo ----------------------------

where java >nul 2>&1
if %errorlevel% equ 0 (
    echo [√] java 已安装
    java -version 2>&1 | findstr "version"
) else (
    echo [×] java 未安装
    set JAVA_OK=0
)

where mvn >nul 2>&1
if %errorlevel% equ 0 (
    echo [√] mvn 已安装
    mvn -version 2>&1 | findstr "Apache Maven"
) else (
    echo [×] mvn 未安装
    set MVN_OK=0
)

where git >nul 2>&1
if %errorlevel% equ 0 (
    echo [√] git 已安装
) else (
    echo [×] git 未安装
)

echo.

if defined JAVA_OK (
    echo 错误: 缺少必要的开发工具
    echo 请安装: JDK 1.8+
    pause
    exit /b 1
)

if defined MVN_OK (
    echo 错误: 缺少必要的开发工具
    echo 请安装: Maven 3.5+
    pause
    exit /b 1
)

REM 2. 检查项目结构
echo 2. 检查项目结构...
echo ----------------------------

set MODULES=jun_java_plugins jun_springboot_plugin jun_springboot_starter jun_springcloud_plugin java_project_template

for %%m in (%MODULES%) do (
    if exist "%%m\" (
        echo [√] %%m 目录存在
    ) else (
        echo [!] %%m 目录不存在
    )
)

echo.

REM 3. 统计模块数量
echo 3. 统计项目模块...
echo ----------------------------

for %%m in (%MODULES%) do (
    if exist "%%m\" (
        dir /AD /B "%%m" 2>nul | find /C /V "" > temp.txt
        set /p count=<temp.txt
        del temp.txt
        echo %%m: !count! 个子模块
    )
)

echo.

REM 4. 检查 Maven 配置
echo 4. 检查 Maven 配置...
echo ----------------------------

if exist "pom.xml" (
    echo [√] 找到根 pom.xml
    findstr "<version>" pom.xml | findstr /V "<?xml" | findstr /V "modelVersion" > temp.txt
    set /p version=<temp.txt
    del temp.txt
    echo 项目配置: !version!
) else (
    echo [×] 未找到根 pom.xml
    pause
    exit /b 1
)

echo.

REM 5. 编译检查选项
echo 5. 编译检查选项...
echo ----------------------------
echo 是否执行 Maven 编译检查? (y/n)
echo 注意: 完整编译可能需要 5-10 分钟
set /p compile_choice=请选择:

if /i "%compile_choice%"=="y" (
    echo.
    echo 开始编译...
    set MAVEN_OPTS=-Xmx2048m -XX:MaxPermSize=512m

    REM 只编译不测试
    call mvn clean compile -DskipTests -T 4

    if %errorlevel% equ 0 (
        echo [√] 编译成功
    ) else (
        echo [×] 编译失败，请查看错误信息
        echo 提示: 某些模块可能有依赖冲突，这在演示仓库中是正常的
    )
) else (
    echo 跳过编译检查
)

echo.

REM 6. 冒烟测试
echo 6. 冒烟测试...
echo ----------------------------

if exist "target\" (
    echo [√] target 目录存在
) else (
    echo [!] target 目录不存在，未进行编译
)

if exist "task_list.json" (
    echo [√] 任务规划文件存在
    findstr /C:"\"id\"" task_list.json | find /C ":" > temp.txt
    set /p task_count=<temp.txt
    del temp.txt
    echo   - 共 !task_count! 个任务
)

if exist "CLAUDE.md" (
    echo [√] CLAUDE.md 文档存在
)

echo.

REM 7. 总结
echo ==========================================
echo    环境检查完成
echo ==========================================
echo.
echo 下一步操作:
echo 1. 阅读 README.md 了解项目结构
echo 2. 查看 task_list.json 了解任务规划
echo 3. 参考 CLAUDE.md 了解开发规范
echo 4. 使用 /crud 技能生成代码
echo.
echo 常用命令:
echo   mvn clean compile         # 编译项目
echo   mvn clean package         # 打包项目
echo   mvn clean install         # 安装到本地仓库
echo.
echo [√] 初始化完成!
echo.

pause
