package com.example.githubuserapp.ui.userslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.repository.NetworkState
import com.example.githubuserapp.repository.Status
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(view: View,
    private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {

    init {
        itemView.retry_button.setOnClickListener { retryCallback() }
    }

    fun bindTo(networkState: NetworkState?) {
        itemView.error_msg.visibility =
            if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            itemView.error_msg.text = networkState.message
        }

        itemView.retry_button.visibility =
            if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        itemView.progress_bar.visibility =
            if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(
            parent: ViewGroup,
            retryCallback: () -> Unit): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_network_state, parent,
                false)
            return NetworkStateViewHolder(view, retryCallback)
        }
    }
}