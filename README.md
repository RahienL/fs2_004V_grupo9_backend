# 🍰 Pastelería 1000 Sabores - Backend

## 📋 Descripción
Backend de la aplicación de Pastelería 1000 Sabores desarrollado con Spring Boot 3.3.0 y MySQL 8.0.

## 🛠️ Requisitos
- Java JDK 22 o superior
- Maven 3.9.0
- MySQL 8.0 (Docker)
- Docker

## 🚀 Instalación y Ejecución

### 1. Iniciar MySQL (Docker)

**Primera vez (crear contenedor):**
`ash
docker run --name pasteleria-mysql 
  -e MYSQL_ROOT_PASSWORD=123456 
  -e MYSQL_DATABASE=pasteleria_db 
  -e MYSQL_USER=pasteleria_user 
  -e MYSQL_PASSWORD=123456 
  -p 3306:3306 
  -d mysql:8.0
`

**Si ya existe (solo iniciar):**
`ash
docker start pasteleria-mysql
`

### 2. Compilar y Ejecutar

`ash
mvn clean spring-boot:run
`

El servidor estará disponible en: **http://localhost:8080**

## 📚 API Documentation
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Base: http://localhost:8080/api

## 🔐 Endpoints Públicos (sin autenticación)
- POST /api/auth/login
- POST /api/auth/register
- GET /api/categorias
- GET /api/categorias/{id}
- GET /api/productos
- GET /api/productos/{id}

## 🗄️ Base de Datos
- Host: localhost
- Puerto: 3306
- Usuario: pasteleria_user
- Contraseña: 123456
- Database: pasteleria_db

## 📊 Tablas
- usuario
- categoria
- producto
- boleta
- detalle_boleta

## 🔑 Autenticación
Usa JWT (JSON Web Token) para endpoints protegidos.
El token se obtiene al hacer login o registro.

## 👥 Grupo 9
DuocUC - Programación Full Stack 2
