@echo off
setlocal
REM Convenience runner for the Salesforce login automation suite.
REM Pass optional Maven -D properties for real credentials, for example:
REM   run-tests -Dsalesforce.username=you@example.com -Dsalesforce.password=secret
cd /d "%~dp0"

REM Point Maven at the locally bundled JDK when present.
if exist ".toolchain\jdk" (
    for /d %%d in (".toolchain\jdk\jdk-*") do set "JAVA_HOME=%%~fd"
)

mvn clean test %*
endlocal