package id.co.edtslib.uibase

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.co.edtslib.databinding.DialogBottomSheetBinding

abstract class BaseBottomSheetDialog<viewBinding : ViewBinding>: BottomSheetDialog {
    private lateinit var bindingDialog: DialogBottomSheetBinding

    private var _binding: ViewBinding? = null

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> viewBinding
    abstract fun title(): String
    abstract fun setupContent()

    open fun closeInOutside() = true
    open fun closable() = true

    @Suppress("UNCHECKED_CAST")
    protected val binding: viewBinding
        get() = _binding as viewBinding

    constructor(context: Context): super(context)
    constructor(context: Context, theme: Int): super(context, theme)
    constructor(context: Context,
                cancelable: Boolean,
                cancelListener: DialogInterface.OnCancelListener?):
            super(context, cancelable, cancelListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingDialog = DialogBottomSheetBinding.inflate(layoutInflater)
        setContentView(bindingDialog.root)

        setCancelable(closable() && closeInOutside())

        bindingDialog.flClose.setOnClickListener {
            dismiss()
        }
        bindingDialog.flClose.isVisible = closable()
        bindingDialog.tvTitle.text = title()

        _binding = bindingInflater.invoke(layoutInflater, bindingDialog.root, false)
        bindingDialog.flContent.addView(binding.root)

        setupContent()
    }
}