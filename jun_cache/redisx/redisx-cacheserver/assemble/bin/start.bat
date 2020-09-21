@echo off
title SHARELING SERVICE
echo.
echo Starting SHARELING SERVICE...
echo.
echo To stop, press Ctrl+c
setlocal

if "%JAVA_HOME%" == "" set _JAVACMD=java.exe
if not exist "%JAVA_HOME%\bin\java.exe" set _JAVACMD=java.exe
if "%_JAVACMD%" == "" set _JAVACMD="%JAVA_HOME%\bin\java.exe"

set SERVER_HOME=%~dp0..
set LIB_DIR=%SERVER_HOME%\lib

for %%a in ("%LIB_DIR%\*.*") do call :process %%~nxa
goto :next

:process
set CLASSPATH=%CLASSPATH%;%LIB_DIR%\%1
goto :end

:next

%_JAVACMD% -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:MaxPermSize=128m -XX:NewSize=512m -XX:MaxNewSize=512m -XX:-UseConcMarkSweepGC -XX:+UseParNewGC -Dserver.home="%SERVER_HOME%" -Dfile.encoding=UTF8 -cp "%CLASSPATH%" com.deepsoft.server.SharelingServer "Shareling_Server" %*

@endlocal
:end