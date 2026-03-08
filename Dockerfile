# 1단계: 빌드용 이미지
FROM gradle:8.7-jdk21 AS builder

WORKDIR /build

# Gradle 관련 파일 먼저 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./

# 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성 캐시 레이어
RUN ./gradlew dependencies --no-daemon || true

# 실제 소스 복사
COPY src ./src

# jar 생성
RUN ./gradlew bootJar --no-daemon

# 2단계: 실행용 이미지
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# jar만 복사
COPY --from=builder /build/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]