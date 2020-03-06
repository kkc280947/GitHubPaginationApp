package com.example.githubuserapp.base

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.app.GitHubApp
import com.example.githubuserapp.app.components.AppComponent
import com.example.githubuserapp.ui.IActivityCallback
import javax.inject.Inject


/***
 *
 * public class TestFragment extends BaseFragment<TestViewModel>
 *
 * @param <T> A ViewModel which inherits from ViewModel with an injected constructor.
</T></TestViewModel> */
abstract class BaseFragment<T : ViewModel?> : Fragment() {

    @Inject
    lateinit var mViewModelFactory: ViewModelFactory

    lateinit var activityCallBack: IActivityCallback

    protected val appComponent: AppComponent by lazy {
        ((activity)?.application as GitHubApp).appComponent
    }

    private var mViewModel: T? = null

    protected fun initViewModel(cls: Class<T>) {
        val activity = activity
            ?: throw RuntimeException(
                "Error: initViewModel() - Cannot create view model with null Activity."
            )
        mViewModel = ViewModelProvider(activity,mViewModelFactory).get(cls)
    }

    protected val viewModel: T
        get() = mViewModel
            ?: throw RuntimeException(
                "Error: getViewModel() - ViewModel not initialised please called initViewModel<T>."
            )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            activityCallBack = activity as IActivityCallback
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString() + " must implement IActvitiyCallback"
            )
        }
    }

    fun getActivityCallback(): IActivityCallback {
        return activityCallBack
    }
    protected fun finishActivity() {
        val activity: Activity? = activity
        activity?.finish()
    }
}