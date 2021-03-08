FROM java:8-jdk-alpine
LABEL maintainer="gxf"
ARG JAR_FILE
VOLUME /tmp
ADD target/${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]