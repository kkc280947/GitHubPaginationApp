package com.example.githubuserapp.ui.userslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.base.BaseFragment
import com.example.githubuserapp.repository.models.GitHubProfileData
import com.example.githubuserapp.repository.NetworkState
import com.example.githubuserapp.repository.Status
import com.example.githubuserapp.utils.Network.NO_INTERNET_ERROR
import com.example.githubuserapp.utils.Network.internetIsConnected
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlinx.android.synthetic.main.item_network_state.*
import javax.inject.Inject

class UserListFragment : BaseFragment<UserListViewModel>(), GitUserViewHolder.OnItemCLicked {

    companion object {
        fun newInstance() = UserListFragment()
    }

    private val mUserListAdapter by lazy {
        UserListAdapter(
            { viewModel.retry() }, this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)
        initViewModel(UserListViewModel::class.java)
        initRecyclerUser()
        initSwitchViewButton()
        initData()
        initInitialLoadScreen()
        initNetworkState()
    }


    private fun initRecyclerUser() {
        recyclerUser.layoutManager = LinearLayoutManager(recyclerUser.context)
        recyclerUser.adapter = mUserListAdapter
    }

    private fun initSwitchViewButton() {
        switchList.setOnCheckedChangeListener { _, isChecked ->
            val i =
                (recyclerUser.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (isChecked) {
                recyclerUser.layoutManager = GridLayoutManager(context, 2)
            } else {
                recyclerUser.layoutManager = LinearLayoutManager(context)
            }
            recyclerUser.scrollToPosition(i)
        }
    }

    private fun initData() {
        if (mUserListAdapter.currentList.isNullOrEmpty()) {
            viewModel.onScreenCreated()
            viewModel.userList.observe(viewLifecycleOwner, Observer<PagedList<GitHubProfileData>> {
                mUserListAdapter.submitList(it) {
                    val layoutManager = (recyclerUser.layoutManager as LinearLayoutManager)
                    val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerUser.scrollToPosition(position)
                    }
                }
            })
        }
    }

    private fun initInitialLoadScreen() {
        viewModel.getInitialLoadStatus().observe(viewLifecycleOwner, Observer {
            if (!internetIsConnected(requireContext())) {
                setInitialLoadingState(NetworkState.error(NO_INTERNET_ERROR))
            } else {
                setInitialLoadingState(it)
            }
        })
    }

    private fun initNetworkState() {
        viewModel.getNetworkState().observe(viewLifecycleOwner, Observer<NetworkState> {
            if (!internetIsConnected(requireContext())) {
                mUserListAdapter.setNetworkState(NetworkState.error(NO_INTERNET_ERROR))
            } else {
                mUserListAdapter.setNetworkState(it)
            }
        })
    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState?) {
        error_msg.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            error_msg.text = networkState.message
        }
        retry_button.visibility =
            if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        progress_bar.visibility =
            if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
        retry_button.setOnClickListener { viewModel.retry() }
    }

    override fun onClickItem(id: String) {
        getActivityCallback().moveToUserDetails(id)
    }
}

