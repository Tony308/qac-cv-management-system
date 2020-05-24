FROM maven:3.6.3-openjdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests=true
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/cvsystem-0.0.1-SNAPSHOT.jar /usr/local/lib/cvsystem-0.0.1-SNAPSHOT.jar
EXPOSE 8081:8081
ENTRYPOINT ["java","-jar","/usr/local/lib/cvsystem-0.0.1-SNAPSHOT.jar"]
