package com.example.githubuserapp.ui.userslist

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.repository.models.GitHubProfileData
import com.example.githubuserapp.repository.NetworkState

/**
 * PagedListAdapter class to show list items in recyclerview
 * */
class UserListAdapter(private val retryCallback: () -> Unit, private val onItemClicked: GitUserViewHolder.OnItemCLicked) : PagedListAdapter<GitHubProfileData,RecyclerView.ViewHolder>(COMPARATOR) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_git_user -> GitUserViewHolder.create(parent, onItemClicked)
            R.layout.item_network_state -> NetworkStateViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is GitUserViewHolder){
            holder.bindView(getItem(position)!!)
        }else{
            (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_git_user
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newNetworkState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<GitHubProfileData>() {
            override fun areContentsTheSame(oldItem: GitHubProfileData, newItem: GitHubProfileData): Boolean =
                newItem == oldItem

            override fun areItemsTheSame(oldItem: GitHubProfileData, newItem: GitHubProfileData): Boolean =
                oldItem.id == newItem.id
        }
    }
}
