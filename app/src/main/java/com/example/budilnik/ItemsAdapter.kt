package com.example.budilnik

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item.view.*

class ItemsAdapter(
    private val items: ArrayList<Item> = arrayListOf(),
    private val onSwitchClick: (Item) -> Unit,
    private val onItemLongClick: (Item) -> Unit,
    private val onItemClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)

        return ItemViewHolder(view)
    }

    fun addItems(data: List<Item>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(item: Item) {
            view.title.text = item.title
            view.time.text = item.time
            view.onOff.isChecked = item.isOn

            view.onOff.setOnClickListener {
                onSwitchClick(item)
            }

            view.setOnLongClickListener {
                onItemLongClick(item)
                return@setOnLongClickListener true
            }

            view.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}