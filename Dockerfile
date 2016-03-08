FROM tomcat:8-jre8
ADD tomcat/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
ADD build/libs/godutch-*.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]