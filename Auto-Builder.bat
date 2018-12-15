@ECHO off
@COLOR 0C
SET MODE=clean package
SET TITLE=Build
CLS
TITLE Aion Lightning - %TITLE%ing All Project
CD AC-Login
start ..\AC-Tools\Ant\bin\ant
CD ../AC-Game
start ..\AC-Tools\Ant\bin\ant
CD ../AC-Chat
start ..\AC-Tools\Ant\bin\ant

