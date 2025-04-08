@echo off
chcp 65001
setlocal enabledelayedexpansion
cls

:main
@REM main...
@REM call :foo arg1,...

:end-main
timeout 4
endlocal
goto :eof

@REM more functions...

@REM :foo
@REM echo %~1
@REM exit /B 0
@REM :end-foo