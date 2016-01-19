@ECHO off

REM Step 1: set properties.
CD /D %~dp0
SET currentPath=%~dp0
call ant -f %currentPath%build.xml