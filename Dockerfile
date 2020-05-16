FROM java:8-jdk-alpine
LABEL maintainer="www.gxf727031867.top"
ARG JAR_FILE
VOLUME /tmp
ADD target/${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]