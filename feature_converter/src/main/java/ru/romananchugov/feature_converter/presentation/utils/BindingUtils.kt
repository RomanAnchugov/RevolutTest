package ru.romananchugov.feature_converter.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.romananchugov.revoluttest.presentation.adapter.BindableAdapter


@BindingAdapter("listData")
fun <T> RecyclerView.setListData(data: T) {
    (this.adapter as? BindableAdapter<T>)?.setData(data)
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}
