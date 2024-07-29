## build stage
FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder

WORKDIR /build
COPY pom.xml .
# 1) dependency caching
RUN mvn -B dependency:resolve

# 2) source 복사 & package
COPY src/main/ /build/src/main/
RUN mvn package
RUN java -Djarmode=layertools -jar target/demo.jar extract

FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

WORKDIR /home/appuser

# 2) Jar 복사
COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/application/ ./

# 3) 실행
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

#docker build -t demo-api:local .  && docker run -p 9090:8080  -e"SPRING_PROFILES_ACTIVE=local"  demo-api:local