package id.co.edtslib.uibase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<viewBinding : ViewBinding>: Fragment() {
    //private val baseViewModel: BaseViewModel by viewModel()

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> viewBinding

    abstract fun getTrackerPageName(): Int

    open fun isInAdapter() = false
    open fun canBack() = true

    private var pageView: String? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: viewBinding
        get() = _binding as viewBinding

    fun isNotNullBinding() = _binding != null
/*
    protected fun isViewModelInitialized() = ::viewModel.isInitialized

    open fun show() {
        if (isViewModelInitialized()) {
            if (getScreenName() > 0 && context != null) {
                viewModel.trackPage(getString(getScreenName()))
            }

            viewModel.trackFlush()
        }
    }

    open fun hide() {
        if (getScreenName() > 0) {
            viewModel.trackUserImpressions(getString(getScreenName()))
        }
    }
*/
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
/*
        if (getScreenName() > 0 && ! isInAdapter()) {
            pageView = getString(getScreenName())
            if (pageView != null) {
                viewModel.trackPage(pageView!!)
            }
        }

        viewModel.trackFlush()
 */
    }

    abstract fun setup()

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}