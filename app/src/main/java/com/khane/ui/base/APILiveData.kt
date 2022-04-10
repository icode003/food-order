package com.khane.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.khane.data.pojo.DataWrapper
import com.khane.data.pojo.ResponseBody

class APILiveData<T> : MutableLiveData<DataWrapper<T>>() {


    /**
     *  @param owner : Life Cycle Owner
     *  @param onChange : live data
     *  @param onError : Server and App error -> return true to handle error by base else return false to handle error by your self
     *
     */
    public fun observe(owner: BaseFragment<*>, onChange: (ResponseBody<T>) -> Unit, onError: (Throwable, ResponseBody<T>?) -> Boolean = { _, _ -> true }) {
        super.observe(owner, Observer<DataWrapper<T>> {
            if (it?.throwable != null) {
                if (onError(it.throwable, it.responseBody)) owner.onError(it.throwable)
            } else if (it?.responseBody != null) {
                onChange(it.responseBody)
            }
        })
    }

    /**
     *  @param owner : Life Cycle Owner
     *  @param onChange : live data
     *  @param onError : Server and App error -> return true to handle error by base else return false to handle error by your self
     *
     */
    public fun observeOwner(owner: BaseFragment<*>, onChange: (ResponseBody<T>) -> Unit, onError: (Throwable, ResponseBody<T>?) -> Boolean = { _, _ -> true }) {
        super.observe(owner.getViewLifecycleOwner(), Observer<DataWrapper<T>> {
            if (it?.throwable != null) {
                if (onError(it.throwable, it.responseBody)) owner.onError(it.throwable)
            } else if (it?.responseBody != null) {
                onChange(it.responseBody)
            }
        })
    }

    /**
     *  @param owner : Life Cycle Owner
     *  @param onChange : live data
     *  @param onError : Server and App error -> return true to handle error by base else return false to handle error by your self
     *
     */
    public fun observe(owner: BaseActivity, onChange: (ResponseBody<T>) -> Unit, onError: (Throwable, ResponseBody<T>?) -> Unit = { _, _ -> }) {
        super.observe(owner, Observer<DataWrapper<T>> {
            if (it?.throwable != null) {
                owner.toggleLoader(false)
                onError(it.throwable, it.responseBody)
            } else if (it?.responseBody != null) {
                onChange(it.responseBody)
            }
        })
    }


}