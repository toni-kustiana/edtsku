package id.co.edtslib.util

object CommonUtil {
    fun hexToAscii(hex: String): String {
        val output = StringBuilder()
        var i = 0
        while (i < hex.length) {
            val str: String = hex.substring(i, i + 2)
            output.append(str.toInt(16).toChar())
            i += 2
        }

        return output.toString()
    }
}