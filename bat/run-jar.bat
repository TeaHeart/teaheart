@echo off
chcp 65001
setlocal enabledelayedexpansion
cls

:main
call :find-file "jar"
if "!errorlevel!" neq "0" (
    goto :end-main
)
call :find-exe "java"
if "!errorlevel!" neq "0" (
    goto :end-main
)

start "application" "!java!" -Dfile.encoding=GBK -jar "!jar!"
if "!errorlevel!" == "0" (
    echo Run application successfully.
    timeout 8
    start http://localhost:80/
) else (
    echo Failed to run application!
)

:end-main
timeout 4
endlocal
goto :eof

:find-file
echo Looking for %~1...
for /F "delims=" %%i in ('dir /B "*.%~1" 2^> nul') do (
    echo Found %~1: %%i
    set "%~1=%%i"
    exit /B 0
)
echo %~1 not found!
exit /B 1

:find-exe
echo Looking for %~1...
if exist "%~1.txt" (
    set /P "exe=" < "%~1.txt"
    if exist "!exe!" (
        choice /M "Using !exe!"
        if "!errorlevel!" == "1" (
            echo Found %~1: !exe!
            set "%~1=!exe!"
            exit /B 0
        )
    )
)
for /F %%d in ('wmic logicaldisk get caption ^| findstr /R "[A-Z]:" 2^> nul') do (
    echo Scanning %%d disk...
    for /F "delims=" %%i in ('dir /S /B /A:-D-S-H "%%d\%~1.exe" 2^> nul') do (
        choice /M "Using %%i"
        if "!errorlevel!" == "1" (
            echo Found %~1: %%i
            echo %%i > "%~1.txt"
            set "%~1=%%i"
            exit /B 0
        )
    )
)
echo %~1 not found!
exit /B 1
