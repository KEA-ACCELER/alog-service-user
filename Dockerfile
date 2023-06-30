
FROM amazoncorretto:17

WORKDIR /app

COPY . /app

CMD ["./gradlew", "bootRun"]