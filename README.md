# EdtsKu

![SlidingButton](https://i.ibb.co/GCcGMwH/edtslibs.png)
## Setup
### Gradle

Add this to your project level `build.gradle`:
```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
Add this to your app `build.gradle`:
```groovy
dependencies {
    implementation 'com.github.edstlib:edtsku:latest'
}
```

### Usage

- Create Class extend Application, and add to manifest android:name
```kotlin
    class App: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
```
- Register your module by extends EdtsStartApplication, like your api service module, and start edts application util
```groovy
    implementation "io.insert-koin:koin-core:3.1.2"
implementation "io.insert-koin:koin-android:3.1.2"
```

```kotlin
    class TestProjectStartApplication: EdtsStartApplication() {
    override fun additionModules() = listOf(appModule, repositoryModule, viewModelModule)
}
```

```kotlin
    class App: Application() {
    override fun onCreate() {
        super.onCreate()

        TestProjectStartApplication()
    }
}
```
### API Service
- Create folder assets on app folder add create edtsku.json to define base url and key header (if any)
```json
{"baseUrl":  "68747470733a2f2f656474736170702e696e646f6d61726574706f696e6b752e636f6d2f" , "key":  "6b752e636f6d2f"}
```


