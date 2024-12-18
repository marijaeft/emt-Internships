FROM maven:3.9.6 AS build

WORKDIR /app

COPY src ./src

COPY pom.xml .

RUN mvn clean package -DskipTests

FROM amazoncorretto:22-alpine3.18-jdk

WORKDIR /app

RUN mkdir images

COPY --from=build /app .

CMD ["java", "-jar", "./target/internships-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080