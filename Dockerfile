FROM openjdk:21-jdk
EXPOSE 8080
COPY target/mealyfy-0.0.1-SNAPSHOT.jar mealyfy-0.0.1.jar
ENTRYPOINT ["java","-jar","/mealyfy-0.0.1.jar"]