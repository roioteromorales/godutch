FROM tomcat:8-jre8

ADD tomcat/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

# Deploy in root folder
RUN rm -rf /usr/local/tomcat/webapps/ROOT/
ADD build/libs/godutch-*.war /usr/local/tomcat/webapps/ROOT.war

# Fix timezone to spain
ENV TZ=Europe/Madrid
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#To debug, not working
#ENV JPDA_ADDRESS=8000
#ENV JPDA_TRANSPORT=dt_socket
#
#EXPOSE 8000

CMD ["catalina.sh", "run"]