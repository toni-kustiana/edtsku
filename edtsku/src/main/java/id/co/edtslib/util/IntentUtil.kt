package id.co.edtslib.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import java.lang.Exception

object IntentUtil {
    fun openApplicationSetting(activity: FragmentActivity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    fun openMapNavigation(activity: FragmentActivity, lat: Double, lng: Double) {
        try {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=$lat,$lng")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            activity.startActivity(mapIntent)
        }
        catch (e: Exception) {
            Toast.makeText(activity, "Hp tidak mendukung fitur membuka navigasi peta", Toast.LENGTH_LONG).show()
        }
    }

    fun call(activity: FragmentActivity, phone: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phone")
            activity.startActivity(intent)
        }
        catch (e: Exception) {
            Toast.makeText(activity, "Hp tidak mendukung fitur membuka navigasi peta", Toast.LENGTH_LONG).show()
        }
    }

    fun openPlayStore(context: Context, packageName: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
        }
    }

    fun openLink(context: Context, url: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse(url)))
        } catch (e: ActivityNotFoundException) {
            // ignore it
        }
    }

}