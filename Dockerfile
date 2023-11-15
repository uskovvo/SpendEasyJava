FROM maven:3.8-eclipse-temurin-19 as builder
WORKDIR /app
COPY . /app/.
RUN mvn install -Pproduction

FROM eclipse-temurin:19-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 8989
ENTRYPOINT ["java", "-jar", "/app/*.jar", "--spring.profiles.active=preprod"]