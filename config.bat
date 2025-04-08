@echo off
chcp 65001
setlocal enabledelayedexpansion
cls

:main
@REM git配置
git config --global http.proxy http://127.0.0.1:7890
git config --global https.proxy https://127.0.0.1:7890
git config --global user.name TeaHeart
git config --global user.email 1069696634@qq.com
git config --global alias.tree "log --pretty=format:'%h %ad | %s%d [%an]' --graph --date=short"

@REM dlss信息显示
reg add "HKEY_LOCAL_MACHINE\SOFTWARE\NVIDIA Corporation\Global\NGXCore" /V "ShowDlssIndicator" /T REG_DWORD /F /D 1024

@REM doh
netsh dns add encryption server=223.5.5.5 dohtemplate=https://dns.alidns.com/dns-query autoupgrade=yes udpfallback=no
netsh dns add encryption server=223.6.6.6 dohtemplate=https://dns.alidns.com/dns-query autoupgrade=yes udpfallback=no
netsh dns add encryption server=2400:3200::1 dohtemplate=https://dns.alidns.com/dns-query autoupgrade=yes udpfallback=no
netsh dns add encryption server=2400:3200:baba::1 dohtemplate=https://dns.alidns.com/dns-query autoupgrade=yes udpfallback=no

@REM 小鹤双拼
reg add "HKEY_CURRENT_USER\SOFTWARE\Microsoft\InputMethod\Settings\CHS" /V "UserDefinedDoublePinyinScheme0" /T REG_SZ /F /D "小鹤双拼*2*^*iuvdjhcwfg^xmlnpbksqszxkrltvyovt"
reg add "HKEY_CURRENT_USER\SOFTWARE\Microsoft\InputMethod\Settings\CHS" /V "DoublePinyinScheme" /T REG_DWORD /F /D "0x0a"
@REM reg add "HKEY_CURRENT_USER\SOFTWARE\Microsoft\InputMethod\Settings\CHS" /V "Enable Double Pinyin" /T REG_DWORD /F /D "0x01"

@REM 修改允许备份频率为0分钟
reg add "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\SystemRestore" /V "SystemRestorePointCreationFrequency" /T REG_DWORD /F /D 0

@REM 显示联想电池养护模式
reg add "HKEY_LOCAL_MACHINE\SOFTWARE\Lenovo\PCManager\BatteryCache" /V "IsBatteryPolymer" /T REG_DWORD /F /D 0
:end-main
timeout 4
endlocal
goto :eof
