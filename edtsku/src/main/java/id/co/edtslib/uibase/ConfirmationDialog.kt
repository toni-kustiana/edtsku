package id.co.edtslib.uibase

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import id.co.edtslib.R

open class ConfirmationDialog protected constructor(context: Context) : Dialog(context) {
    private var imageResId = 0
    private var title = ""
    private var subTitle = ""
    private var positiveButtonText = ""
    private var negativeButtonText = ""
    private var positiveButtonListener: View.OnClickListener? = null
    private var negativeButtonListener: View.OnClickListener? = null
    private var reverse = false

    companion object {
        private var dialog: ConfirmationDialog? = null
        fun showOneButton(activity: FragmentActivity, imageResId: Int, title: String,
                 subTitle: String, buttonText: String,
                 onClickListener: View.OnClickListener) = showTwoButton(activity, imageResId,
            title, subTitle, buttonText, "", onClickListener,
            null)

        fun showTwoButton(activity: FragmentActivity, imageResId: Int, title: String,
                          subTitle: String, positiveButtonText: String, negativeButtonText: String,
                          positiveButtonListener: View.OnClickListener,
                          negativeButtonListener: View.OnClickListener?) {

            if (dialog != null) {
                close()
            }

            dialog = ConfirmationDialog(activity)
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

        fun showTwoButtonReverse(activity: FragmentActivity, imageResId: Int, title: String,
                          subTitle: String, positiveButtonText: String, negativeButtonText: String,
                          positiveButtonListener: View.OnClickListener,
                          negativeButtonListener: View.OnClickListener?) {

            if (dialog != null) {
                close()
            }

            dialog = ConfirmationDialog(activity)
            dialog?.setCancelable(false)
            dialog?.show(
                imageResId, title, subTitle,
                positiveButtonText, negativeButtonText,
                positiveButtonListener, negativeButtonListener, true
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

    open fun layout() = if (reverse) R.layout.dialog_confirmation_reverse else
        R.layout.dialog_confirmation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layout())
        window?.decorView?.setBackgroundResource(android.R.color.transparent)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle.isVisible = title.isNotEmpty()
        tvTitle.text = title

        val tvSubTitle = findViewById<TextView>(R.id.tvSubTitle)
        tvSubTitle.text = HtmlCompat.fromHtml(subTitle, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val imageView = findViewById<ImageView>(R.id.imageView)
        if (imageResId != 0) {
            imageView.setImageResource(imageResId)
        }
        else {
            imageView.isVisible = false

            tvTitle.gravity = Gravity.LEFT
            tvSubTitle.gravity = Gravity.LEFT
        }

        val tvPositiveButton = findViewById<TextView>(R.id.tvPositiveButton)
        tvPositiveButton.text = positiveButtonText
        tvPositiveButton.setOnClickListener(positiveButtonListener)

        val tvNegativeButton = findViewById<TextView>(R.id.tvNegativeButton)
        tvNegativeButton.text = negativeButtonText
        tvNegativeButton.isVisible = negativeButtonText.isNotEmpty()

        if (negativeButtonListener != null) {
            tvNegativeButton.setOnClickListener(negativeButtonListener)
        }

    }

    fun show(imageResId: Int, title: String, subTitle: String, positiveButtonText: String,
             negativeButtonText: String, positiveButtonListener: View.OnClickListener,
             negativeButtonListener: View.OnClickListener?, reverse: Boolean) {
        this.imageResId = imageResId
        this.title = title
        this.subTitle = subTitle
        this.positiveButtonText = positiveButtonText
        this.negativeButtonText = negativeButtonText
        this.positiveButtonListener = positiveButtonListener
        this.negativeButtonListener = negativeButtonListener
        this.reverse = reverse

        super.show()
    }
}