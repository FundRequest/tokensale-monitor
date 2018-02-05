FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /tmp
ADD build/libs/tokensale-monitor-0.0.1-SNAPSHOT.jar tokensale-monitor.jar
RUN sh -c 'touch /tokensale-monitor.jar' && \
    mkdir config

ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /tokensale-monitor.jar" ]
