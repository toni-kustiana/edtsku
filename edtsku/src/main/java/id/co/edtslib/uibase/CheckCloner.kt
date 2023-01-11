package id.co.edtslib.uibase

import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.FragmentActivity
import id.co.edtslib.EdtsKu
import java.io.File

class CheckCloner(private val activity: FragmentActivity) {
    private val dualAppId999 = "999"
    private val dot = '.'
    private val basePath = "/data/user/"

    fun check(): Boolean {
        try {
            val version = activity.packageManager.getPackageInfo(activity.packageName, PackageManager.GET_META_DATA)
            if (version?.versionName != EdtsKu.versionName) {
                return true
            }
        }
        catch (e: Exception) {
            // ignore it
        }

        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            true
        } else {
            val path = activity.filesDir.path
            if (path.contains(dualAppId999)) {
                return true
            }

            if (!path.contains(EdtsKu.packageName)) {
                return true
            }

            val count = getDotCount(path)
            val packageDotCount = getPackageDotCount()
            val cloning = count > packageDotCount
            if (!cloning) {
                if (path.startsWith(basePath)) {
                    var min = -1
                    for (i in 0..99) {
                        val file = File("$basePath$i")
                        if (file.exists()) {
                            min = i
                            break
                        }
                    }

                    if (min >= 0) {
                        if (!path.startsWith("$basePath$min")) {
                            return true
                        }
                    }

                }
            }

            return cloning
        }
    }

    private fun getDotCount(path: String): Int {
        var count = 0
        val packageDotCount = getPackageDotCount()
        for (element in path) {
            if (count > packageDotCount) {
                break
            }
            if (element == dot) {
                count++
            }
        }
        return count
    }

    private fun getPackageDotCount(): Int {
        var i = 0
        for (cc in EdtsKu.packageName) {
            if (cc == '.') {
                i++
            }
        }

        return i
    }
}