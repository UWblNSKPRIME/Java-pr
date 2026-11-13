@echo off
chcp 65001 >nul
cd /d "%~dp0"
echo Compile...
javac -encoding UTF-8 -d src\main\java src\main\java\var1\Solution.java src\main\java\var1\ProcessConfig.java
if errorlevel 1 (
echo Compile error!
pause
exit /b 1
)
echo Run Solution (Part 1)...
echo.
java -Dfile.encoding=UTF-8 -cp src\main\java var1.Solution
if errorlevel 1 exit /b 1
echo.
echo Run ProcessConfig (Part 2)...
echo.
java -Dfile.encoding=UTF-8 -cp src\main\java var1.ProcessConfig --append
if errorlevel 1 exit /b 1
echo.
pause