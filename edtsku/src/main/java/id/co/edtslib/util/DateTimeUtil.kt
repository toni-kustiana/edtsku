package id.co.edtslib.util

import android.content.Context
import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {
    fun getUTCDate(date: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return try {
            dateFormat.parse(date)
        }
        catch (e: ParseException) {
            null
        }
    }

    fun getUTCString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.format(date)
    }

    fun getDateFmt(date: Date, context: Context): String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        return String.format("%d %s %d", calendar.get(Calendar.DATE),
            MonthName.values()[calendar.get(Calendar.MONTH)].toName(context),
            calendar.get(Calendar.YEAR))
    }

    fun getTimeFmt(date: Date, context: Context): String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        return String.format("%02d.%02d WIB", getHour(context, calendar), calendar.get(Calendar.MINUTE))
    }

    fun getHour(context: Context, cal: Calendar) =
        if (DateFormat.is24HourFormat(context)) {
            cal.get(Calendar.HOUR_OF_DAY)
        }
        else {
            cal.get(Calendar.HOUR_OF_DAY)
        }
}