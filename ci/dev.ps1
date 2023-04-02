## Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
## netstat -nao|findstr 8080
## tasklist|findstr "5988"
## tasklist|findstr "java"
## taskkill /F /PID 7464
## Get-Process java | Stop-Process -Confirm:$false
echo "Power tools for developing."
$ErrorActionPreference = "Stop"
$curDir = Get-Location
mvn clean package
if (-not $?) {throw "Failed to doSomething"}
## Start-Process powershell.exe -WorkingDirectory $curDir -ArgumentList "-noexit mvn -q jetty:run -Dhsqlmem -pl server -am -Penv-dev"
Start-Process powershell.exe -WorkingDirectory $curDir -ArgumentList "mvn -q jetty:run -Dhsqlmem -pl server -am -Penv-dev"
## Start-Process powershell.exe -WorkingDirectory $curDir -ArgumentList "-noexit mvn gwt:codeserver -pl client -am"
Start-Process powershell.exe -WorkingDirectory $curDir -ArgumentList "mvn gwt:codeserver -pl client -am"
