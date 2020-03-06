package com.example.githubuserapp.ui.userslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.repository.models.GitHubProfileData
import kotlinx.android.synthetic.main.item_git_user.view.*


/**
 *ViewHolder Class to inflate git user item or cell in recyclerview.
 * */
class GitUserViewHolder(itemView: View, onItemClicked: OnItemCLicked) : RecyclerView.ViewHolder(itemView) {
    lateinit var userItem : GitHubProfileData
    interface OnItemCLicked{
        fun onClickItem(id: String)
    }
    init {
        itemView.setOnClickListener {
            onItemClicked.onClickItem(userItem.login.toString())
        }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClicked: OnItemCLicked): GitUserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_git_user, parent, false)
            return GitUserViewHolder(view,onItemClicked)
        }
    }

    fun bindView(
        gitHubProfileData: GitHubProfileData
    ) {
        this.userItem = gitHubProfileData
        itemView.textViewGitUserName.text = gitHubProfileData.login
        Glide.with(itemView.context)
            .asDrawable()
            .load(gitHubProfileData.avatarUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(itemView.imageViewGitUser)
    }
}