FROM java:8
LABEL maintainer="www.gxf727031867.top"
ARG JAR_FILE
VOLUME /tmp
ADD target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]