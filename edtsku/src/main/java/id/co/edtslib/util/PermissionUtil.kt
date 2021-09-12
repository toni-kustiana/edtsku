package id.co.edtslib.util

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

object PermissionUtil {
    const val CAMERA = 3520
    const val GPS = 3521
    const val WRITE = 3522

    fun cameraPermission(activity: FragmentActivity, already: () -> Unit) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA
            )
        } else {
           already()
        }
    }

    fun storagePermission(activity: FragmentActivity, already: () -> Unit) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE
            )
        } else {
            already()
        }
    }

    fun gpsPermission(activity: FragmentActivity, already: () -> Unit) {
        if (! isGpsAllowed(activity)) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                GPS
            )
        } else {
            already()
        }
    }

    fun isGpsAllowed(activity: FragmentActivity) = (ActivityCompat.checkSelfPermission(activity,
        Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            )
}