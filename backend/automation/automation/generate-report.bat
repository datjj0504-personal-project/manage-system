@echo off
setlocal EnableExtensions

pushd "%~dp0..\.."

echo =====================================
echo Task Management Automation Runner
echo =====================================
echo Current Java Version
java -version
echo.
echo Current Maven Version
call .\mvnw.cmd -version
echo.
if defined SPRING_PROFILES_ACTIVE (
  set "CURRENT_SPRING_PROFILE=%SPRING_PROFILES_ACTIVE%"
) else (
  set "CURRENT_SPRING_PROFILE=local"
)
echo Current Spring Profile
echo %CURRENT_SPRING_PROFILE%
echo.
echo Working Directory
echo %CD%
echo.
call .\mvnw.cmd test surefire-report:report-only
if errorlevel 1 goto :failed
if exist target\failsafe-reports (
  call .\mvnw.cmd failsafe:report-only
  if errorlevel 1 goto :failed
) else (
  echo No Failsafe reports found. Skipping integration HTML report generation.
)
echo.
echo SUCCESS
popd
exit /b 0

:failed
echo.
echo FAILED
popd
exit /b 1