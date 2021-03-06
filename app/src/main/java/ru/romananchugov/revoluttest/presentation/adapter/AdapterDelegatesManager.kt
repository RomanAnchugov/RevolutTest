package ru.romananchugov.revoluttest.presentation.adapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class AdapterDelegatesManager<T> {

    private val delegates: SparseArray<AdapterDelegate<T>> = SparseArray()

    fun addDelegate(delegate: AdapterDelegate<T>) {
        var viewType = delegates.size()
        while (delegates.get(viewType) != null) {
            viewType++
            if (viewType == Int.MAX_VALUE - 1) throw Exception("AdapterDelegatesManager delegates overflow")
        }

        delegates.put(viewType, delegate)
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateForViewType(viewType)?.onCreateViewHolder(parent, viewType)
            ?: throw Exception("No Delegate Found for ViewType $viewType")
    }

    fun onBindViewHolder(item: T, holder: RecyclerView.ViewHolder, position: Int) {
        delegateForViewType(holder.itemViewType)?.onBindViewHolder(item, holder, position)
            ?: throw Exception("No Delegate Found for ViewType ${holder.itemViewType}")
    }

    fun onBindViewHolder(
        item: T,
        holder: RecyclerView.ViewHolder,
        position: Int,
        payload: MutableList<Any>
    ) {
        delegateForViewType(holder.itemViewType)?.onBindViewHolder(item, holder, position, payload)
            ?: throw Exception("No Delegate Found for ViewType ${holder.itemViewType}")
    }

    private fun delegateForViewType(viewType: Int): AdapterDelegate<T>? = delegates.get(viewType)
}