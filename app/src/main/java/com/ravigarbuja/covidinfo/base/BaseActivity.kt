package com.ravigarbuja.covidinfo.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.util.ProgressDialogHelper
import com.ravigarbuja.covidinfo.util.hideSoftKeyboard

abstract class BaseActivity<M : BaseViewModel<*>, V : ViewDataBinding> : AppCompatActivity() {
    lateinit var mViewDataBinding: V
    private var mViewModel: M? = null
    var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        observableListener()
    }


    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
    }


    private fun observableListener() {
        with(getViewModel()) {
            collapseSoftKeyboard.observe(this@BaseActivity, Observer {
                hideSoftKeyboard()
            })
        }
    }

    fun showLoading(message: String) {
        progressDialog = ProgressDialogHelper.progressDialog(this, message)
        progressDialog!!.show()
    }

    fun hideLoading() {
        if (progressDialog != null)
            progressDialog!!.dismiss()
    }

    /**
     * @return layout resource idd
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): M

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    /**
     * activity enter to next transition
     */
    private fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    /**
     * triggered on backpressed with right transition
     */
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }
}