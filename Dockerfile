FROM openjdk:17-jdk

WORKDIR /app

COPY target/SpendEasyJava-0.0.1.jar /app/SpendEasy.jar

EXPOSE 8080

CMD ["java", "-jar", "SpendEasy.jar"]