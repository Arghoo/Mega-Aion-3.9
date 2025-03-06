@ECHO off
TITLE Mega Aion - Chat Server Console
:START
CLS
echo.

ECHO Starting Mega Aion Chat Server in %MODE% mode.
JAVA -Xms128m -Xmx512m -server -cp ./libs/*;chatserver.jar com.aionemu.chatserver.ChatServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 2 GOTO START
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO Chat Server has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO Chat Server is terminated!
ECHO.
PAUSE
EXIT
