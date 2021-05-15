package com.smqpro.projectexample.util

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.smqpro.projectexample.R
import com.smqpro.projectexample.model.dto.Product


typealias Position = Int

fun Activity.showSnackbar(text: String) {
    val snack: Snackbar = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
    setCorrectAnimationAndPosition(snack)
    snack.show()
}

fun Fragment.showSnackbar(text: String) {
    requireActivity().showSnackbar(text)
}

private fun setCorrectAnimationAndPosition(snackbar: Snackbar) {
    val params = snackbar.view.layoutParams
    if (params is CoordinatorLayout.LayoutParams) {
        params.gravity = Gravity.TOP
    } else if (params is FrameLayout.LayoutParams) {
        params.gravity = Gravity.TOP
    }

    snackbar.view.layoutParams = params
    snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
}

fun RecyclerView.onSwipeToDeleteListener(block: (Position) -> Unit) {
    val itemTouchHelperCallback =
        object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                block(viewHolder.bindingAdapterPosition)
            }
        }

    val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
    itemTouchHelper.attachToRecyclerView(this)
}