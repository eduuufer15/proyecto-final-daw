# ============================================
# ETAPA 1: Build con Maven
# ============================================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# ============================================
# ETAPA 2: Runtime con Java 17
# ============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar JAR desde etapa build
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto
EXPOSE 8080

# Variable de entorno para perfil
ENV SPRING_PROFILES_ACTIVE=docker

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
