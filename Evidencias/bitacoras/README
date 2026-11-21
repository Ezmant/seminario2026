# Documentación Técnica y de Proyecto: Portfolio3D (AppSeminario)

* **Desarrollado por:** Glitch Labs
* **Plataforma:** Android (Nativo)
* **Paquete Base:** `com.seminario2026.appseminario`
* **Tipo de Aplicación:** Portfolio, Galería y Showcase para Artistas y Creadores 3D

---

## 1. Introducción y Resumen del Proyecto

**Portfolio3D** (implementada técnicamente bajo el módulo base `AppSeminario`) es una aplicación móvil nativa para Android diseñada específicamente para artistas, modeladores y creadores de contenido tridimensional. Su objetivo principal es proporcionar una galería inmersiva y profesional donde los usuarios puedan exhibir sus renders y modelos 3D, organizar sus proyectos y conectar con otros profesionales o posibles clientes.

La aplicación resuelve la necesidad de los artistas 3D de contar con un portfolio de bolsillo optimizado para dispositivos móviles, respetando la alta fidelidad visual de sus trabajos mediante una interfaz moderna, reactiva y desacoplada.

---

## 2. Requerimientos y Funcionalidades Principales

### Módulo de Autenticación (`presentation.auth`)
* **Pantalla de Bienvenida (Splash Screen):** Presentación inicial con la identidad visual de Glitch Labs.
* **Flujo Completo de Acceso:** Sistema integral que abarca tres pantallas principales:
  * **Inicio de Sesión (Login):** Acceso seguro mediante correo electrónico y contraseña.
  * **Registro de Usuario:** Creación de cuentas nuevas solicitando nombre de usuario, correo y contraseña.
  * **Recuperación de Contraseña (Reset Password):** Flujo dedicado para restablecer credenciales mediante envío de enlace al correo registrado.
* **Validación Reactiva en Cliente:** Control previo de formato de correo electrónico (mediante expresiones regulares) y longitud mínima de seguridad para contraseñas (6 caracteres) antes de ejecutar peticiones de red.

### Módulo de Exploración (Home / Galería Feed)
* **Feed Principal:** Cuadrícula asimétrica (*Staggered Grid*) optimizada para visualizar renders y proyectos destacados de la comunidad sin recortar sus proporciones originales.
* **Búsqueda y Filtrado:** Barra de búsqueda integrada y filtros rápidos mediante *Chips* de categorización (ej. "Todos", "3D Renders", "Mobiliario", "Personajes").

### Módulo de Gestión de Perfil
* **Perfil de Creador:** Vista detallada con avatar, biografía, nivel de experiencia (ej. "Modelador 3D Sr") y etiquetas de herramientas utilizadas (Blender, Unreal Engine, ZBrush, etc.).
* **Organización por Pestañas (Tabs):** Clasificación dinámica del portafolio personal en *Mis Renders*, *Borradores* y *Favoritos*.

### Módulo de Carga de Proyectos
* **Acción Rápida:** Botón de acción flotante (FAB) global para crear, etiquetar y subir nuevos renders o modelos al portfolio.

---

## 3. Arquitectura y Stack Tecnológico

El proyecto sigue los estándares modernos recomendados por Google para desarrollo Android, priorizando la escalabilidad, el bajo acoplamiento y la testabilidad.

* **Entorno de Desarrollo:** Android Studio.
* **Lenguaje de Programación:** Kotlin.
* **Interfaz de Usuario (UI):** Jetpack Compose bajo un paradigma 100% declarativo.
* **Patrón de Arquitectura (Clean Architecture + MVVM):**
  * **Capa de Presentación (`presentation`):** Implementa el patrón MVVM (Model-View-ViewModel). Gestiona el estado de la interfaz de forma unidireccional y reactiva mediante `StateFlow` y `asStateFlow()`, garantizando la supervivencia de los datos ante cambios de configuración (como rotación de pantalla).
    * *Ejemplo de implementación (`presentation.auth`):* Uso de interfaces selladas (`sealed interface AuthUiState`) para modelar estados finitos y seguros (`Idle`, `Loading`, `Success`, `Error`), controlados por `AuthViewModel`.
  * **Capa de Dominio (`domain`):** Contiene las reglas de negocio puras, modelos de datos principales y casos de uso.
  * **Capa de Datos (`data`):** Gestiona los repositorios, fuentes de datos remotas y persistencia local.
* **Conectividad y Red:** Retrofit para el manejo de solicitudes HTTP y consumo de APIs REST externas complementarias.

---

## 4. Diseño y Experiencia de Usuario (UI/UX)

La interfaz gráfica fue diseñada por Glitch Labs enfocándose en ceder el protagonismo absoluto a las piezas visuales y renders 3D.

* **Sistema de Diseño:** Material Design 3 (Material You).
* **Identidad Visual:** Uso de un tono púrpura vibrante como color de acento principal (*Primary Color*), contrastando con superficies neutras para evitar fatiga visual y resaltar la paleta de colores de cada render.
* **Tematización:** Interfaz diseñada e implementada en **Modo Claro**, priorizando la legibilidad, el contraste limpio y una experiencia visual uniforme en todas las pantallas.
* **Componentes:** Uso de `Cards` con bordes redondeados, `TopAppBars` integradas, `OutlinedTextFields` con feedback de error en tiempo real y navegación fluida entre pantallas.

---

## 5. Metodología y Registro de Progreso

El desarrollo se lleva a cabo bajo un enfoque iterativo y modular, documentado mediante bitácoras de avance técnico:

* **Análisis de Requerimientos:** Definición de módulos de galería, perfil y autenticación.
* **Diseño de Arquitectura Base:** Estructuración de paquetes (`com.seminario2026.appseminario`) bajo Clean Architecture + MVVM.
* **Implementación de Flujo de Autenticación:**
  * Creación de `AuthUiState` (`Idle`, `Loading`, `Success`, `Error`).
  * Desarrollo de `AuthViewModel` con `StateFlow`, validaciones por Regex (`EMAIL_REGEX`), control de longitud de contraseñas y lógica para `login`, `register` y `resetPassword`.
  * Verificación y compilación exitosa mediante Gradle (`app:assembleDebug`).
* **Maquetación de Galería y Perfil (Jetpack Compose):** Diseño de tarjetas de renders y cuadrícula asimétrica.
* **Integración con Servicios Firebase:** Conexión de Auth, Firestore y Storage.
* **Pruebas y Optimización:** Depuración de memoria en carga de imágenes de alta resolución y rendimiento de scroll.

---

## 6. Pruebas y Aseguramiento de la Calidad (Testing QA)

Con el objetivo de garantizar la estabilidad, el correcto funcionamiento de los flujos críticos y una experiencia de usuario fluida en dispositivos Android, se estableció un plan de pruebas dividido en distintos niveles de verificación.

### 6.1. Tipos de Pruebas Aplicadas
* **Pruebas de Compilación y Construcción (Build Testing):** Verificación periódica mediante Gradle (`app:assembleDebug`) para asegurar la ausencia de errores de sintaxis, conflictos de dependencias o problemas de integración entre módulos.
* **Pruebas Funcionales y de Validación:** Comprobación de la lógica de negocio implementada en los ViewModels (especialmente en `AuthViewModel`), validando respuestas correctas ante datos válidos e inválidos (formato de correo con `EMAIL_REGEX`, longitud de contraseñas y transiciones de estado en `AuthUiState`).
* **Pruebas de Interfaz y Usabilidad (UI/UX):** Revisión visual en distintos tamaños de pantalla y densidades de píxeles en Modo Claro, asegurando la correcta legibilidad, alineación de componentes de Material Design 3 y fluidez del scroll en la cuadrícula asimétrica (*Staggered Grid*).
* **Pruebas de Persistencia de Estado:** Verificación del comportamiento reactivo mediante `StateFlow` frente a cambios de configuración del dispositivo (como la rotación de pantalla) para evitar la pérdida de datos ingresados por el usuario.

### 6.2. Matriz de Casos de Prueba Principales

| ID | Módulo | Escenario de Prueba | Resultado Esperado | Estado |
| :--- | :--- | :--- | :--- | :--- |
| **TC-01** | Autenticación | Ingresar un correo electrónico con formato inválido en el Login o Registro. | El sistema bloquea el envío y muestra un mensaje de error en tiempo real en el `OutlinedTextField`. | Aprobado |
| **TC-02** | Autenticación | Ingresar una contraseña de menos de 6 caracteres. | La validación en cliente impide continuar e indica el requisito mínimo de seguridad. | Aprobado |
| **TC-03** | Autenticación | Solicitar recuperación de contraseña con un correo válido. | El estado de `AuthUiState` transiciona correctamente (`Idle -> Loading -> Success`) y notifica al usuario. | Aprobado |
| **TC-04** | Estado/UI | Rotar la pantalla del dispositivo mientras se completan los campos de acceso. | Los datos ingresados y el estado actual se conservan sin reiniciarse gracias al `ViewModel`. | Aprobado |
| **TC-05** | Exploración (Home) | Desplazarse por el Feed principal y aplicar filtros mediante Chips de categoría. | La cuadrícula asimétrica renderiza las tarjetas sin recortar proporciones y actualiza el listado sin trabas. | En prueba |
| **TC-06** | Perfil | Navegar entre las pestañas Mis Renders, Borradores y Favoritos. | La transición entre pestañas es fluida y muestra únicamente el contenido correspondiente a cada categoría. | En prueba |

### 6.3. Entorno de Pruebas
* **Herramientas:** Emulador oficial de Android Studio (AVD) y dispositivos físicos Android.
* **Criterio de Aceptación:** Ningún módulo se considera finalizado hasta superar la compilación sin errores y aprobar los casos de prueba funcionales críticos sin cierres inesperados (*crashes*).
