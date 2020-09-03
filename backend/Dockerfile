FROM maven:3.6.3-openjdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests=true
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/cvsystem-1.0.0.jar /usr/local/lib/cvsystem-1.0.0.jar
EXPOSE 8081:8081
ENTRYPOINT ["java","-jar","/usr/local/lib/cvsystem-1.0.0.jar"]
