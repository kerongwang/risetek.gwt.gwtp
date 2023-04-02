FROM jetty:9.4.36-jre11-slim

COPY ROOT.war /var/lib/jetty/webapps
COPY docker-entrypoint.sh /entrypoint.sh

VOLUME /config
VOLUME /.database

## ENTRYPOINT ["/entrypoint.sh"]
RUN java -jar "$JETTY_HOME/start.jar" --create-startd --add-to-start=https --add-to-start=http2
