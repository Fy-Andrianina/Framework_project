set tomcatPath="E:\Installations\apache-tomcat-10.0.22\webapps"
copy ..\Flamework.jar web\WEB-INF\lib\
copy ..\Flamework.jar lib\
dir src /a-d /b /s *.java > sources.txt
javac -cp .;lib/* -d bin @sources.txt
del sources.txt

cd bin
jar -cvf ../web/WEB-INF/lib/My_boo.jar *

xcopy "*" "../web/WEB-INF/classes" /E /Y /I
pause
cd ../web
jar -cvf "%tomcatPath%/My_boo.war" *
