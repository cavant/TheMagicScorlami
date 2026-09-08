@rem TheMagicSportslami Gradle Wrapper Script
@rem Forwarding directly to installed Gradle distribution

@if "%DEBUG%"=="" @echo off
@setlocal

set GRADLE_BIN=C:\Users\Connor\.gradle\wrapper\dists\gradle-8.11.1-all\2qik7nd48slq1ooc2496ixf4i\gradle-8.11.1\bin\gradle.bat

if exist "%GRADLE_BIN%" (
    call "%GRADLE_BIN%" %*
) else (
    echo Error: Gradle binary not found at %GRADLE_BIN%
    exit /b 1
)
