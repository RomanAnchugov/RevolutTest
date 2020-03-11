package ru.romananchugov.core.base.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<ViewState : BaseState, ViewAction : BaseAction>(initialState: ViewState) :
    ViewModel() {

    private val _viewStateLiveData: MutableLiveData<ViewState> = MutableLiveData()
    val viewStateLiveData: LiveData<ViewState>
        get() = _viewStateLiveData

    abstract fun init()
    abstract fun handleAction(viewAction: ViewAction): ViewState

    protected fun sendAction(action: ViewAction) {
        val result = handleAction(action)
//        if (_viewStateLiveData.value == result) return
        _viewStateLiveData.value = result
    }
}