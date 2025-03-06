@ECHO off

CLS

CD AL-Login/build/dist/AL-Login
start StartLS
CD ../../../../AL-Chat/build/dist/AL-Chat
start StartCS
CD ../../../../AL-Game/build/dist/AL-Game
start StartGS
)