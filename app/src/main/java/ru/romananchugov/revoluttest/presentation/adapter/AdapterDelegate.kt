package ru.romananchugov.revoluttest.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterDelegate<in T> {

    abstract fun isForViewType(items: List<T>, position: Int): Boolean

    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindViewHolder(
        item: T,
        holder: RecyclerView.ViewHolder,
        position: Int
    )
}