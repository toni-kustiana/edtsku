package id.co.edtslib.util

import android.util.Base64
import id.co.edtslib.EdtsKu
import id.co.edtslib.tracker.Tracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.text.SimpleDateFormat
import java.util.*

class SecurityUtil {

    companion object {
        fun generateHashSha256(phoneNumber: String, publicKey: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val dateTime = dateFormat.format(Date())
            val unHashedAppId = "$phoneNumber$dateTime$publicKey"
            return hashWithSha256(unHashedAppId)
        }

        private fun hashWithSha256(combination: String): String {
            val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
            val encodedHash = digest.digest(combination.toByteArray(StandardCharsets.UTF_8))
            val hexString = StringBuilder(2 * encodedHash.size)
            for (i in encodedHash.indices) {
                val hex = Integer.toHexString(0xff and encodedHash[i].toInt())
                if (hex.length == 1) {
                    hexString.append('0')
                }
                hexString.append(hex)
            }
            return hexString.toString()
        }

        @Throws(IOException::class, NoSuchAlgorithmException::class, InvalidKeySpecException::class)
        fun getPrivateKeyFromKeyStore(privateKey: String): PrivateKey? {
            val message = StringBuilder()
            return try {
                val startPattern = EdtsKu.pemStartTag.toRegex()
                val startTag = startPattern.find(privateKey)

                val pem = if (startTag?.value != null) {
                    val endPattern = EdtsKu.pemEndTag.toRegex()

                    val endTag = endPattern.find(privateKey, startTag.range.first+startTag.value.length)
                    if (endTag?.value != null) {
                        privateKey.substring(startTag.range.first+startTag.value.length, endTag.range.first).trimStart()
                    }
                    else {
                        ""
                    }
                }
                else {
                    ""
                }
                val encoded = Base64.decode(pem, Base64.NO_WRAP)
                if (encoded.isEmpty()) {
                    message.append("$pem is empty.")
                }
                val keySpec = PKCS8EncodedKeySpec(encoded)
                val kf = KeyFactory.getInstance("RSA")
                val res = kf.generatePrivate(keySpec)
                if (res == null) {
                    message.append("generatePrivate is null. al: ${kf.algorithm} pr: ${kf.provider.name} ")
                }

                if (message.isNotEmpty()) {
                    trackerFailed(message.toString(), "enkripsi")
                }
                res
            } catch (e: Exception) {
                message.append("error: ${e.message}.")
                trackerFailed(message.toString(), "enkripsi")
                null
            } catch (e: Error) {
                message.append("error: ${e.message}.")
                trackerFailed(message.toString(), "enkripsi")
                null
            }

        }

        fun signWithPayload(payload: String, privateKey: PrivateKey?): String {
            val privateSignature = Signature.getInstance("SHA256withRSA")
            privateSignature.initSign(privateKey)
            privateSignature.update(payload.toByteArray(StandardCharsets.UTF_8))
            val signature = privateSignature.sign()
            return Base64.encodeToString(signature, Base64.NO_WRAP)
        }

        private fun trackerFailed(reason: String?, url: String?) {
            try {
                Tracker.trackSubmissionFailed(name = "api_failed",
                    category = "",
                    reason = reason,
                    details = url)
            }
            catch (ignore: Exception) {

            }
        }
    }

}