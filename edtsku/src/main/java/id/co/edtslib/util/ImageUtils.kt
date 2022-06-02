package id.co.edtslib.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageUtils {
    fun encodeToBase64(file: String?, quality: Int = 100): String? {
        val bmp = BitmapFactory.decodeFile(file)
        val bos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, bos)
        val bt = bos.toByteArray()
        return Base64.encodeToString(bt, Base64.DEFAULT)
    }
}