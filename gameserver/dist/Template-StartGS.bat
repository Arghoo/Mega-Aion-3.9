@ECHO off
TITLE Mega Aion - Game Server Console
:START
CLS
SET JAVAVER=1.6
SET NUMAENABLE=false
CLS
echo.

IF "%JAVAVER%" == "1.7" (
SET JAVA_OPTS=-XX:+TieredCompilation %JAVA_OPTS%
)
IF "%NUMAENABLE%" == "true" (
SET JAVA_OPTS=-XX:+UseNUMA %JAVA_OPTS%
)
ECHO Starting Mega Aion Game Server in %MODE% mode.
JAVA -Xms3072m -Xmx12288m -XX:MaxHeapSize=12288m -Xdebug -XX:MaxNewSize=48m -XX:NewSize=48m -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseConcMarkSweepGC -XX:-UseSplitVerifier -Xbootclasspath/p:libs/jsr166-1.0.0.jar -ea -javaagent:./libs/al-commons-1.3.jar -cp ./libs/*;AL-Game.jar com.aionemu.gameserver.GameServer
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