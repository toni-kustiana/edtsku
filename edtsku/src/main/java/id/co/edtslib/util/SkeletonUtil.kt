package id.co.edtslib.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.ViewSkeletonScreen
import id.co.edtslib.R

class SkeletonUtil {
    companion object {
        fun showRecyclerView(recyclerView: RecyclerView,
                             adapter: RecyclerView.Adapter<*>,
                             layout: Int) = showRecyclerView(recyclerView,
            adapter, layout, 10)

        fun showRecyclerView(recyclerView: RecyclerView,
                             adapter: RecyclerView.Adapter<*>,
                             layout: Int, count: Int): RecyclerViewSkeletonScreen? =
            try {
                Skeleton.bind(recyclerView)
                    .adapter(adapter)
                    .load(layout)
                    .angle(0)
                    .color(R.color.colorShimmer)
                    .count(count)
                    .show()
            }
            catch (e: IllegalArgumentException) {
                null
            }

        fun showScreen(viewGroup: ViewGroup, layout: Int): ViewSkeletonScreen? {
            return try {
                Skeleton.bind(viewGroup)
                    .load(layout)
                    .angle(0)
                    .color(R.color.colorShimmer)
                    .show()
            } catch (e: ClassCastException) {
                null
            }
        }
    }
}