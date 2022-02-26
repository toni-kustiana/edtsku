package id.co.edtslib.uibase

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import id.co.edtslib.R

class ConfirmationHorizDialog private constructor(context: Context) : ConfirmationDialog(context) {
    companion object {
        private var dialog: ConfirmationHorizDialog? = null
        fun show(activity: FragmentActivity, imageResId: Int, title: String,
                          subTitle: String, positiveButtonText: String, negativeButtonText: String,
                          positiveButtonListener: View.OnClickListener,
                          negativeButtonListener: View.OnClickListener?) {

            if (dialog != null) {
                close()
            }

            dialog = ConfirmationHorizDialog(activity)
            dialog?.setCancelable(false)
            dialog?.show(
                imageResId, title, subTitle,
                positiveButtonText, negativeButtonText,
                positiveButtonListener, negativeButtonListener, false
            )

            val width = Resources.getSystem().displayMetrics.widthPixels * 0.9f
            dialog?.window?.setLayout(
                width.toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        fun close() {
            try {
                dialog?.dismiss()
                dialog = null
            }
            catch (e: Exception) {
                // ignore it
            }
        }
    }

    override fun layout() = R.layout.dialog_confirmation_horiz
}