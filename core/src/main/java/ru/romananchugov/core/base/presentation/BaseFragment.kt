package ru.romananchugov.core.base.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseFragment<ViewState> : Fragment() {
    abstract val stateObserver: Observer<ViewState>?
}
