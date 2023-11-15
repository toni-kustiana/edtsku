package id.co.edtslib.util

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.SigningInfo
import android.os.Build
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import java.lang.Exception
import java.security.MessageDigest

fun Context.isValidSignature(packageName: String, sha1: String?) =
    getSignatures(packageName)?.any { it == sha1 } == true

// 0: valid
// -1: invalid sha 1
// -3: failed fetch remote config
// -2: remote config empty
fun Context.checkValidSignature(packageName: String,
                                remoteConfigKey: String,
                                onCallback: (code: Int, signatures: List<String>?, sha1: String?) -> Unit) {
    val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    mFirebaseRemoteConfig.fetch().addOnCompleteListener {
        if (it.isSuccessful) {
            val sha1 = mFirebaseRemoteConfig.getString(remoteConfigKey)
            if (sha1.isNotEmpty()) {
                val signatures = getSignatures(packageName)
                val filter = signatures?.filter {sign -> sign == sha1 }
                if (filter?.isNotEmpty() == true) {
                    onCallback(0, signatures, sha1)
                }
                else {
                    onCallback(-1, signatures, sha1)
                }
            }
            else {
                onCallback(-2, null, sha1)
            }
        }
        else {
            onCallback(-3, null, null)
        }
    }
}

fun Context.getSignatures(packageName: String): List<String>? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val sig = packageManager?.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(
                PackageManager.GET_SIGNING_CERTIFICATES.toLong()))?.signingInfo
            getSignatures(sig)
        }
        else
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val sig = packageManager?.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)?.signingInfo
                getSignatures(sig)
            }
            else {
                val sig = packageManager?.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)?.signatures
                sig?.map {
                    val digest = MessageDigest.getInstance("SHA")
                    digest.update(it.toByteArray())
                    bytesToHex(digest.digest())
                }
            }

    }
    catch (ignore: Exception) {
        null
    }
}

private fun getSignatures(sig: SigningInfo?) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        if (sig?.hasMultipleSigners() == true) {
            // Send all with apkContentsSigners
            sig.apkContentsSigners.map {
                val digest = MessageDigest.getInstance("SHA")
                digest.update(it.toByteArray())
                bytesToHex(digest.digest())
            }
        } else {
            // Send one with signingCertificateHistory
            sig?.signingCertificateHistory?.map {
                val digest = MessageDigest.getInstance("SHA")
                digest.update(it.toByteArray())
                bytesToHex(digest.digest())
            }
        }
    }
    else {
        null
    }

private fun bytesToHex(bytes: ByteArray): String {
    val hexArray = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    val hexChars = CharArray(bytes.size * 2)
    var v: Int
    for (j in bytes.indices) {
        v = bytes[j].toInt() and 0xFF
        hexChars[j * 2] = hexArray[v.ushr(4)]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}