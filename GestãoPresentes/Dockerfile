# Imagem única (sem multi-stage para compatibilidade)
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Instalar Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copiar arquivos do projeto
COPY pom.xml .
COPY src ./src

# Build da aplicação (pulando Spotless no Docker)
RUN mvn clean package -DskipTests -Dspotless.check.skip=true

EXPOSE 8080

# Executar o JAR gerado
ENTRYPOINT ["java", "-jar", "/app/target/GestaoPresentes-0.0.1-SNAPSHOT.jar"]

