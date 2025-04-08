@echo off
chcp 65001
setlocal enabledelayedexpansion
cls

:main
net stop mmpc 2> nul
echo 杀死进程中...
for /L %%i in (0, 0, 1) do (
    for %%p in (Ctsc_Multi.exe,DeviceControl_x64.exe,HRMon.exe,MultiClient.exe,OActiveII-Client.exe,OEClient.exe,OELogSystem.exe,OEUpdate.exe,OEProtect.exe,ProcessProtect.exe,RunClient.exe,RunClient.exe,ServerOSS.exe,Student.exe,wfilesvr.exe,tvnserver.exe,updatefilesvr.exe,ScreenRender.exe,StudentMain.exe) do (
        taskkill /F /T /IM "%%p" 2> nul
    )
)

:end-main
timeout 4
endlocal
goto :eof