### SPRING_BOOT/MAVEN CV_SYSTEM CONTAINER ##
FROM ubuntu 
RUN apt-get update && apt-get install maven -y
VOLUME /tmp
WORKDIR /app
EXPOSE 8081:8081
ADD ./target/cvsystem-0.0.1-SNAPSHOT.jar ./cvsystem-0.0.1-SHANPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./cvsystem-0.0.1-SHANPSHOT.jar"]
