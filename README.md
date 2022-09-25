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
    implementation 'com.github.edtslib:edtsku:latest'
}
```

### Koin MVVM
- Create Class extend Application, and add to manifest android:name
```xml
<application
    android:name=".App">
</application>
```
- Initialize EdtsKu library with this static method

```kotlin
// baseUrlApi: base urf of service api
// modules: koin module you have
fun init(application: Application, baseUrlApi: String, modules: List<Module>)

// with initial function
fun init(application: Application, baseUrlApi: String, modules: List<Module>,
         initEx:  (koin: KoinApplication) -> Unit )
```
Here is example code

```kotlin
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        EdtsKu.init(this, "http://api.edtsku.com",
            listOf(appModule, repositoryModule, viewModelModule))
    }
}
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

### SSL Pinner
- Edtsku provide to install ssl pinner

```kotlin
    EdtsKu.sslPinner = CommonUtil.hexToAscii(BuildConfig.SSL_PINNER)
EdtsKu.sslDomain = CommonUtil.hexToAscii(BuildConfig.SSL_DOMAIN)
```

### Header Apps Information
- Edstku send apps header to each api on json format. You must give edtsku version name and tablet information
```kotlin
    EdtsKu.versionName = "1.0.0"
EdtsKu.isTablet = false
```

### Base Activity
- Your activity must extends of BaseActivity

#### Abstract Method

```kotlin
// override fo define your initial activity
abstract fun setup()

// tracker page name string resource id
abstract fun getTrackerPageName(): Int

// false, user can't use back button for finish it, true vice versa
open fun canBack() = false

// see quit toast section
open fun isHomeActivity() = false
```
##### Quit Toast

![SlidingButton](https://i.ibb.co/qmD126B/toast.png)
For enable toast quit please override isHomeActivity on your home activity, and set to true
```kotlin
override fun isHomeActivity() = true
```


#### Utilitas
##### AndroidUtil
```kotlin
    // check is webview installed on
// success: callback function when webview already installed
fun checkWebAvailable(activity: FragmentActivity, success: () -> Unit)

// copy source to clipboard
// onSuccess: callback function when copied
fun copyToClipboard(context: Context, label: String, source: String, onSuccess: () -> Unit)

// force close soft keyboard
fun hideKeyboard(activity: FragmentActivity)

// read contents from asset file
// jsonFileName: json filename under assets folder
fun loadJSONFromAsset(context: Context, jsonFileName: String): String?

// force show soft keyboard
fun showKeyboard(activity: FragmentActivity, editText: EditText?)
```
##### DateTimeUtil
```kotlin
    // convert date to format like 2021-10-06T15:16:00.000Z 
fun getUTCString(date: Date): String
```

##### IntentUtil
```kotlin
    // open call intent
fun call(activity: FragmentActivity, phone: String)

// open android application setting intent
fun openApplicationSetting(activity: FragmentActivity)

// open map navigation intent from lat,lng to current position
fun openMapNavigation(activity: FragmentActivity, lat: Double, lng: Double)
```
#### Utilitas
##### AndroidUtil
```kotlin
    // check is webview installed on
// success: callback function when webview already installed
fun checkWebAvailable(activity: FragmentActivity, success: () -> Unit)

// copy source to clipboard
// onSuccess: callback function when copied
fun copyToClipboard(context: Context, label: String, source: String, onSuccess: () -> Unit)

// force close soft keyboard
fun hideKeyboard(activity: FragmentActivity)

// read contents from asset file
// jsonFileName: json filename under assets folder
fun loadJSONFromAsset(context: Context, jsonFileName: String): String?

// force show soft keyboard
fun showKeyboard(activity: FragmentActivity, editText: EditText?)
```
##### DateTimeUtil
```kotlin
    // convert date to date format like 22 Oktober 2020
fun getDateFmt(date: Date, context: Context): String

// convert date to time format like 00:00 WIB
fun getTimeFmt(date: Date, context: Context): String

// convert date format like 2021-10-06T15:16:00.000Z to date
fun getUTCDate(date: String): Date?

// convert date to format like 2021-10-06T15:16:00.000Z 
fun getUTCString(date: Date): String


```

##### IntentUtil
```kotlin
    // open call intent
fun call(activity: FragmentActivity, phone: String)

// open android application setting intent
fun openApplicationSetting(activity: FragmentActivity)

// open map navigation intent from lat,lng to current position
fun openMapNavigation(activity: FragmentActivity, lat: Double, lng: Double)
```

##### ImageUtils
```kotlin
    // encode file to base 64
fun encodeToBase64(file: String?, quality: Int = 100): String?
```
