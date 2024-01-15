package com.example.flowsyroomfrancisco.ui.list.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flowsyroomfrancisco.R
import com.example.flowsyroomfrancisco.databinding.ItemPostsBinding
import com.example.flowsyroomfrancisco.domain.model.Post

class PostsAdapter(
    val context: Context,
    val actions: PostsActions
) : ListAdapter<Post, PostsAdapter.ItemViewHolder>(DiffCallback()) {

    interface PostsActions {
        fun itemHasClicked(post: Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_posts, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPostsBinding.bind(itemView)

        fun bind(item: Post) {
            itemView.setOnClickListener {
                actions.itemHasClicked(item)
            }

            with(binding) {
                tvTitle.text = item.title
                tvId.text = item.id.toString()
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
