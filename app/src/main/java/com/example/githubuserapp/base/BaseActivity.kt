package com.example.githubuserapp.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.githubuserapp.app.GitHubApp

/**
 * Base Activity class: Acts as helper class for Custom Activities.
 * Example: class MainActivity: BaseActivity(){
 *
 * }
 * */
abstract class BaseActivity: AppCompatActivity(){

    fun getAppComponent() = (application as GitHubApp).appComponent

    protected fun swapFragment(
        fragment: Fragment, addToBackStack: Boolean
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction()

        fragmentTransaction.replace(getContainerViewId(), fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    interface OnBackPressedListener {
        fun onBackPressed(): Boolean
    }

    override fun onBackPressed() {
        var handled = false
        val fragment =
            supportFragmentManager.findFragmentById(getContainerViewId())
        if (fragment is OnBackPressedListener) {
            handled =
                (fragment as OnBackPressedListener?)!!.onBackPressed()
        }
        if (!handled) {
            super.onBackPressed()
        }
    }

    abstract fun getContainerViewId(): Int
}