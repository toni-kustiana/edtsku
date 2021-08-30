package id.co.edtslib.util

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset


object AndroidUtil {
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String? {
        try {
            val manager = context.assets
            val inputStream = manager.open(jsonFileName)
            val size = inputStream.available()
            if (size > 0) {
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                return String(buffer, Charset.forName("UTF-8"))
            }
            else {
                return null
            }

        }
        catch (e: IOException) {
            return null
        }
    }

}