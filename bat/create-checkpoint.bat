@echo off
chcp 65001
setlocal enabledelayedexpansion
cls

:main
@REM 修改允许备份频率为0分钟
reg add "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\SystemRestore" /V "SystemRestorePointCreationFrequency" /T REG_DWORD /F /D 0
powershell.exe -ExecutionPolicy Bypass -Command "Checkpoint-Computer -Description $(Get-Date -Format 'yyyyMMdd') -RestorePointType MODIFY_SETTINGS"

:end-main
timeout 4
endlocal
goto :eof