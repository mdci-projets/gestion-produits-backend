# Utiliser une image Java officielle
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR dans l'image
COPY target/*.jar app.jar

ENV FRONTEND_URL=http://localhost:4300

# Exposer le port de l'application
EXPOSE 8080

# Démarrer l'application
CMD ["java", "-jar", "app.jar"]
