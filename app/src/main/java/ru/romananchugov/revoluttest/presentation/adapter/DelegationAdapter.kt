package ru.romananchugov.revoluttest.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class DelegationAdapter<T>() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = mutableListOf<T>()
    private val adapterDelegatesManager: AdapterDelegatesManager<T> = AdapterDelegatesManager()

    constructor(vararg delegate: AdapterDelegate<T>) : this() {
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

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapterDelegatesManager.onBindViewHolder(items, holder, position)
    }

    fun getItem(position: Int): T = items[position]

    fun add(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
        val itemCount = items.size - position
        notifyItemRangeChanged(position, itemCount)
    }

    fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(items: List<T>) {
        val size = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(size, items.size)
    }

    fun addAll(position: Int, items: List<T>) {
        this.items.addAll(position, items)
        notifyItemRangeInserted(position, items.size)
    }

    fun set(position: Int, item: T) {
        items[position] = item
        notifyItemChanged(position)
    }

    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun clearRange(startPosition: Int) {
        val size = items.size
        items.subList(startPosition, size).clear()
        notifyItemRangeRemoved(startPosition, size)
    }

    fun set(newItems: List<T>, diffUtilsCallbackProducer: (List<T>, List<T>) -> DiffUtil.Callback) {
        val diffResult =
            DiffUtil.calculateDiff(diffUtilsCallbackProducer.invoke(this.items, newItems))
        this.items = newItems.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    fun set(newItems: List<T>, diffResult: DiffUtil.DiffResult) {
        this.items = newItems.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    fun remove(item: T) {
        val index = items.indexOf(item)
        if (index >= 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}