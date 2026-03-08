# Allure

An Android application built with a scalable, multi-module Clean Architecture. The project serves as a production-ready template demonstrating best practices in Android development using Kotlin, Jetpack Compose, and a full suite of modern libraries.

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Build Variants](#build-variants)
- [Code Quality](#code-quality)
- [Getting Started](#getting-started)

> For a deep dive into the architecture, module structure, and core design patterns see [docs/Architecture.md](docs/Architecture.md).

---

## Tech Stack

| Category | Library / Tool | Version |
|---|---|---|
| Language | Kotlin | — |
| UI | Jetpack Compose + Material3 | `1.8.3` / `1.3.2` |
| Dependency Injection | Hilt | `2.56.2` |
| Networking | Retrofit + OkHttp | `3.0.0` / `5.0.0-alpha.14` |
| Local DB | Room | `2.7.2` |
| Preferences | DataStore (Preferences) | `1.1.1` |
| Typed Storage | Proto DataStore | `4.27.3` |
| Serialization | Kotlinx Serialization | `1.6.2` |
| Navigation | Navigation Compose | `2.7.7` |
| Async | Kotlin Coroutines + Flow | — |
| HTTP Debugging | Chucker | `4.0.0` |
| Immutable Collections | Kotlinx Immutable Collections | `0.3.7` |

---

## Build Variants

The project uses **two flavor dimensions**:

| Dimension | Flavors | Purpose |
|---|---|---|
| `store` | `google`, `huawei` | Target app store and push notification provider |
| `app` | `consumer`, `provider` | App role / user type |

Combined with three build types:

| Build Type | Description |
|---|---|
| `debug` | Development; logging and Chucker enabled |
| `release` | Production; minified, certificate-pinned |
| `releaseExternalQA` | QA distribution build with release signing |

Example variants: `consumerGoogleDebug`, `providerHuaweiRelease`

Store-specific code lives in `app/src/google/` and `app/src/huawei/` source sets (e.g. `DataProvider`).

---

## Code Quality

| Tool | Purpose |
|---|---|
| **ktlint** | Kotlin code style enforcement |
| **Spotless** | Auto-formatter (integrates ktlint) |
| **Detekt** | Static analysis for code smells |
| **Dokka** | KDoc API documentation generation |
| **Ben Manes Versions Plugin** | Detects outdated dependencies (`update-dependencies`) |

All tools are applied uniformly via `SharedLibraryGradlePlugin` and the app-level `build.gradle.kts`.

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- JDK 11+
- Android SDK with API 35

### Build

```bash
# Clone the repository
git clone <repo-url>
cd Allure

# Build a debug variant
./gradlew assembleConsumerGoogleDebug

# Run code quality checks
./gradlew ktlintCheck
./gradlew detekt
./gradlew spotlessCheck

# Check for dependency updates
./gradlew dependencyUpdates

# Generate KDoc documentation
./gradlew dokkaHtml
```

### App Configuration

| Property | Value |
|---|---|
| Application ID | `com.realkarim.allure` |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 35 (Android 15) |
| Version | `1.0` (code: `1`) |
| JVM Target | Java 11 |
