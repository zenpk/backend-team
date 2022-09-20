FROM openjdk:latest
VOLUME /tmp
COPY ./target/*.jar /target.jar
ENTRYPOINT ["java","-jar","/target.jar"]
