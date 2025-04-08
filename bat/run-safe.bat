@echo off
chcp 65001
setlocal enabledelayedexpansion
cls

:main
if exist "%1" (
    if /i "%~x1" == ".bak" (
        copy "%~1" "%~n1"
        start "" "%~n1"
    ) else if /i "%~x1" == ".exe" (
          copy "%~1" "%~1.bak"
    )
)

:end-main
@REM timeout 4
endlocal
goto :eof
