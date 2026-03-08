# Architecture

This document covers the architectural decisions, module structure, and core design patterns used in Allure.

---

## Table of Contents

- [Overview](#overview)
- [Module Structure](#module-structure)
- [Core Concepts](#core-concepts)
  - [StateRenderer](#staterenderers-o)
  - [OutCome](#outcomet)
  - [AsyncUseCase](#asyncusecasei-r)
  - [AppNavigator](#appnavigator)
  - [OkHttp Interceptor Chain](#okhttp-interceptor-chain)
  - [Proto DataStore](#proto-datastore)
  - [SharedLibraryGradlePlugin](#sharedlibrarygradleplugin)

---

## Overview

The project follows **Clean Architecture** with a strict separation of concerns across three layers:

```
Presentation  ──▶  Domain  ──▶  Data
```

- **Presentation**: Compose screens, ViewModels, `StateRenderer`
- **Domain**: Use cases (`AsyncUseCase`), domain models, `OutCome<T>` result wrapper
- **Data**: Retrofit services, OkHttp interceptors, mappers, data sources

The UI layer uses an **MVI-inspired** pattern with explicit `Input`, `UiState`, and `Output` types per feature:

```
User Interaction
      │
      ▼
  ViewModel.setInput(LoginInput)
      │
      ▼
  Update UiState / Emit Output
      │
      ▼
  StateRenderer / Navigation event
```

---

## Module Structure

```
Allure/
├── app/                        # Application entry point, DI wiring, flavor sources
│
├── features/
│   ├── login/                  # Login feature module
│   ├── signup/                 # Sign-up feature module
│   └── home/                   # Home/dashboard feature module
│
├── core/
│   ├── domain/                 # Domain models, use-case interfaces, OutCome<T>
│   ├── data/                   # Networking layer (Retrofit, OkHttp, interceptors)
│   ├── presentation/           # StateRenderer, shared UI state components
│   ├── navigator/              # Navigation abstraction (AppNavigator)
│   ├── datastore/              # Preferences DataStore (AppSettings)
│   └── protodatastore/         # Proto DataStore (session tokens, typed preferences)
│
└── buildSrc/                   # Centralized build logic and dependency management
    └── src/main/kotlin/
        ├── buildutils/         # BuildConfig, BuildTypes, BuildDimensions, BuildCreator
        ├── dependency/         # Dependencies, DependenciesVersions, DependencyProvider
        ├── flavor/             # FlavorTypes, BuildFlavor
        ├── plugin/             # SharedLibraryGradlePlugin, BuildPlugins, custom plugins
        ├── signing/            # SigningTypes, BuildSigning
        ├── release/            # ReleaseConfig (versionCode, versionName)
        └── test/               # TestBuildConfig, TestDependencies
```

---

## Core Concepts

### `StateRenderer<S, O>`

A sealed class in `:core:presentation` that drives all screen states in a type-safe, composable way. `S` is the view state type and `O` is the success output type.

```kotlin
sealed class StateRenderer<out S, O> {
    class ScreenContent<S, O>(val viewState: S)                                   : StateRenderer<S, O>()
    class LoadingPopup<S, O>(val viewState: S, ...)                               : StateRenderer<S, O>()
    class LoadingFullScreen<S, O>(val viewState: S, ...)                          : StateRenderer<S, O>()
    class ErrorPopup<S, O>(val viewState: S, val errorMessage: ErrorMessage)      : StateRenderer<S, O>()
    class ErrorFullScreen<S, O>(val viewState: S, val errorMessage: ErrorMessage) : StateRenderer<S, O>()
    class Empty<S, O>(...)                                                        : StateRenderer<S, O>()
    class Success<S, O>(val output: O)                                            : StateRenderer<S, O>()
}
```

Usage in a composable screen:

```kotlin
StateRenderer.of(statRenderer = stateRenderer, retryAction = { ... }) {
    onUiState      { state  -> /* render normal screen */  }
    onLoadingState { state  -> /* render with overlay */   }
    onSuccessState { output -> /* navigate or act */       }
    onErrorState   { state  -> /* render with error */     }
    onEmptyState   {          /* render empty state */     }
}
```

---

### `OutCome<T>`

A sealed result wrapper used at the domain/data boundary. Implements the Visitor pattern via `accept`:

```kotlin
sealed class OutCome<T> {
    class Success<T>(val data: T)                  : OutCome<T>()
    class Error<T>(val errorMessage: ErrorMessage) : OutCome<T>()
    class Empty<T>()                               : OutCome<T>()
}
```

---

### `AsyncUseCase<I, R>`

Base class for all use cases. Exposes a clean `execute` API with typed callbacks and delegates to a single abstract `run` method:

```kotlin
abstract class AsyncUseCase<I, R> : UseCase<R> {
    suspend fun execute(
        input: I,
        success: suspend (R) -> Unit = {},
        empty:   suspend () -> Unit = {},
        error:   suspend (ErrorMessage) -> Unit = {},
    )

    abstract suspend fun run(input: I): OutCome<R>
}
```

---

### `AppNavigator`

A decoupled navigation abstraction backed by a Kotlin `Channel`. ViewModels and screens emit navigation events without holding a direct `NavController` reference.

```kotlin
interface AppNavigator {
    fun navigateUp(): Boolean
    fun popBackStack()
    fun navigate(destination: String, builder: NavOptionsBuilder.() -> Unit = { ... }): Boolean
    val destinations: Flow<NavigatorEvent>
}
```

`AppNavigatorImpl` is a `@Singleton` that uses a `Channel<NavigatorEvent>` so each event is consumed by exactly one collector.

---

### OkHttp Interceptor Chain

The `:core:data` module provides a layered interceptor chain assembled in `NetworkModule`:

| Interceptor | Purpose |
|---|---|
| `HeaderInterceptor` | Injects common headers into every request |
| `AuthenticationInterceptor` | Attaches Bearer token; on 401 refreshes tokens via `SessionService` using a `Mutex` to prevent concurrent refresh races |
| `ConnectivityInterceptor` | Fails fast when there is no network connection |
| Chucker | In-app HTTP traffic inspector (debug only; no-op in release) |
| OkHttp Logging | Logs request/response body (debug `OkHttpClientProviderImpl` only) |

`OkHttpClientProvider` has separate implementations per build type (`debug`, `release`, `releaseExternalQA`), enabling certificate pinning in production without polluting debug builds.

---

### Proto DataStore

`:core:protodatastore` manages typed persistent storage using Protocol Buffers:

- **`SessionDataStore`** — stores/retrieves `accessToken` and `refreshToken`
- **`PreferencesDataStore`** — stores typed user preferences

---

### `SharedLibraryGradlePlugin`

A custom Gradle plugin in `buildSrc` applied to every library module. It centralises:

- Compile SDK, min SDK, Java/JVM version
- Signing configs (debug / release / releaseExternalQA)
- Build types and product flavor dimensions
- Code quality plugins (ktlint, Spotless, Detekt, Dokka, dependency version checker)

This eliminates boilerplate and guarantees consistency across all modules.
