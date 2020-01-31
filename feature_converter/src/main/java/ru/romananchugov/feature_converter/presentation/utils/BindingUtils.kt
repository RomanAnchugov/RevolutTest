package ru.romananchugov.feature_converter.presentation.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.romananchugov.revoluttest.presentation.adapter.BindableAdapter

@BindingAdapter("listData")
fun <T> RecyclerView.setListData(data: T) {
    (this.adapter as? BindableAdapter<T>)?.setData(data)
}
