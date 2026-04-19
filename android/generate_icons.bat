@echo off
REM Icon Generation Script for Zen Pop Arcade (Windows)
REM
REM Usage: generate_icons.bat <path_to_source_icon.png>
REM
REM This script resizes your icon to all required Android densities
REM and places them in the correct directories.
REM
REM Requirements: ImageMagick (convert.exe in PATH)

setlocal enabledelayedexpansion

if "%1"=="" (
    echo Usage: %0 ^<path_to_icon.png^>
    echo.
    echo Example: %0 C:\Users\YourName\my-icon.png
    exit /b 1
)

if not exist "%1" (
    echo Error: File not found: %1
    exit /b 1
)

set SOURCE_ICON=%1
set SCRIPT_DIR=%~dp0

REM Check if convert is available
where convert >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: ImageMagick 'convert' command not found.
    echo Install ImageMagick from: https://imagemagick.org/script/download.php
    exit /b 1
)

echo Generating Android app icons from: %SOURCE_ICON%

REM Define densities and sizes
set "densities[0]=mdpi" & set "sizes[0]=48"
set "densities[1]=hdpi" & set "sizes[1]=72"
set "densities[2]=xhdpi" & set "sizes[2]=96"
set "densities[3]=xxhdpi" & set "sizes[3]=144"
set "densities[4]=xxxhdpi" & set "sizes[4]=192"

REM Generate icons for each density
for /l %%i in (0,1,4) do (
    set density=!densities[%%i]!
    set size=!sizes[%%i]!
    
    set output_dir=%SCRIPT_DIR%app\src\main\res\mipmap-!density!
    set output_file=!output_dir!\ic_launcher.png
    
    if not exist "!output_dir!" mkdir "!output_dir!"
    
    echo   Generating !density! (!size!x!size!)...
    convert "%SOURCE_ICON%" -resize "!size!x!size!^!" "!output_file!"
    
    if !ERRORLEVEL! EQU 0 (
        echo     Created: !output_file!
    ) else (
        echo     Failed to create: !output_file!
        exit /b 1
    )
)

echo.
echo Successfully generated all app icons!
echo.
echo Next steps:
echo 1. Review the generated icons in app\src\main\res\mipmap-*\
echo 2. Build the app: gradlew.bat bundleRelease
echo 3. Upload to Google Play Console
