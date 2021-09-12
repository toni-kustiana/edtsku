package id.co.edtslib.util

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.FragmentActivity

object IntentUtil {
    fun openApplicationSetting(activity: FragmentActivity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    fun openMapNavigation(activity: FragmentActivity, lat: Double, lng: Double) {
        val gmmIntentUri =
            Uri.parse("google.navigation:q=$lat,$lng")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        activity.startActivity(mapIntent)
    }
}