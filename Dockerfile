FROM tomcat:8-jre8

ADD tomcat/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

# Deploy in root folder
RUN rm -rf /usr/local/tomcat/webapps/ROOT/
ADD build/libs/godutch-*.war /usr/local/tomcat/webapps/ROOT.war

# Fix timezone to spain
ENV TZ=Europe/Madrid
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

CMD ["catalina.sh", "run"]