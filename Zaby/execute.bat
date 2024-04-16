set tomcatPath="C:\apache-tomcat-10.1.15\webapps"
copy ..\Flamework.jar web\WEB-INF\lib\
copy ..\Flamework.jar lib\
dir src /a-d /b /s *.java > sources.txt
javac -cp .;lib/* -d bin @sources.txt
del sources.txt

cd bin
jar -cvf ../web/WEB-INF/lib/Zaby.jar *

xcopy "*" "../web/WEB-INF/classes" /E /Y /I

cd ../web
jar -cvf "%tomcatPath%/Zaby.war" *
