package id.co.edtslib.uibase

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import id.co.edtslib.R

class ConfirmationDialog private constructor(context: Context) : Dialog(context) {
    private var imageResId = 0
    private var title = ""
    private var subTitle = ""
    private var positiveButtonText = ""
    private var negativeButtonText = ""
    private var positiveButtonListener: View.OnClickListener? = null
    private var negativeButtonListener: View.OnClickListener? = null

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

            if (dialog == null) {
                dialog = ConfirmationDialog(activity)
                dialog?.setCancelable(false)
                dialog?.show(
                    imageResId, title, subTitle,
                    positiveButtonText, negativeButtonText,
                    positiveButtonListener, negativeButtonListener
                )

                val width = Resources.getSystem().displayMetrics.widthPixels * 0.9f
                dialog?.window?.setLayout(
                    width.toInt(),
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }

        }

        fun close() {
            dialog?.dismiss()
            dialog  = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_confirmation)
        window?.decorView?.setBackgroundResource(android.R.color.transparent)

        val imageView = findViewById<ImageView>(R.id.imageView)
        if (imageResId != 0) {
            imageView.setImageResource(imageResId)
        }
        else {
            imageView.isVisible = false
        }

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = title

        val tvSubTitle = findViewById<TextView>(R.id.tvSubTitle)
        tvSubTitle.text = HtmlCompat.fromHtml(subTitle, HtmlCompat.FROM_HTML_MODE_LEGACY)

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
             negativeButtonListener: View.OnClickListener?) {
        this.imageResId = imageResId
        this.title = title
        this.subTitle = subTitle
        this.positiveButtonText = positiveButtonText
        this.negativeButtonText = negativeButtonText
        this.positiveButtonListener = positiveButtonListener
        this.negativeButtonListener = negativeButtonListener

        super.show()
    }
}