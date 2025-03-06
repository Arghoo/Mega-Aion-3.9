@ECHO off
TITLE Mega Aion - Game Server Console
SET PATH=..\Jdk\bin
:START
CLS

ECHO Starting Mega Aion Game Server in %MODE% mode.
JAVA -Xms1280m -Xmx8192m -XX:MaxHeapSize=8192m -ea -javaagent:./lib/commons-3.9.jar -cp ./lib/*;gameserver.jar com.aionemu.gameserver.GameServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 2 GOTO START
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO Game Server has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO Game Server is terminated!
ECHO.
PAUSE
EXIT
