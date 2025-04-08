@echo off
chcp 65001
setlocal enabledelayedexpansion
cls

:main
for /F "tokens=5" %%i in ('reg query "HKEY_CURRENT_USER\SOFTWARE\Microsoft\InputMethod\Settings\CHS" /V "Enable Double Pinyin" /T REG_DWORD ^| findstr "0x"') do (
    set "opt=%%i"
)

if "!opt!" == "" (
    echo 添加小鹤双拼
    set "opt=0x0"
    reg add "HKEY_CURRENT_USER\SOFTWARE\Microsoft\InputMethod\Settings\CHS" /V "UserDefinedDoublePinyinScheme0" /T REG_SZ /F /D "小鹤双拼*2*^*iuvdjhcwfg^xmlnpbksqszxkrltvyovt" > nul
    reg add "HKEY_CURRENT_USER\SOFTWARE\Microsoft\InputMethod\Settings\CHS" /V "DoublePinyinScheme" /T REG_DWORD /F /D "0x0a" > nul
)

call :set-typing "!opt!"

:end-main
timeout 4
endlocal
goto :eof

:set-typing
if "%~1" == "0x0" (
    echo 启用双拼
    set "value=0x1"
) else (
    echo 禁用双拼
    set "value=0x0"
)
reg add "HKEY_CURRENT_USER\SOFTWARE\Microsoft\InputMethod\Settings\CHS" /V "Enable Double Pinyin" /T REG_DWORD /F /D %value% > nul
exit /B