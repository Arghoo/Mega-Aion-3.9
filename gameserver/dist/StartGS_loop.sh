#!/bin/bash

err=1
until [ $err == 0 ];
do
	[ -d log/ ] || mkdir log/
	[ -f log/console.log ] && mv log/console.log "log/backup/`date +%Y-%m-%d_%H-%M-%S`_console.log"
	java -Xms128m -Xmx1536m -Xbootclasspath/p:lib/jsr305-1.3.7.jar -ea -javaagent:./lib/commons-3.9.jar -cp ./lib/*:gameserver.jar com.aionemu.gameserver.GameServer > log/console.log 2>&1
	err=$?
	gspid=$!
	echo ${gspid} > gameserver.pid
	sleep 10
done
