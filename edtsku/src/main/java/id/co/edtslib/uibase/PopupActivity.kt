package id.co.edtslib.uibase

import android.graphics.drawable.ColorDrawable
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import id.co.edtslib.R

abstract class PopupActivity<viewBinding: ViewBinding>: BaseActivity<viewBinding>() {
    override fun canBack() = true

    abstract fun setupPopup()
    override fun setup() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actionBar = supportActionBar
        val colorDrawable = ColorDrawable(ContextCompat.getColor(this, getStatusBarColor()))
        actionBar?.setBackgroundDrawable(colorDrawable)

        setupPopup()
    }

    protected open fun getStatusBarColor() = R.color.actionBarColor

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}