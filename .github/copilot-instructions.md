# Instrucciones para trabajar en Elemental Cards

## Fuente de verdad

Estas instrucciones describen el código que existe y los requisitos de colección indicados por el usuario; no des por implementado lo que todavía es solo un requisito. Si una decisión de producto o de modelo no está resuelta aquí ni en el código, déjala como pregunta y consulta antes de inventar una regla.

## Stack y versiones

- Android, Kotlin y Jetpack Compose; la interfaz usa Material 3.
- Gradle Wrapper 9.5.0, Android Gradle Plugin 9.3.3, Kotlin 2.2.10 y KSP 2.2.10-2.0.2.
- SDK mínimo 24, objetivo 37, compile SDK 37 (minor API 1), Java 11 y versión de la app 1.0.
- Compose BOM 2026.02.01; Navigation Compose 2.8.7; Room 2.8.5; Lifecycle 2.11.0; Activity Compose 1.13.0.
- Las versiones y aliases se mantienen en `gradle/libs.versions.toml`; las dependencias de la app, en `app/build.gradle.kts`.

## Arquitectura y paquetes

El namespace base es `com.example.elementalcards`. La app sigue un flujo MVVM sencillo:

- `data/local`: entidad Room `Card`, enum `Element`, `CardDAO` y `AppDatabase`.
- `data/repository`: `CardRepository`, capa que usa la app para acceder a las cartas y coordinar operaciones del DAO.
- `view`: pantallas Compose y `Route`/`StartApp`.
- `viewmodel`: lógica de presentación y negocio, basada en `ViewModel`; cada ViewModel se construye con su factory.
- `ui`: arte de cartas en `CardArt.kt` y recursos del tema en `ui/theme`.
- En el paquete base: `ElementalCardApp` proporciona la base de datos y el repositorio; `MainActivity` configura tema, ViewModels y contenido.

Los nombres actuales de los archivos Kotlin coinciden con su clase o responsabilidad principal. Las pantallas existentes se llaman `LandingScreen` y `BattleScreen`; sigue ese estilo para una pantalla nueva y mantén una responsabilidad de pantalla por archivo.

## Estado, navegación, dependencias y datos

- Los ViewModels exponen estado observable con `StateFlow` y mantienen privados sus `MutableStateFlow`; `BattleScreen` los observa con `collectAsStateWithLifecycle()`. Conserva este patrón al conectar el estado de una pantalla.
- Las operaciones suspendidas del ViewModel se ejecutan dentro de `viewModelScope`.
- `Route.kt` define rutas como objetos de `Route`, con `path` y `label`, y `StartApp` las registra dentro de un `NavHost`. Actualmente solo están registradas Landing y Battle. `Route.Collection` ya existe, pero no está conectada al `NavHost`; el botón de colección de Landing todavía es un TODO.
- No hay un framework de inyección de dependencias. `ElementalCardApp` crea de forma lazy `AppDatabase` y `CardRepository`; los factories obtienen la instancia de `Application` y pasan el repositorio a cada ViewModel. Reutiliza ese mecanismo.
- `Card` tiene `id`, `element`, `value`, `quantity` e `inDeck`. Room guarda filas únicas por combinación `element`/`value`; una recompensa repetida incrementa `quantity`.
- `CardDAO` expone flujos de cartas y operaciones de selección. El acceso desde la aplicación debe pasar por `CardRepository`, no desde una pantalla directamente al DAO.
- El repositorio inicializa cinco valores de FIRE, WATER e ICE con `inDeck = true`. Las recompensas se agregan con `inDeck = false`. La lógica de batalla usa el mazo equipado.
- `Card.painter()` en `ui/CardArt.kt` selecciona los drawables existentes por elemento y valor. Prefiérelo para mostrar cartas en vez de duplicar el mapeo de recursos.

## Interfaz y convenciones

- Mantén la interfaz con Compose y los componentes ya presentes en `Landing.kt` y `Battle.kt` (por ejemplo, `Column`, `Row`, `Text`, `Image`, `Button` y listas lazy). Antes de introducir otro componente o una dependencia, comprueba si se puede resolver con los elementos disponibles; añade algo nuevo solo si es necesario.
- Usa `R.color.*` y `R.string.*` para los recursos existentes. Los colores de referencia incluyen negro, naranja y tonos claros de azul/naranja. Añade textos de interfaz a `app/src/main/res/values/strings.xml` en lugar de fijarlos en el composable.
- En Kotlin, conserva nombres de tipos en PascalCase, funciones y propiedades en lowerCamelCase; los recursos XML usan nombres snake_case. Mantén el formato local y evita refactors ajenos al cambio.
- Para la colección, el usuario pide tres listas LazyColumn (una por elemento), selección de hasta 15 cartas y contadores de colección y mazo. La imagen adjunta es una referencia visual, no una fuente de valores: omite el panel flotante “Sobre” y no copies sus números. Procura que la pantalla se integre visualmente con Landing y Battle.

## Límites y cuestiones pendientes

- No afirmes que la pantalla Colección ya funciona: `CollectionViewModel` existe, pero está vacío; no hay una pantalla Collection registrada ni operaciones de colección expuestas por el repositorio.
- El requisito de no iniciar una batalla con menos de 15 cartas equipadas aún no está aplicado: Landing inicia la batalla directamente. Si se implementa, debe validarse con el estado real de la colección.
- El DAO cuenta filas (`COUNT(*)`), mientras que `quantity` representa copias repetidas. **Pregunta:** ¿los contadores de cartas desbloqueadas/equipadas deben contar copias (`SUM(quantity)`) o tipos distintos (filas)? **Pregunta:** ¿se pueden equipar algunas copias de una carta repetida o se equipa el tipo completo? El booleano `inDeck` actual solo permite seleccionar o deseleccionar una fila completa.
- No añadas un segundo origen de verdad, no accedas al DAO desde UI, no dupliques el mapeo de arte de cartas, ni cambies las reglas de batalla o la persistencia fuera de lo necesario y aprobado para la tarea.
- No introduzcas una biblioteca de DI, una dependencia Compose alternativa ni una migración de Room por conveniencia. Confirma la necesidad antes de hacerlo.
- No ocultes fallos con valores por defecto o excepciones ignoradas. La inicialización actual de batalla imprime excepciones; no reproduzcas ese manejo opaco en código nuevo.
- No añadas pantallas, botones o la interacción del panel “Sobre” que el usuario pidió ignorar.

## Pruebas

El proyecto configura JUnit 4 para pruebas locales y Compose UI Test/Espresso para pruebas instrumentadas. Solo existe `ExampleUnitTest` (prueba de suma), así que no hay aún precedentes de pruebas de ViewModel o de UI. Al cambiar comportamiento, añade pruebas enfocadas donde corresponda y verifica al menos con `.\gradlew.bat testDebugUnitTest`; las pruebas instrumentadas requieren un dispositivo o emulador.
