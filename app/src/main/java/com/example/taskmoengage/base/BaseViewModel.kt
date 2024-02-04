package com.example.taskmoengage.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T> : ViewModel() {

    fun onSuccess(): LiveData<T> = onSuccessResponse
    protected val onSuccessResponse: MutableLiveData<T> = MutableLiveData()

}