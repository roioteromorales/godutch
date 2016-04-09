FROM tomcat:8-jre8
ADD tomcat/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
RUN rm -rf /usr/local/tomcat/webapps/ROOT/
ADD build/libs/godutch-*.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]