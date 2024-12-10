# Guía para Peticiones de Puntuaciones

## Creación de Puntuación

### POST de Puntuación para **Plato**

**URL:**
```
http://localhost:8080/api/v0/puntuacion/crear
```

**Body:**
```json
{
  "puntuacion": 5,
  "usuario": {
    "idUsuario": 4
  },
  "idEntidad": 5,
  "tipoEntidad": "PLATO"
}
```

**Headers:**
```
Authorization: Bearer <hash>
```

---

### POST de Puntuación para **Lugar de Interés**

**URL:**
```
http://localhost:8080/api/v0/puntuacion/crear
```

**Body:**
```json
{
  "puntuacion": 4,
  "usuario": {
    "idUsuario": 7
  },
  "idEntidad": 10,
  "tipoEntidad": "LUGAR_INTERES"
}
```

**Headers:**
```
Authorization: Bearer <hash>
```

---

### POST de Puntuación para **Fiesta o Tradición**

**URL:**
```
http://localhost:8080/api/v0/puntuacion/crear
```

**Body:**
```json
{
  "puntuacion": 3,
  "usuario": {
    "idUsuario": 8
  },
  "idEntidad": 15,
  "tipoEntidad": "FIESTA_TRADICION"
}
```

**Headers:**
```
Authorization: Bearer <hash>
```

---

## Obtención de Puntuación Actualizada

### GET de Puntuaciones para Entidad

**URL:**
```
http://localhost:8080/api/v0/puntuacion/entidad/{tipoEntidad}/{idEntidad}
```

**URL:**
```
http://localhost:8080/api/v0/puntuacion/entidad/PLATO/1
```

- **Parámetros de ruta:**
    - `tipoEntidad`: Tipo de la entidad (por ejemplo, `PLATO`, `LUGAR_INTERES`, `FIESTA_TRADICION`).
    - `idEntidad`: ID de la entidad cuya puntuación quieres obtener.

**Headers:**
```
Authorization: Bearer <hash>
```

**Respuesta esperada:**
```json
{
  "puntuacionMedia": 4.5,
  "totalPuntuaciones": 30,
  "distribucion": {
    "5_estrellas": 20,
    "4_estrellas": 8,
    "3_estrellas": 2
  }
}
```

---

## Sugerencias para Implementaciones en Flutter y C#

### Flutter

1. **Cliente HTTP:**
   Utiliza `http` o `dio` para manejar las peticiones. Ejemplo con `dio`:
   ```dart
   import 'package:dio/dio.dart';

   class PuntuacionService {
     final Dio _dio = Dio();

     Future<void> crearPuntuacion(Map<String, dynamic> data, String token) async {
       final response = await _dio.post(
         'http://localhost:8080/api/v0/puntuacion/crear',
         data: data,
         options: Options(headers: {'Authorization': 'Bearer $token'}),
       );
       print(response.data);
     }

     Future<Map<String, dynamic>> obtenerPuntuacion(String tipoEntidad, int idEntidad, String token) async {
       final response = await _dio.get(
         'http://localhost:8080/api/v0/puntuacion/entidad/$tipoEntidad/$idEntidad',
         options: Options(headers: {'Authorization': 'Bearer $token'}),
       );
       return response.data;
     }
   }
   ```

2. **Actualización en la Interfaz:**
   Utiliza `FutureBuilder` o `StreamBuilder` para mostrar datos actualizados.

3. **Almacenamiento Seguro de Tokens:**
   Usa `flutter_secure_storage` para guardar y recuperar el token de autenticación.

### C#

1. **HttpClient:**
   Usa `HttpClient` para manejar las peticiones:
   ```csharp
   using System;
   using System.Net.Http;
   using System.Text;
   using System.Threading.Tasks;
   using Newtonsoft.Json;

   public class PuntuacionService {
       private readonly HttpClient _client;

       public PuntuacionService() {
           _client = new HttpClient();
       }

       public async Task CrearPuntuacionAsync(object data, string token) {
           var json = JsonConvert.SerializeObject(data);
           var content = new StringContent(json, Encoding.UTF8, "application/json");
           _client.DefaultRequestHeaders.Add("Authorization", $"Bearer {token}");

           var response = await _client.PostAsync("http://localhost:8080/api/v0/puntuacion/crear", content);
           response.EnsureSuccessStatusCode();
       }

       public async Task<string> ObtenerPuntuacionAsync(string tipoEntidad, int idEntidad, string token) {
           _client.DefaultRequestHeaders.Add("Authorization", $"Bearer {token}");
           var response = await _client.GetAsync($"http://localhost:8080/api/v0/puntuacion/entidad/{tipoEntidad}/{idEntidad}");
           response.EnsureSuccessStatusCode();

           return await response.Content.ReadAsStringAsync();
       }
   }
   ```

2. **Presentación de Datos:**
   Usa componentes como `DataGrid` o `ListView` para mostrar información en la interfaz.

3. **Gestor de Configuración:**
   Guarda configuraciones, como el token, en archivos locales o en `Settings`. 

