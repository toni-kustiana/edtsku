package id.co.edtslib.data.source.local

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.edtslib.domain.model.TrackerData
import id.co.edtslib.domain.model.TrackerApps
import java.lang.Exception
import java.util.*

class TrackerLocalDataSource(sharedPreferences: SharedPreferences, app: Application): LocalDataSource<List<TrackerData>>(sharedPreferences) {
    override fun getKeyName(): String = "trackers"
    override fun getValue(json: String): List<TrackerData> = Gson().fromJson(json, object : TypeToken<List<TrackerData>>() {}.type)

    val apps = TrackerApps.create(app.applicationContext)
    val sessionId = String.format("%s-%d", UUID.randomUUID().toString(),
        Date().time)

    fun add(trackerData: List<TrackerData>) {
        try {
            val cached = getCached()
            val list = cached?.toMutableList() ?: mutableListOf()
            list.addAll(trackerData)

            save(list)
        }
        catch (e: Exception) {
            // nothing to do
        }
    }
}