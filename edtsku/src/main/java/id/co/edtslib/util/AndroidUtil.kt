package id.co.edtslib.util

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.webkit.WebViewCompat
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

    fun checkWebAvailable(activity: FragmentActivity, success: () -> Unit) {
        if (WebViewCompat.getCurrentWebViewPackage(activity) != null) {
            success()
        }
        else {
            Toast.makeText(activity, "Terjadi kesalahan: Web service tidak ditemukan",
                Toast.LENGTH_LONG).show()
        }
    }

    fun hideKeyboard(activity: FragmentActivity) {
        val imm = activity.getSystemService(FragmentActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        activity.currentFocus?.clearFocus()
    }

    fun showKeyboard(activity: FragmentActivity, editText: EditText?) {
        editText?.requestFocus()
        editText?.post {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun copyToClipboard(context: Context, label: String, source: String, onSuccess: () -> Unit) {
        try {
            val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java)
            val clip = ClipData.newPlainText(label,source)
            clipboard?.setPrimaryClip(clip)

            onSuccess()
        }
        catch (e: SecurityException) {
            // nothing to
        }
    }

    fun isCloning(activity: FragmentActivity, packageName: String): Boolean {
        if (activity.packageName != packageName) {
            return true
        }

        val path = activity.filesDir.path
        val p = packageName.split(".")

        if (path.contains("999")) {
            return true
        }

        if (!path.contains(packageName)) {
            return true
        }

        var count = 0
        for (i in path.indices) {
            if (path[i] == '.') {
                count++
            }
        }

        return count > p.size
    }
}