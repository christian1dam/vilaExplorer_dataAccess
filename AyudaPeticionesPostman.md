**Guía de Peticiones CRUD para Lugar de Interés utilizando Postman**

Este documento está diseñado para ayudar a realizar peticiones CRUD (Crear, Leer, Actualizar, Desactivar/Eliminar) para la entidad `LugarInteres` utilizando Postman. A continuación se describen las diferentes peticiones HTTP, sus endpoints y ejemplos de uso.

### 1. **Crear Lugar de Interés (POST)**
- **Endpoint**: `/api/lugares/crear`
- **Método**: POST
- **URL**: `http://localhost:8080/api/lugares/crear`
- **Headers**:
  - `Content-Type: application/json`
- **Body (JSON)**:
  ```json
  {
    "nombreLugar": "Nuevo Parque Central",
    "descripcion": "Parque con áreas de juegos y zonas verdes",
    "imagen": "parque_central.jpg",
    "tipoLugar": { "idTipoLugar": 3 },
    "fechaAlta": "2024-10-30",
    "coordenadas": [
      { "latitud": 38.6254, "longitud": -0.2765 },
      { "latitud": 38.6260, "longitud": -0.2759 }
    ],
    "activo": true
  }
  ```
- **Resultado Esperado**: Se debería crear un nuevo lugar de interés con las coordenadas especificadas.

### 2. **Obtener un Lugar de Interés Activo (GET)**
- **Endpoint**: `/api/lugares/detalle/{id}`
- **Método**: GET
- **URL**: `http://localhost:8080/api/lugares/detalle/{id}`
  - Reemplaza `{id}` con el ID del lugar de interés que deseas consultar.
- **Resultado Esperado**: Devolverá un lugar de interés **activo** con el ID especificado. Si el lugar está desactivado, devolverá un error 404 (Not Found).

### 3. **Obtener un Lugar de Interés Completo (Para Administradores) (GET)**
- **Endpoint**: `/api/lugares/detalle-completo/{id}`
- **Método**: GET
- **URL**: `http://localhost:8080/api/lugares/detalle-completo/{id}`
  - Reemplaza `{id}` con el ID del lugar de interés que deseas consultar.
- **Resultado Esperado**: Devolverá un lugar de interés con el ID especificado sin importar si está activo o no.

### 4. **Obtener Todos los Lugares de Interés (Para Administradores) (GET)**
- **Endpoint**: `/api/lugares/todos`
- **Método**: GET
- **URL**: `http://localhost:8080/api/lugares/todos`
- **Resultado Esperado**: Devolverá una lista con todos los lugares de interés, incluyendo los activos e inactivos.

### 5. **Obtener Todos los Lugares de Interés Activos (GET)**
- **Endpoint**: `/api/lugares/activos`
- **Método**: GET
- **URL**: `http://localhost:8080/api/lugares/activos`
- **Resultado Esperado**: Devolverá una lista con todos los lugares de interés **activos**.

### 6. **Modificar Lugar de Interés (PUT)**
- **Endpoint**: `/api/lugares/modificar/{id}`
- **Método**: PUT
- **URL**: `http://localhost:8080/api/lugares/modificar/{id}`
  - Reemplaza `{id}` con el ID del lugar de interés que deseas modificar.
- **Headers**:
  - `Content-Type: application/json`
- **Body (JSON)**:
  ```json
  {
    "nombreLugar": "Parque Central Renovado",
    "descripcion": "Parque con más zonas verdes y nuevas áreas de juegos",
    "imagen": "parque_central_renovado.jpg",
    "tipoLugar": { "idTipoLugar": 3 },
    "fechaAlta": "2024-11-01",
    "coordenadas": [
      { "latitud": 38.6255, "longitud": -0.2767 },
      { "latitud": 38.6261, "longitud": -0.2760 }
    ]
  }
  ```
- **Resultado Esperado**: Actualiza el lugar de interés con el ID especificado.

### 7. **Desactivar Lugar de Interés (Borrado Lógico - PUT)**
- **Endpoint**: `/api/lugares/desactivar/{id}`
- **Método**: PUT
- **URL**: `http://localhost:8080/api/lugares/desactivar/{id}`
  - Reemplaza `{id}` con el ID del lugar de interés que deseas desactivar.
- **Resultado Esperado**: Cambiará el estado del lugar de interés a `inactivo` (borrado lógico).

### 8. **Eliminar Lugar de Interés de Forma Lógica (DELETE)**
- **Endpoint**: `/api/lugares/eliminar/{id}`
- **Método**: DELETE
- **URL**: `http://localhost:8080/api/lugares/eliminar/{id}`
  - Reemplaza `{id}` con el ID del lugar de interés que deseas eliminar lógicamente.
- **Resultado Esperado**: Cambia el campo `activo` del lugar de interés a `false`. Devuelve un mensaje indicando que el lugar ha sido eliminado lógicamente.

---
Este documento está pensado para ser expandido en el futuro con más detalles sobre otros métodos y entidades disponibles en la API. Se recomienda seguir la estructura descrita para facilitar el uso de Postman y la integración de nuevas funcionalidades a medida que el proyecto crezca.

