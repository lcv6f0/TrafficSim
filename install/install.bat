:: Version 0.2
mkdir "%localappdata%\Traffic Simulator\"
mkdir "%localappdata%\Traffic Simulator\resources"
mkdir "%localappdata%\Traffic Simulator\app"
mkdir  "%localappdata%\Traffic Simulator\config"
mkdir "%appdata%\Microsoft\Windows\Start Menu\Programs\Traffic Simulator\"
set c_path=%cd%
cd /d "%localappdata%/Traffic Simulator/app"
del "Traffic Simulator.jar"
cd /d %c_path%
copy "TrafficSim.jar" "%localappdata%\Traffic Simulator\app\Traffic Simulator.jar"
::copy "traffic.conf" "%localappdata%/Traffic Simulator/config/traffic.conf"
xcopy /E "resources" "%localappdata%/Traffic Simulator/resources"
powershell "$s=(New-Object -COM WScript.Shell).CreateShortcut('%userprofile%\Start Menu\Programs\Traffic Simulator\Traffic Simulator.lnk');$s.TargetPath='%LOCALAPPDATA%\Traffic Simulator\app\Traffic Simulator.jar';$s.Save()"
pause
::"
