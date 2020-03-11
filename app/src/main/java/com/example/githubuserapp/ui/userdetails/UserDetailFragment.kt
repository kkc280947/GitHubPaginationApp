package com.example.githubuserapp.ui.userdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.base.BaseActivity
import com.example.githubuserapp.base.BaseFragment
import com.example.githubuserapp.repository.Status
import com.example.githubuserapp.repository.models.GitHubUserDetailData
import com.example.githubuserapp.utils.Network.internetIsConnected
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_user_detail.*

class UserDetailFragment: BaseFragment<UserDetailViewModel>(),BaseActivity.OnBackPressedListener{

    companion object{
        const val ARGS_USER_ID = "userID"
        fun newInstance(id: String) : UserDetailFragment{
            val fragment = UserDetailFragment()
            val bundle = Bundle()
            bundle.putString(ARGS_USER_ID,id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_detail,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)
        initViewModel(UserDetailViewModel::class.java)
        initUserDetails()
        observeNetworkState()
    }

    private fun initUserDetails() {
        if(internetIsConnected(requireContext())){
           observeUserDetails()
        }else{
            createNetworkDialog(getString(R.string.no_internet_connection),getString(R.string.internet_not_connected))
        }
    }

    private fun observeUserDetails(){
        viewModel.getUserDetail(requireArguments().getString(ARGS_USER_ID,"")).observe(viewLifecycleOwner,
            Observer {
                updateUserDetailsUI(it)
            })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUserDetailsUI(userData: GitHubUserDetailData?) {
        if(userData != null){
            textViewName.text = "Name: ${userData.name ?: ""}"
            textViewCompany.text = "Company: ${userData.company ?: getString(R.string.n_a)}"
            textViewLocation.text = "Location: ${userData.location ?: getString(R.string.n_a)}"
            Glide.with(this)
                .asDrawable()
                .centerCrop()
                .load(userData.avatarUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageViewAvatar)
        }
    }

    private fun observeNetworkState() {
        viewModel.mUserDetailRepository.networkState.observe(viewLifecycleOwner,
            Observer {
                if(it.status == Status.FAILED){
                    createNetworkDialog(it.status.toString(),it.message.toString())
                }
            })
    }

    private fun createNetworkDialog(title: String, message: String){
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.retry)) { dialog, _->
                dialog.dismiss()
                initUserDetails()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _->
                dialog.dismiss()
                onBackPressed()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    override fun onBackPressed(): Boolean {
        activity?.supportFragmentManager?.popBackStack()
        return true
    }
}