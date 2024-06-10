@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Bulgakov startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and BULGAKOV_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Bulgakov.jar;%APP_HOME%\lib\java-jwt-4.4.0.jar;%APP_HOME%\lib\http4k-format-jackson-5.14.0.0.jar;%APP_HOME%\lib\jackson-module-kotlin-2.16.1.jar;%APP_HOME%\lib\jackson-databind-2.16.1.jar;%APP_HOME%\lib\jackson-annotations-2.16.1.jar;%APP_HOME%\lib\jackson-core-2.16.1.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.16.1.jar;%APP_HOME%\lib\http4k-client-okhttp-5.14.0.0.jar;%APP_HOME%\lib\http4k-cloudnative-5.14.0.0.jar;%APP_HOME%\lib\http4k-multipart-5.14.0.0.jar;%APP_HOME%\lib\http4k-server-netty-5.14.0.0.jar;%APP_HOME%\lib\http4k-format-core-5.14.0.0.jar;%APP_HOME%\lib\http4k-template-pebble-5.14.0.0.jar;%APP_HOME%\lib\http4k-template-core-5.14.0.0.jar;%APP_HOME%\lib\http4k-realtime-core-5.14.0.0.jar;%APP_HOME%\lib\http4k-core-5.14.0.0.jar;%APP_HOME%\lib\kotlin-reflect-1.6.21.jar;%APP_HOME%\lib\okhttp-4.12.0.jar;%APP_HOME%\lib\okio-jvm-3.6.0.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.9.10.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.9.10.jar;%APP_HOME%\lib\kotlin-stdlib-1.9.23.jar;%APP_HOME%\lib\flyway-core-9.5.1.jar;%APP_HOME%\lib\h2-2.1.214.jar;%APP_HOME%\lib\netty-codec-http2-4.1.107.Final.jar;%APP_HOME%\lib\pebble-3.2.2.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\toml-3.6.6.jar;%APP_HOME%\lib\netty-codec-http-4.1.107.Final.jar;%APP_HOME%\lib\netty-handler-4.1.107.Final.jar;%APP_HOME%\lib\netty-codec-4.1.107.Final.jar;%APP_HOME%\lib\netty-transport-native-unix-common-4.1.107.Final.jar;%APP_HOME%\lib\netty-transport-4.1.107.Final.jar;%APP_HOME%\lib\netty-buffer-4.1.107.Final.jar;%APP_HOME%\lib\netty-resolver-4.1.107.Final.jar;%APP_HOME%\lib\netty-common-4.1.107.Final.jar;%APP_HOME%\lib\unbescape-1.1.6.RELEASE.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\core-3.6.6.jar


@rem Execute Bulgakov
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %BULGAKOV_OPTS%  -classpath "%CLASSPATH%" ru.yarsu.BulgakovKt %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable BULGAKOV_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%BULGAKOV_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
