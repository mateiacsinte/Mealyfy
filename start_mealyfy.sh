
#### REQUIREMENTS DOCKER AND MAVEN CLI

#### IF YOU DONT HAVE DOCKER OR MAVEN CLI
#### brew install maven
#### brew install docker
#### brew install docker-compose
mvn clean verify
docker pull openjdk:21-jdk
docker build -t mealyfy:latest .
docker-compose up