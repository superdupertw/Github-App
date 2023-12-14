package com.example.GithubApp.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.GithubApp.data.local.entity.UserEntity
import com.example.GithubApp.databinding.GithubItemBinding


class FavoritesUserAdapter(
    private val onClick: (UserEntity) -> Unit,
    private val onDelete: (UserEntity) -> Unit
) : ListAdapter<UserEntity, FavoritesUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GithubItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onClick(user)
        }
        holder.binding.btnDelete.setOnClickListener{
            onDelete(user)
        }
    }

    class MyViewHolder(val binding: GithubItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity){
            binding.tvUsername.text = user.username
            binding.btnDelete.isVisible = true
            Glide.with(binding.root)
                .load(user.avatar)
                .into(binding.ivPhoto)
                .clearOnDetach()
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}