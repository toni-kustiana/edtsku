package id.co.edtslib.data.source.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HttpHeaderLocalSource(sharedPreferences: SharedPreferences):
    LocalDataSource<MutableMap<String, String?>>(sharedPreferences) {

    override fun getKeyName(): String = "HttpHeader"
    override fun getValue(json: String): MutableMap<String, String?> =
        Gson().fromJson(json, object : TypeToken<MutableMap<String, String?>>() {}.type)

    fun setHeader(key: String, value: String?) {
        val headers = getCached()
        if (value == null) {
            headers?.remove(key)
        }
        else {
            headers?.put(key, value)
        }
        save(headers)
    }

    fun setBearerToken(token: String?) {
        if (token != null) {
            val accessToken = String.format("Bearer %s", token)
            setHeader("Authorization", accessToken)
        }
    }

    fun isLogged(): Boolean{
        val headers = getCached()
        return headers?.get("Authorization") != null
    }

    fun logout() {
        val headers = getCached()
        headers?.remove("Authorization")
        save(headers)
    }
}