package id.co.edtslib.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {
    fun getUTCString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.format(date)
    }
}