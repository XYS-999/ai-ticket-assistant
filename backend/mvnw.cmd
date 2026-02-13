@ECHO OFF
SETLOCAL EnableDelayedExpansion

SET BASE_DIR=%~dp0
IF "%BASE_DIR:~-1%"=="\" SET BASE_DIR=%BASE_DIR:~0,-1%
SET WRAPPER_DIR=%BASE_DIR%\.mvn\wrapper
SET WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar
SET WRAPPER_PROPERTIES=%WRAPPER_DIR%\maven-wrapper.properties
SET MAVEN_PROJECTBASEDIR=%BASE_DIR%

IF NOT EXIST "%WRAPPER_PROPERTIES%" (
  ECHO Missing %WRAPPER_PROPERTIES%
  EXIT /B 1
)

IF NOT EXIST "%WRAPPER_JAR%" (
  FOR /F "usebackq tokens=* delims=" %%I IN (`powershell -NoProfile -ExecutionPolicy Bypass -Command "$line = Get-Content -Path '%WRAPPER_PROPERTIES%' | Where-Object { $_ -like 'wrapperUrl=*' } | Select-Object -First 1; if ($line) { $line.Substring(11) }"`) DO (
    SET WRAPPER_URL=%%I
  )
  IF "!WRAPPER_URL!"=="" (
    ECHO wrapperUrl is not set in %WRAPPER_PROPERTIES%
    EXIT /B 1
  )
  powershell -NoProfile -ExecutionPolicy Bypass -Command "New-Item -ItemType Directory -Path '%WRAPPER_DIR%' -Force | Out-Null; Invoke-WebRequest -UseBasicParsing -Uri '!WRAPPER_URL!' -OutFile '%WRAPPER_JAR%'"
  IF ERRORLEVEL 1 EXIT /B 1
)

java -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
IF ERRORLEVEL 1 EXIT /B 1

ENDLOCAL
