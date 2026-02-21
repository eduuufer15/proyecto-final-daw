## Estructura del Repositorio
* **`Proyecto-1/`**: Backend desarrollado con **Spring Boot**, Maven y JPA. Incluye configuración de Docker para las bases de datos.
* **`Quiz-React/`**: Frontend desarrollado con **React**, Vite y TypeScript.

---

## Requisitos Previos
Para ejecutar este proyecto, asegúrate de tener instalado:
1. **Docker Desktop** (para MySQL y MongoDB).
2. **JDK 17** o superior.
3. **Node.js** (v18+) y **npm**.

---

## Guía de Inicio (Copiar y Pegar)

Sigue estrictamente este orden para asegurar que la conexión entre servicios sea exitosa.

### 1. Levantar Bases de Datos (Docker)
El backend utiliza **MySQL 8.0** para los datos de la aplicación y **MongoDB 7.0** para el sistema de logs.

Abre una terminal en la raíz del proyecto y ejecuta:
cd Proyecto-1 // 
docker-compose up -d
