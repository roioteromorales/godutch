FROM tomcat
CMD pwd
COPY godutch-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]