# Spring Boot API with CI/CD & Docker & AWS de gestion des produits

![GitHub Actions](https://img.shields.io/github/actions/workflow/status/mdci-projets/gestion-produits-backend/actions/workflows/ci-cd.yml?branch=main)
![CI - Build, Test, and Docker](https://github.com/mdci-projets/gestion-produits-backend/actions/workflows/ci-cd.yml/badge.svg)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green)

## 📖 Description
Cette application **Spring Boot** gère des produits avec une API REST sécurisée. Elle inclut :
- **Authentification avec JWT**
- **Gestion des produits avec pagination et cache**
- **Gestion des erreurs personnalisée**
- **Notifications en temps réel lorsqu'un produit est ajouté via WebSocket**
- **CI/CD avec GitHub Actions**
- **Dockerisation et déploiement sur un serveur distant**

---

## 🚀 Installation & Exécution

### 📌 Prérequis
- **Java 17+**
- **Maven**
- **Docker**
- **Git**

### 🔹 1️⃣ Cloner le projet

```bash
git clone https://github.com/your-repo-name.git
cd your-repo-name
```


### 🔹 2️⃣ Construire et exécuter avec Maven

```bash
mvn clean install mvn spring-boot:run
```


L'API est accessible à `http://localhost:8080/api/products`.

---

## 📦 **Dockerisation**

### 🔹 1️⃣ Construire l'image Docker

```bash
docker build -t spring-boot-app .
```

### 🔹 2️⃣ Exécuter le conteneur

```bash
docker run -d -p 8080:8080 --name spring-app spring-boot-app
```


### 🔹 3️⃣ Arrêter et supprimer le conteneur

```bash
docker stop spring-app docker rm spring-app
```

---

## 🔄 **CI/CD avec GitHub Actions**

### ✅ **Pipeline CI/CD :**
1. **Build et test** du projet avec Maven (`mvn verify`).
2. **Build et push** de l’image Docker sur Docker Hub.
3. **Déploiement automatique** sur un serveur distant.

### 🛠️ **Configuration des secrets GitHub**
Ajoutez ces secrets dans **GitHub → Settings → Secrets and Variables** :

| Secret Name         | Description |
|---------------------|-------------|
| `DOCKER_USERNAME`   | Nom d’utilisateur Docker Hub |
| `DOCKER_PASSWORD`   | Mot de passe Docker Hub |
| `SSH_HOST`         | Adresse IP du serveur |
| `SSH_USER`         | Nom d’utilisateur SSH |
| `SSH_PRIVATE_KEY`  | Clé privée SSH pour connexion |

### 🚀 **Déploiement Automatique**
À chaque **push** sur `main`, le pipeline :
- Teste et construit le projet.
- Construit l’image Docker et la pousse sur Docker Hub.
- Se connecte au serveur et déploie la dernière version.

---

## 📡 **API Documentation**

### 🔹 **Endpoints Produits**
| Méthode | Endpoint | Description | Accès |
|---------|---------|-------------|------|
| `GET`   | `/api/products` | Liste paginée des produits | `USER, ADMIN` |
| `GET`   | `/api/products/{id}` | Détails d’un produit | `USER, ADMIN` |
| `POST`  | `/api/products` | Ajouter un produit | `ADMIN` |
| `PUT`   | `/api/products/{id}` | Modifier un produit | `ADMIN` |
| `DELETE`| `/api/products/{id}` | Supprimer un produit | `ADMIN` |

### 🔹 **Exemple de requête avec cURL**

```bash
curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" -d '{ "username": "user1", "password": "password", "roles": ["USER"] }'
```

### 2️⃣ **Se connecter pour obtenir un Token**

```bash
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{ "username": "user1", "password": "password" }'
```

### 3️⃣ **Utiliser le Token pour appeler l'API**

```bash
curl -X GET http://localhost:8080/api/products -H "Authorization: Bearer <TOKEN>"
```

---
## 📖 Utilisation de Swagger
Une fois l’application lancée, Swagger UI est disponible ici : 👉 Swagger UI


### 🔐 Ajouter un Token JWT dans Swagger

1. **Allez sur Swagger UI (http://localhost:8080/swagger-ui/index.html)**
2. **Cliquez sur Authorize (en haut à droite)**
3. **Entrez votre token JWT au format :**

---

## 🛠️ **Configuration**

### 📌 **Variables d'environnement**
Dans `application.properties` :
**server.port=8080**
**spring.datasource.url=jdbc:h2:mem:testdb**
**spring.datasource.username=sa**
**spring.datasource.password=**
**spring.datasource.driverClassName=org.h2.Driver**
**spring.jpa.databa[main](src%2Fmain)se-platform=org.hibernate.dialect.H2Dialect**
**spring.h2.console.enabled=true**

JWT
jwt.secret=mySecretKeymySecretKeymySecretKeymySecretKey 
jwt.expirationMs=3600000


---

## 🔍 **Tests**
Exécuter les tests unitaires et d'intégration avec Maven :

```bash
mvn test
```

---

## 🏗️ **Contributions**
🚀 Les contributions sont les bienvenues ! Forkez le repo, créez une branche et proposez une **Pull Request**.

---

## 📜 **Licence**
MIT License © 2025 **Youssef Massaoudi**

---

## 🛠️ **Technologies utilisées**
- ✅ **Spring Boot 3.4.1**
- ✅ **Maven**
- ✅ **JWT Authentication**
- ✅ **Spring Data JPA**
- ✅ **Docker**
- ✅ **Swagger**
- ✅ **GitHub Actions**