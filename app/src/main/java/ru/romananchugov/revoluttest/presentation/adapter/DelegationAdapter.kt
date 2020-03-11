package ru.romananchugov.revoluttest.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 * T - is an item class
 */
class DelegationAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, RecyclerView.ViewHolder>(diffCallback), BindableAdapter<List<T>> {

    private val adapterDelegatesManager: AdapterDelegatesManager<T> = AdapterDelegatesManager()

    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        vararg delegate: AdapterDelegate<T>
    ) : this(diffCallback) {
        delegate.forEach {
            adapterDelegatesManager.addDelegate(it)
        }
    }

    fun addDelegates(vararg delegate: AdapterDelegate<T>) {
        delegate.forEach {
            adapterDelegatesManager.addDelegate(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapterDelegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapterDelegatesManager.onBindViewHolder(getItem(position), holder, position)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        adapterDelegatesManager.onBindViewHolder(getItem(position), holder, position, payloads)
    }

    override fun setData(data: List<T>?) {
        submitList(data)
    }
}