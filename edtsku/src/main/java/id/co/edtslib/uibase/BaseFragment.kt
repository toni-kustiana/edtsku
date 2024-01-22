package id.co.edtslib.uibase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import id.co.edtslib.tracker.Tracker
import java.util.*

abstract class BaseFragment<viewBinding : ViewBinding>: Fragment() {
    //private val baseViewModel: BaseViewModel by viewModel()
    private val now = Date().time

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> viewBinding

    abstract fun getTrackerPageName(): String?
    fun getTrackerPageId() = if (getTrackerPageName() == null) null else "${getTrackerPageName()}_${now}"

    open fun isInAdapter() = false
    open fun canBack() = true

    private var pageView: String? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: viewBinding
        get() = _binding as viewBinding

    fun isNotNullBinding() = _binding != null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()

        if (getTrackerPageName() != null) {
            Tracker.trackPage(getTrackerPageName()!!, getTrackerPageId()!!)
        }

        initTrackerSetup()
    }

    abstract fun setup()

    protected open fun initTrackerSetup() { }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

    override fun onResume() {
        super.onResume()

        if (getTrackerPageName() != null) {
            Tracker.resumePage(getTrackerPageName()!!, getTrackerPageId()!!)
        }
    }
}