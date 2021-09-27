package com.example.submission2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.databinding.ItemUserBinding
import com.example.submission2.model.User

class UserGithubAdapter(private val user: ArrayList<User>) : RecyclerView.Adapter<UserGithubAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = user.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(user[position])
    }

    inner class ListViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding){

                txtName.text = user.login
                txtScore.text = user.score
                txtUrlhtml.text = user.htmlUrl

                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgPhoto)

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}
