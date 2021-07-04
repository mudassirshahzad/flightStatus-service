FROM adoptopenjdk/openjdk14:alpine

# These arguments are being passed from the docker compose file
ARG JAR_FILE
ARG activeSpringProfile

ADD ${JAR_FILE} app.jar

EXPOSE 8080 8787

ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8787,suspend=n"

RUN sh -c 'touch /app.jar'

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -e SERVER_PORT=0 -Dspring.profiles.active=$activeSpringProfile -jar /app.jar" ]
