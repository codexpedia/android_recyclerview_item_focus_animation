package com.example.listitemfocusanimation

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.list_item.view.*


class ItemListAdapter(private val itemList: List<String>): RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    private var previousFocusedViewHolder: ViewHolder? = null
    private var previouslyFocusedPos = 0
    private var currentlyFocusedPos = 0
    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(layoutView)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.d("ItemListAdapter", "onAttachedToRecyclerView ")
        this.recyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("ItemListAdapter", "onBindViewHolder position: $position")

        holder.itemView.setOnFocusChangeListener { focusedView, hasFocus ->
            Log.d("ItemListAdapter", "onBindViewHolder OnFocusChangeListener position: $position")
            updateFocusPositions(holder, hasFocus, position)
            startFocusAnimation(holder, hasFocus)
        }

        holder.tvTopic.text = itemList[position]
    }

    override fun getItemCount() = itemList.size

    private fun startFocusAnimation(holder: ViewHolder, hasFocus: Boolean) {
        Log.d("ItemListAdapter", "startFocusAnimation hasFocus: $hasFocus, currentlyFocusedPos: $currentlyFocusedPos, previouslyFocusedPos: $previouslyFocusedPos")

        if (hasFocus) {
            previousFocusedViewHolder?.let {
                val moveOutAnimSet = if (currentlyFocusedPos > previouslyFocusedPos) R.anim.move_out_down else R.anim.move_out_up
                val moveOutAnim = AnimationUtils.loadAnimation(it.itemBg.context, moveOutAnimSet)
                moveOutAnim.fillAfter = true
                moveOutAnim.duration = 250
                it.itemBg.startAnimation(moveOutAnim)
                Log.d("ItemListAdapter", "startFocusAnimation, moving out from previousViewHolder: $it")
            }

            val moveInAnimSet = if (currentlyFocusedPos > previouslyFocusedPos) R.anim.move_in_from_top else R.anim.move_in_from_bottom
            val moveInAnim = AnimationUtils.loadAnimation(holder.itemBg.context, moveInAnimSet)
            moveInAnim.fillAfter = true
            moveInAnim.duration = 400
            holder.itemBg.startAnimation(moveInAnim)
            Log.d("ItemListAdapter", "startFocusAnimation, moving into the  currentViewHolder: $holder")
        }
    }

    private fun updateFocusPositions(viewHolder: ViewHolder, hasFocus: Boolean, position: Int) {
        if (hasFocus) {
            previouslyFocusedPos = currentlyFocusedPos
            currentlyFocusedPos = position
        } else {
            previousFocusedViewHolder = viewHolder
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var itemBg: View
        var tvTopic: TextView

        init {
            itemView.setOnClickListener(this)
            tvTopic = itemView.topic
            itemBg = itemView.view_bg
        }

        override fun onClick(view: View) {
            Toast.makeText(view.context, "Clicked Position = " + adapterPosition, Toast.LENGTH_SHORT).show()
        }
    }
}