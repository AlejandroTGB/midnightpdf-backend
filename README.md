# MidnightPDF Backend

MMidnightPDF Backend es una API REST desarrollada con Spring Boot y Java, cuyo objetivo principal es transformar archivos PDF aplicando un **modo oscuro**. Esta funcionalidad facilita la lectura nocturna, reduce el cansancio visual y mejora la experiencia de usuarios que prefieren fondos oscuros al interactuar con documentos PDF.

## Características principales

- Recepción de archivos PDF mediante un endpoint HTTP POST.
- Conversión del PDF al **modo oscuro**: adaptación de colores de fondo y, en la medida de lo posible, del texto.
- Procesamiento automatizado de PDFs usando bibliotecas especializadas (PDFBox).
- Respuesta con el PDF modificado, listo para visualizarse en modo oscuro.
- Arquitectura limpia y escalable basada en Controller, Service y Repository

## Estructura del proyecto

```
src/
 └── main/
      └── java/
          └── com/
              └── midnightpdf/
                  ├── backend/
                  │    ├── controller/    # Controladores REST
                  │    ├── service/       # Lógica de negocio
                  │    
      └── resources/
          └── application.properties      # Configuración
```

## Instalación y ejecución

1. Clona el repositorio:
   ```
   git clone https://github.com/AlejandroTGB/midnightpdf-backend.git
   ```
2. Accede al directorio del proyecto:
   ```
   cd midnightpdf-backend
   ```
3. Instala las dependencias (Maven o Gradle).
4. Ejecuta la aplicación:
   ```
   ./mvnw spring-boot:run
   ```
   o
   ```
   ./gradlew bootRun
   ```

## Uso de la API

### Endpoint principal

- **POST** `/process-pdf`
  - Parámetros:
    - `pdf` (form-data, tipo archivo): PDF a transformar.
    - `mode` (form-data, texto): `"oscuro"` o `"claro"`.

#### Ejemplo con Postman local.

1. Selecciona método `POST` y URL: `http://localhost:8080/process-pdf`
2. En `Body`, usa `form-data`:
   - Campo `pdf` (tipo archivo): selecciona tu PDF.
   - Campo `mode` (tipo texto): escribe `"oscuro"` para convertir el PDF a modo oscuro, o `"claro"` para el modo tradicional.
3. Haz clic en `Send` y descarga el PDF transformado.

## Dependencias clave

- Spring Boot Starter Web
- Apache PDFBox
- Lombok

## Licencia

Este proyecto utiliza únicamente bibliotecas de código abierto. Consulta las licencias incluidas en el repositorio por cada dependencia utilizada.

---

## Autor

AlejandroTGB

---
