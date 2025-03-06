@ECHO off
TITLE Mega Aion - Login Server Console
:START
CLS
echo.

ECHO Starting Mega Aion Login Server in %MODE% mode.
JAVA -Xms128m -Xmx512m -server -cp ./libs/*;AL-Login.jar com.aionemu.loginserver.LoginServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 2 GOTO START
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO Login Server has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO Login Server is terminated!
ECHO.
PAUSE
EXIT