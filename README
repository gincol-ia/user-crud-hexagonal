# User CRUD Hexagonal Architecture

Proyecto Spring Boot 3.5.0 con arquitectura hexagonal para gestión de usuarios.

## 🏗️ Arquitectura

El proyecto sigue los principios de la arquitectura hexagonal (ports & adapters):

```
src/main/java/com/example/usercrud/
├── domain/                     # Núcleo del dominio
│   └── User.java              # Entidad de dominio
├── application/               # Lógica de aplicación
│   ├── port/
│   │   ├── in/               # Puertos de entrada
│   │   │   └── UserServicePort.java
│   │   └── out/              # Puertos de salida
│   │       └── UserRepositoryPort.java
│   └── service/
│       └── UserService.java  # Implementación del servicio
└── infrastructure/           # Adaptadores
    └── adapter/
        ├── in/              # Adaptadores de entrada
        │   └── web/
        │       ├── controller/
        │       │   └── UserController.java
        │       ├── dto/
        │       │   └── UserDto.java
        │       └── exception/
        │           └── GlobalExceptionHandler.java
        └── out/             # Adaptadores de salida
            └── persistence/
                ├── entity/
                │   └── UserEntity.java
                ├── mapper/
                │   └── UserMapper.java
                └── repository/
                    ├── JpaUserRepository.java
                    └── SpringDataUserRepository.java
```

## 🚀 Inicio Rápido

### Requisitos
- Docker y Docker Compose
- Java 17 (opcional, para desarrollo local)
- Maven 3.8+ (opcional, para desarrollo local)

### Ejecutar con Docker

```bash
# Clonar el repositorio
git clone <tu-repositorio>
cd user-crud-hexagonal

# Iniciar los servicios
docker-compose up -d

# Ver logs
docker-compose logs -f backend
```

### Accesos

- **API REST**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **pgAdmin**: http://localhost:5050
  - Email: admin@example.com
  - Password: admin_pass

## 📚 API Endpoints

### Usuarios

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/users` | Crear usuario |
| GET | `/api/users/{id}` | Obtener usuario por ID |
| GET | `/api/users/username/{username}` | Obtener usuario por username |
| GET | `/api/users` | Listar todos los usuarios |
| GET | `/api/users?activeOnly=true` | Listar usuarios activos |
| PUT | `/api/users/{id}` | Actualizar usuario |
| PATCH | `/api/users/{id}/deactivate` | Desactivar usuario |
| PATCH | `/api/users/{id}/activate` | Activar usuario |
| DELETE | `/api/users/{id}` | Eliminar usuario |

### Ejemplo de peticiones

#### Crear usuario
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

#### Actualizar usuario
```bash
curl -X PUT http://localhost:8080/api/users/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe_updated",
    "email": "john.updated@example.com",
    "firstName": "John",
    "lastName": "Doe Updated"
  }'
```

## 🧪 Testing

### Para los agentes de IA

Este proyecto está preparado para que los agentes puedan:

1. **Generar tests unitarios**: 
   - Tests para el dominio
   - Tests para servicios
   - Tests para controladores
   - Tests de integración

2. **Optimizar código**:
   - Refactorizar métodos complejos
   - Mejorar la legibilidad
   - Aplicar patrones de diseño

3. **Detectar bugs**:
   - Análisis estático
   - Detección de code smells
   - Sugerencias de mejora

4. **Añadir funcionalidades**:
   - Paginación
   - Filtros avanzados
   - Auditoría
   - Autenticación/Autorización

## 🔧 Configuración

### Variables de entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `SPRING_DATASOURCE_URL` | URL de la base de datos | `jdbc:postgresql://postgres:5432/hexdb` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de BD | `hexuser` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de BD | `hexpass` |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | Modo DDL de Hibernate | `update` |

## 📦 Estructura de la base de datos

### Tabla: users

| Columna | Tipo | Descripción |
|---------|------|-------------|
| id | UUID | Identificador único |
| username | VARCHAR(50) | Nombre de usuario único |
| email | VARCHAR(100) | Email único |
| first_name | VARCHAR(50) | Nombre |
| last_name | VARCHAR(50) | Apellido |
| created_at | TIMESTAMP | Fecha de creación |
| updated_at | TIMESTAMP | Fecha de actualización |
| active | BOOLEAN | Estado del usuario |

## 🛠️ Desarrollo

### Construir el proyecto
```bash
mvn clean package
```

### Ejecutar tests
```bash
mvn test
```

### Ejecutar sin Docker
```bash
# Iniciar PostgreSQL localmente
# Configurar las variables de entorno
mvn spring-boot:run
```

## 📄 Licencia

MIT License