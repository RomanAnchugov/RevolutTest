package ru.romananchugov.core.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: Observer<T>?) {
    observer?.let {
        liveData.observe(this, observer)
    }
}