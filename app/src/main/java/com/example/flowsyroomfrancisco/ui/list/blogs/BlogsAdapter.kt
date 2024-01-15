package com.example.flowsyroomfrancisco.ui.list.blogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flowsyroomfrancisco.R
import com.example.flowsyroomfrancisco.databinding.ItemBlogsBinding
import com.example.flowsyroomfrancisco.domain.model.Blog
import com.example.recyclerviewretrofitfrancisco.framework.SwipeGesture


class BlogsAdapter(
    val context: Context,
    val actions: BlogsActions
) : ListAdapter<Blog, BlogsAdapter.ItemViewHolder>(DiffCallback()) {

    interface BlogsActions {
        fun itemHasClicked(blog: Blog)
        fun onDelete(blog: Blog)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_blogs, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)

    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemBlogsBinding.bind(itemView)

        fun bind(item: Blog) {
            itemView.setOnClickListener {
                actions.itemHasClicked(item)
            }

            with(binding) {
                tvId.text = item.id.toString()
                tvNombre.text = item.name
            }
        }


    }


    val swipeGesture = object : SwipeGesture(context){
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT){
                val position = viewHolder.bindingAdapterPosition
                actions.onDelete(currentList[position])
            }
        }
    }

}



class DiffCallback : DiffUtil.ItemCallback<Blog>() {
    override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem == newItem
    }
}