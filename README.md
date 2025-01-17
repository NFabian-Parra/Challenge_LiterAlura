# LiterAlura - Consola de Gestión de Libros y Autores

## Descripción del Proyecto

**LiterAlura** es una aplicación interactiva basada en consola que permite gestionar información sobre libros y autores. Con esta herramienta, los usuarios pueden buscar libros por título, listar libros y autores almacenados, explorar autores vivos en un año específico, y mucho más.

Esta aplicación está diseñada para ser intuitiva y sencilla, proporcionando un flujo eficiente para la administración y visualización de datos literarios.

---

## Características Principales

1. **Búsqueda de Libros por Título**

   - Encuentra libros específicos por su título e incluye detalles como el autor, idioma y número de descargas.

2. **Listado de Libros Almacenados**

   - Muestra todos los libros registrados en la base de datos.

3. **Listado de Autores Almacenados**

   - Despliega información de los autores, incluyendo fechas de nacimiento, fallecimiento y libros asociados.

4. **Listado de Autores Vivos en un Año Específico**

   - Permite conocer qué autores estaban vivos en un año ingresado por el usuario.

5. **Filtrado de Libros por Idioma**

   - Ofrece un listado de libros en idiomas seleccionados (Español, Inglés o Francés).

6. **Top 5 de Libros Más Descargados**

   - Muestra los libros más populares según el número de descargas.

---

## Requisitos del Sistema

- **Java 17 o superior**
- **Spring Boot**
- **Dependencias:**
  - Spring Framework
  - Maven

---

## Estructura del Proyecto

El proyecto sigue una arquitectura bien estructurada utilizando las mejores prácticas:

```
src/
├── main/
│   ├── java/
│   │   ├── com/alura/literAlura/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── service/
│   │   │   │   ├── impl/
│   │   │   └── LiterAluraApplication.java
│   └── resources/
│       ├── application.properties
```

---

## Cómo Ejecutar la Aplicación

1. **Clona el Repositorio:**

   ```bash
   git clone https://github.com/NFabian-Parra/Challenge_LiterAlura.git
   ```

2. **Compila y Ejecuta el Proyecto:**

   ```bash
   mvn spring-boot:run
   ```

3. **Interacción:**

   - Sigue las instrucciones en la consola para utilizar las funcionalidades del menú.

---

## Capturas de Pantalla

### Menú Principal:

```
--- Menú ---
1. Buscar libros por título.
2. Listado de libros almacenados.
3. Listado de autores almacenados.
4. Listado de autores vivos en determinado año.
5. Listado de libros por idioma.
6. Top 5 de los más descargados.
7. Salir.
Ingrese una opción:
```

---

## Contribuciones

¡Las contribuciones son bienvenidas! Si deseas mejorar el proyecto, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama:
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. Realiza tus cambios y haz un commit:
   ```bash
   git commit -m "Agrega nueva funcionalidad"
   ```
4. Envía tus cambios:
   ```bash
   git push origin feature/nueva-funcionalidad
   ```
5. Abre un Pull Request.

---

## Autores

- **Nestor Fabian Parra** - Desarrollador Principal.

---

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para obtener más detalles.

---

¡Gracias por usar LiterAlura! 📚🌐

