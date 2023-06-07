package id.co.edts.edtsku.example

import android.content.Context
import android.util.AttributeSet
import id.co.edtslib.uibase.EdtsWebView

class MyWebView: EdtsWebView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun userAgentAdditionalInfo(): Map<String, Any?> {
        val map = HashMap<String, Any?>()
        map["applicationNameForUserAgent"] = "klikindomaretmobile"

        return map
    }
}