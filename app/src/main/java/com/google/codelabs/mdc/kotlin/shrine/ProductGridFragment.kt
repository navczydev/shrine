package com.google.codelabs.mdc.kotlin.shrine

import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.*

class ProductGridFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // Set up the toolbar.
        val view = inflater.inflate(R.layout.shr_product_grid_fragment, container, false)
        (activity as AppCompatActivity).setSupportActionBar(view.app_bar)
        activity?.let {
            view.app_bar.setNavigationOnClickListener(NavigationIconClickListener(
                    it,
                    view.product_grid,
                    AccelerateDecelerateInterpolator(),
                    context?.let { ctx -> ContextCompat.getDrawable(ctx, R.drawable.shr_branded_menu) }, // Menu open icon
                    context?.let { ctx -> ContextCompat.getDrawable(ctx, R.drawable.shr_close_menu) })) // Menu close icon

        }

        // Set up the RecyclerView
        with(view.recycler_view) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            val adapterItems = ProductCardRecyclerViewAdapter(
                    ProductEntry.initProductEntryList(resources))
            adapter = adapterItems
        }
        with(resources) {
            val largePadding = getDimensionPixelSize(R.dimen.shr_product_grid_spacing)
            val smallPadding = getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small)
            view.recycler_view.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
        }
        // Set cut corner background for API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.product_grid.background = context?.getDrawable(R.drawable.shr_product_grid_background_shape)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
