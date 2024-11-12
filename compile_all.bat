@echo off
setlocal

REM Set the source and output directories
set src_dir=src
set out_dir=out\production\logarlec

REM Create the output directory if it doesn't exist
if not exist %out_dir% (
    mkdir %out_dir%
)

REM Compile all Java files recursively and store the file list in a temporary file
del filelist.txt >nul 2>&1
(for /R %src_dir% %%f in (*.java) do echo %%f) > filelist.txt

REM Compile all the Java files using the list from filelist.txt
javac -encoding UTF-8 -d %out_dir% @filelist.txt

REM Check if the compilation was successful
if %errorlevel% equ 0 (
    echo Compilation succeeded.
) else (
    echo Compilation failed.
)

REM Clean up temporary file
del filelist.txt

endlocal
pause
