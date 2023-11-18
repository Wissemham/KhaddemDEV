FROM openjdk:8
ADD target/*.jar khaddem.jar
ENTRYPOINT ["java","-jar","/khaddem.jar"]

