FROM openjdk:22-jdk as build
WORKDIR /tmp/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw && ./mvnw install -DskipTests

FROM eclipse-temurin:22-jre-alpine
WORKDIR /app

COPY --from=build /tmp/app/target/*.jar /app/app.jar
COPY start.sh start.sh
RUN chmod +x start.sh
RUN apk update && apk add --update apk-cron
RUN echo "* * * * * /app/start.sh" > /etc/crontabs/root

CMD ["crond", "-f", "-l", "0"]