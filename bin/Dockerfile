# Stage 1: Build Stage
FROM maven:3.9.6 as build
WORKDIR /app
COPY . /app
RUN mvn clean package assembly:single

# Stage 2: Run Stage
# OpenJDK docker images are deprecated.
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/
CMD ["java", "-jar", "chorumebot-1.0-SNAPSHOT-jar-with-dependencies.jar"]