package com.chartered4.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by rishabhkhanna on 14/11/17.
 */
class MyRecyclerHelper<T>(var list: ArrayList<T>, var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : ItemTouchHelper.Callback() {

    var onDragListener: OnDragListener? = null
    var onSwipeListener: OnSwipeListener? = null
    private var isItemDragEnabled: Boolean = false
    private var isItemSwipeEnbled: Boolean = false

    fun onMoved(fromPos: Int, toPos: Int) {
        list.removeAt(toPos)
        mAdapter.notifyItemRemoved(toPos)
    }

    fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(list, fromPosition, toPosition)
        mAdapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        var dragFlags: Int = 0;
        var swipeFlags: Int = 0;
        if (isItemDragEnabled) {
            dragFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT;
        }
        if (isItemSwipeEnbled) {
            swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        onItemMoved(viewHolder.adapterPosition, target.adapterPosition)
        onDragListener?.onDragItemListener(viewHolder.adapterPosition, target.adapterPosition);
        return true;
    }

    /*override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        var dragFlags: Int = 0;
        var swipeFlags: Int = 0;
        if (isItemDragEnabled) {
            dragFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT;
        }
        if (isItemSwipeEnbled) {
            swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }*/

   /* override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        onItemMoved(viewHolder!!.adapterPosition, target!!.adapterPosition)
        onDragListener?.onDragItemListener(viewHolder.adapterPosition, target.adapterPosition);
//        onDragListener?.onDragItemListener(viewHolder?.adapterPosition, target?.adapterPosition)
        return true;
    }
*/

  /*  override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        onMoved(viewHolder!!.oldPosition, viewHolder.adapterPosition)
        onSwipeListener?.onSwipeItemListener()
    }
*/
    override fun isLongPressDragEnabled(): Boolean {
        return true;
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true;
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onMoved(viewHolder.oldPosition, viewHolder.adapterPosition)
        onSwipeListener?.onSwipeItemListener()
    }

    fun setRecyclerItemDragEnabled(isDragEnabled: Boolean): MyRecyclerHelper<T> {
        this.isItemDragEnabled = isDragEnabled
        return this;
    }

    fun setRecyclerItemSwipeEnabled(isSwipeEnabled: Boolean): MyRecyclerHelper<T> {
        this.isItemSwipeEnbled = isSwipeEnabled
        return this;
    }

    fun setOnDragItemListener(onDragListener: OnDragListener): MyRecyclerHelper<T> {
        this.onDragListener = onDragListener
        return this;
    }

    fun setOnSwipeItemListener(onSwipeListener: OnSwipeListener): MyRecyclerHelper<T> {
        this.onSwipeListener = onSwipeListener
        return this;
    }

}