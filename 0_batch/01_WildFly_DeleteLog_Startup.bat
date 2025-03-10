@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-23
set JBOSS_HOME=c:\tools\wildfly-35.0.1.Final

del /F /Q %JBOSS_HOME%\standalone\log\*
del /F /Q %JBOSS_HOME%\standalone\tmp\*
call %JBOSS_HOME%\bin\standalone.bat --server-config=standalone-full.xml