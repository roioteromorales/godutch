FROM tomcat:8-jre8
ADD tomcat/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
ADD build/libs/godutch-1.0.war /usr/local/tomcat/webapps/godutch-1.0.war
CMD ["catalina.sh", "run"]