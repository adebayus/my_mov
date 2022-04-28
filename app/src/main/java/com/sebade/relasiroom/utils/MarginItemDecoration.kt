package com.sebade.relasiroom.utils

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration (private val spaceSize: Int, val orientation: Int?) :
    RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == parent.childCount) {
                    right = 0
                }
                right = spaceSize
            }
        } else {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = spaceSize
                }
                bottom = spaceSize
            }
        }
    }
}
