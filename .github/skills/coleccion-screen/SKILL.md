---
name: nueva-pantalla
description: Úsala cuando haya que diseñar e implementar una pantalla nueva de Elemental Cards con su lógica de presentación, integrándola con la navegación, ViewModel y repositorio existentes.
---

# Crear una pantalla nueva en Elemental Cards

Sigue los patrones reales del proyecto y limita los cambios a lo que necesita la pantalla. Para una pantalla con estado y cartas, la referencia principal es `app/src/main/java/com/example/elementalcards/view/Battle.kt`: observa estado con `collectAsStateWithLifecycle()` y recibe su ViewModel como parámetro. Para el estilo sencillo de Landing, consulta también `app/src/main/java/com/example/elementalcards/view/Landing.kt`.

## Flujo de trabajo requerido

El pedido original de Colección especifica iterar con aprobación del usuario. Mantén estas etapas separadas: presenta lo que propones y espera aprobación antes de avanzar a la siguiente; no implementes varias etapas en una sola pasada.

1. **Analiza y propone.** Lee primero `Route.kt`, `MainActivity.kt`, `ElementalCardApp.kt`, `Card.kt`, `CardDAO.kt`, `CardRepository.kt`, los ViewModels y las pantallas relacionadas. Explica una por una las clases, funciones y cambios que propones, indicando qué responsabilidades cubre cada uno. Para Colección, comprueba que `CollectionViewModel` está vacío y que `Route.Collection` todavía no se registra. Pide aprobación antes de tocar código.
2. **Propón los cambios de datos.** Reutiliza `Card`, `Element`, las consultas existentes de `CardDAO` y el repositorio único. Actualmente DAO ya tiene `getUnlockedCards()`, `getDeckCards()`, conteos y `setInDeck()`, pero esas operaciones no están expuestas como API de colección en `CardRepository`. Presenta qué métodos faltan o cuáles se pueden reutilizar; no cambies el modelo ni el esquema por anticipado. Pide aprobación y espera antes de modificar la capa de datos.
3. **Diseña la pantalla.** Propón el contenido y las interacciones y espera aprobación. Para la colección, toma como referencia visual la imagen: resumen/contadores, filtros o encabezados para FIRE/WATER/ICE, y cartas seleccionables. Ignora por completo el panel flotante “Sobre”. No copies los contadores de ejemplo de la imagen. El requisito indicado es equipar hasta 15 cartas y usar tres listas LazyColumn, una por elemento; valida el límite mediante estado, sin confiar solo en la UI.
4. **Implementa el ViewModel.** Después de aprobar las capas anteriores, completa `../../../app/src/main/java/com/example/elementalcards/viewmodel/CollectionViewModel.kt`. Inyecta `CardRepository` mediante el constructor, sigue el patrón de estado privado MutableStateFlow/público StateFlow y usa `viewModelScope` para operaciones suspendidas. Mantén la lógica fuera del composable. Si hay una decisión no resuelta sobre copias, pregúntala antes de fijar el comportamiento.
5. **Conecta y refactoriza lo mínimo.** Crea `app/src/main/java/com/example/elementalcards/view/Collection.kt` con una única función de pantalla `@Composable`, que reciba su ViewModel y una acción de regreso. Añade el destino de `Route.Collection` al `NavHost` de `StartApp` y conecta la acción de entrada desde Landing. Reutiliza `CollectionViewModelFactory` que ya está declarado y la instancia que `MainActivity` ya crea. Cambia Battle o la validación de inicio solo si es indispensable para impedir empezar con menos de 15 cartas equipadas; explica el cambio y solicita la aprobación prevista antes de hacerlo.
6. **Prueba y presenta.** Añade pruebas enfocadas para selección/equipamiento, el límite de 15 y los contadores acordados; evita depender de una prueba de suma como evidencia. Verifica compilación y pruebas locales con `.\gradlew.bat testDebugUnitTest` y `.\gradlew.bat assembleDebug`. Si añades UI tests, ejecútalos en un dispositivo/emulador disponible. Informa qué quedó implementado, qué verificaste y cualquier decisión pendiente.

## Archivos esperados para Colección

- `app/src/main/java/com/example/elementalcards/view/Collection.kt` — nuevo composable de pantalla.
- `../../../app/src/main/java/com/example/elementalcards/viewmodel/CollectionViewModel.kt` — completar la clase y conservar su factory actual.
- `app/src/main/java/com/example/elementalcards/view/Route.kt` — registrar Collection y conectar navegación de entrada/salida.
- `app/src/main/java/com/example/elementalcards/data/repository/CardRepository.kt` — exponer las operaciones de colección que el ViewModel necesite, reutilizando el DAO.
- `app/src/main/java/com/example/elementalcards/data/local/CardDAO.kt` — solo si falta una consulta u operación aprobada; ya existen flujos de desbloqueadas/equipadas, conteos y `setInDeck`.
- `app/src/main/res/values/strings.xml` — textos nuevos de pantalla.
- Pruebas en `app/src/test/java/com/example/elementalcards/` o `app/src/androidTest/java/com/example/elementalcards/`, según si se prueba lógica local o UI instrumentada.

No cambies `Card` ni `AppDatabase` salvo que el diseño aprobado demuestre que el modelo actual no puede cumplir un requisito. Si eso ocurre, explica primero la limitación: `quantity` agrupa copias en una fila y `inDeck` es un único booleano para toda esa fila.

## Reglas de implementación

- Pasa los datos desde `CollectionViewModel` a `CollectionScreen`; no consultes Room desde Compose.
- Reutiliza `Card.painter()` de `app/src/main/java/com/example/elementalcards/ui/CardArt.kt` para representar cartas existentes.
- Usa primero los componentes Compose y recursos que ya utiliza Landing/Battle. No agregues dependencias para reproducir un detalle visual si puede componerse con los elementos existentes.
- Conserva las reglas existentes de batalla y la persistencia salvo cambios indispensables, previamente explicados y aprobados.
- No supongas que la pantalla de colección está ya implementada porque existan `Route.Collection`, `CollectionViewModel` o consultas del DAO: revisa la navegación y el contenido de cada clase.
- Antes de crear resúmenes o límites basados en `quantity`, resuelve con el usuario cómo contar y equipar duplicados. El DAO actual cuenta filas para los conteos existentes; eso no equivale necesariamente al número de copias.
