# Build
FROM eclipse-temurin:17-alpine AS build
RUN apk add --no-cache bash

WORKDIR /app

COPY gradlew .
RUN chmod +x ./gradlew

COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN ./gradlew dependencies --no-daemon

# 타임리프 템플릿 포함 (resources 폴더 안의 템플릿을 빌드 후 JAR에 포함되도록)
COPY src/main/resources /app/src/main/resources
COPY src/main/java /app/src/main/java

# 전체 프로젝트 파일 복사
COPY . .
RUN chmod +x ./gradlew

RUN ./gradlew build --no-daemon -x test

# Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -g 1000 worker && \
    adduser -u 1000 -G worker -s /bin/sh -D worker

COPY --from=build --chown=worker:worker /app/build/libs/*.jar ./main.jar

USER worker:worker

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "main.jar"]
