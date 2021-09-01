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
- Create folder assets on app folder add create edtsku.json to define base url
```json
{"baseUrl":  "68747470733a2f2f656474736170702e696e646f6d61726574706f696e6b752e636f6d2f", "trackerBaseUrl": "68747470733a2f2f617369612d736f75746865617374322d69646d2d636f72702d6465762e636c6f756466756e6374696f6e732e6e6574"}
```

### Auth

##### Login
- We provide HttpHeaderLocalSource class for set token, key and other http header. Add class to your login repository, when login is success, add bearer token to http header local source. Here is example

```kotlin
class AccountRepository(private val remoteDataSource: AccountRemoteDataSource,
    private val httpHeaderLocalSource: HttpHeaderLocalSource):
    IAccountRepository {
    override fun login(username: String, password: String): Flow<Result<LoginResponse?>> = flow {
        emit(Result.loading())
        val response = remoteDataSource.login(username, password)

        when(response.status) {
            Result.Status.SUCCESS -> {
                if (response.data?.id_token != null) {
                    httpHeaderLocalSource.setBearerToken(response.data?.id_token!!)
                }
                emit(Result.success(response.data))
            }
            else -> {
                emit(Result.error<LoginResponse?>(response.code, response.message, null))
            }
        }
    }
}
```
##### Check is Logged
- On your above repository add method which call httpheaderlocalsource isLogged
```kotlin
    override fun isLogged(): Flow<Boolean> {
        emit(httpHeaderLocalSource.isLogged())
    }
```

### Base Activity
- Your activity must extends of BaseActivity

##### Quit Toast

![SlidingButton](https://i.ibb.co/qmD126B/toast.png)
For enable toast quit please override isHomeActivity on your home activity, and set to true
```kotlin
override fun isHomeActivity() = true
```




