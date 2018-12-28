@echo off
setlocal enabledelayedexpansion
setlocal

rem # Set prospector home directory
set PROSPECTOR_HOME=%~dp0..

rem # Set java
if defined JAVA_HOME (
    set JAVA="%JAVA_HOME%\bin\java.exe"
) else (
    for %%I in (java.exe) do set JAVA="%%~$PATH:I"
)

if exist %JAVA% goto found_java

rem # No java - was it bundled?
if exist "%PROSPECTOR_HOME%\jre" (
	set JAVA="%PROSPECTOR_HOME%\jre\bin\java.exe"
	goto found_java
)

echo Failed to find java - set JAVA_HOME or add java to the PATH 1>&2
exit /b 1

:found_java
rem # Set JVM classpath
for %%i in ("%PROSPECTOR_HOME%\lib\*.jar") do (
    call :concat "%%i"
)

rem # Set JVM parameters
set JVM_PARAMS=-Dlogback.configurationFile="%PROSPECTOR_HOME%\etc\logback.xml"
set JVM_PARAMS=%JVM_PARAMS% -Dio.netty.tryReflectionSetAccessible=false

%JAVA% %JVM_PARAMS% -cp %CLASSPATH% mn.foreman.prospector.app.Main
goto end

:concat
if not defined CLASSPATH (
    set CLASSPATH="%~1"
) else (
    set CLASSPATH=%CLASSPATH%;"%~1"
)
goto :eof

:end
endlocal